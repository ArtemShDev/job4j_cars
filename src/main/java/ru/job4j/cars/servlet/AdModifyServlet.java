package ru.job4j.cars.servlet;

import ru.job4j.cars.model.Ad;
import ru.job4j.cars.store.StoreHibernate;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdModifyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Ad ad = StoreHibernate.instOf().getAd(Integer.parseInt(req.getParameter("id")));
        req.setAttribute("ad", ad);
        req.getRequestDispatcher("edit.jsp").forward(req, resp);
    }
}
