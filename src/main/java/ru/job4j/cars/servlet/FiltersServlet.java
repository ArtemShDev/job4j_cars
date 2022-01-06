package ru.job4j.cars.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.store.StoreHibernate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class FiltersServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = "";
        if (req.getParameterMap().containsKey("carBodies")) {
            json = GSON.toJson(StoreHibernate.instOf().findCarBodies());
        } else if (req.getParameterMap().containsKey("brands")) {
            json = GSON.toJson(StoreHibernate.instOf().findBrands());
        } else if (req.getParameterMap().containsKey("models")) {
            json = GSON.toJson(StoreHibernate.instOf()
                    .findModelsByBrand(new Brand(Integer.parseInt(req.getParameter("models")))));
        }
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}
