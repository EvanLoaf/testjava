package com.gmail.evanloafakahaitao.servlets.command.impl;

import com.gmail.evanloafakahaitao.config.ConfigurationManager;
import com.gmail.evanloafakahaitao.services.OrderService;
import com.gmail.evanloafakahaitao.services.impl.OrderServiceImpl;
import com.gmail.evanloafakahaitao.servlets.command.Command;
import com.gmail.evanloafakahaitao.servlets.model.CommandEnum;
import com.gmail.evanloafakahaitao.servlets.model.UserPrincipal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddOrderCommandImpl implements Command {

    private OrderService orderService = new OrderServiceImpl();
    private ConfigurationManager configurationManager = ConfigurationManager.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long vendorCode = Long.valueOf(request.getParameter("vendor_code"));
        HttpSession session = request.getSession();
        UserPrincipal userPrincipal = (UserPrincipal) session.getAttribute("user");
        Long userId = userPrincipal.getId();
        Integer ordersSaved = orderService.save(userId, vendorCode);
        if (ordersSaved != null && !ordersSaved.equals(0)) {
            response.sendRedirect(request.getContextPath() + CommandEnum.ORDERS.getUrl());
        } else {
            request.setAttribute("error", "Could not save order");
            return configurationManager.getProperty(configurationManager.PAGE_PROPERTIES.ORDERS_PAGE_PATH);
        }
        return null;
    }
}
