package com.gmail.evanloafakahaitao.servlets;

import com.gmail.evanloafakahaitao.config.ConfigurationManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("Exception Servlet Init");
    }

    @Override
    public void destroy() {
        System.out.println("Exception Servlet Destroyed");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Throwable throwable = (Throwable) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Integer statusCode = (Integer) req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String servletName = (String) req.getAttribute(RequestDispatcher.ERROR_SERVLET_NAME);
        if (servletName == null) {
            servletName = "unknown";
        }
        String requestUri = (String) req.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "unknown";
        }
        System.out.println("Error details:");
        System.out.println("Status code : " + statusCode);
        System.out.println("Servlet name : " + servletName);
        System.out.println("Exception type : " + throwable.getClass().getName());
        System.out.println("Request Uri : " + requestUri);
        throwable.printStackTrace();

        req.setAttribute("error", statusCode);
        req.setAttribute("error_desc", "Temporary unavailability, please be patient");
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        String page = configurationManager.getProperty(configurationManager.PAGE_PROPERTIES.ERRORS_PAGE_PATH);
        getServletContext().getRequestDispatcher(page).forward(req, resp);
    }
}
