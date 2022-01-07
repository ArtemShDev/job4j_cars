package ru.job4j.cars.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.cars.store.StoreHibernate;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ListAdServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String src = "";
        Map<String, String> srcMap = new HashMap<>();
        for (Map.Entry<String, String[]> param : req.getParameterMap().entrySet()) {
            String val = param.getValue()[0];
            if (!"Choose...".equals(val) && !param.getKey().equals("showOnlyWithPhoto")) {
                if (src.equals("")) {
                    src = " where ";
                }
                srcMap.put(param.getKey(), val);
                src = src + (src.length() > 7 ? " AND " : "") + "ad." + param.getKey() + " = :" + param.getKey();
            }
        }
        String json = GSON.toJson(StoreHibernate.instOf().findAllAds(src, srcMap));
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}
