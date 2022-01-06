package ru.job4j.cars.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.cars.model.*;
import ru.job4j.cars.store.StoreHibernate;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class AdServlet extends HttpServlet {

    private Set<Photo> photos;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String idAd = req.getParameter("id");
        if (session.getAttribute("user") == null) {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("edit.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return;
        }
        Map<String, String> params = getParams(req, user);
        String paramId = req.getParameter("id");
        String paramCarBody = params.get("carBody");
        String paramBrand = params.get("brand");
        String paramModel = params.get("model");
        Ad ad = new Ad(params.get("name"), params.get("description"), user,
                new CarBody(Integer.parseInt(paramCarBody)),
                new Brand(Integer.parseInt(paramBrand)), new Model(Integer.parseInt(paramModel)));
        ad.setSold("true".equals(params.get("sold")));
        ad.setPhoto(photos);
        if (!"".equals(paramId)) {
            ad.setId(Integer.parseInt(paramId));
            StoreHibernate.instOf().replaceAd(ad);
        } else {
            StoreHibernate.instOf().saveAd(ad);
        }
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

    private ServletFileUpload getServletFU() {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        return new ServletFileUpload(factory);
    }

    private Map<String, String> getParams(HttpServletRequest req, User user) {
        ServletFileUpload upload = getServletFU();
        Map<String, String> params = new HashMap<>();
        List<FileItem> photos = new ArrayList<>();
        try {
            List<FileItem> items = upload.parseRequest(req);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    params.put(item.getFieldName(), item.getString());
                    } else {
                    photos.add(item);
                }
                }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        loadFiles(photos, user);
        return params;
    }

    private void loadFiles(List<FileItem> items, User user) {
        photos = new HashSet<>();
        Properties cfg = new Properties();
        try {
            cfg.load(AdServlet.class.getClassLoader().getResourceAsStream("dir.properties"));
            File folder = new File(cfg.getProperty("dir") + user.getId());
            if (!folder.exists()) {
                folder.mkdir();
            }
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    File file = new File(folder + File.separator + item.getName());
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                        photos.add(new Photo(cfg.getProperty("dir2") + user.getId() + "/" + item.getName()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
