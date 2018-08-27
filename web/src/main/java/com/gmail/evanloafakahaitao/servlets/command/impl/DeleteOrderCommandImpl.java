package com.gmail.evanloafakahaitao.servlets.command.impl;

import com.gmail.evanloafakahaitao.config.ConfigurationManager;
import com.gmail.evanloafakahaitao.services.OrderService;
import com.gmail.evanloafakahaitao.services.impl.OrderServiceImpl;
import com.gmail.evanloafakahaitao.servlets.command.Command;
import com.gmail.evanloafakahaitao.servlets.model.CommandEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteOrderCommandImpl implements Command {

    private OrderService orderService = new OrderServiceImpl();
    private ConfigurationManager configurationManager = ConfigurationManager.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String uuid = request.getParameter("uuid");
        Integer deletedOrders = orderService.deleteByUuid(uuid);
        if (deletedOrders != null && !deletedOrders.equals(0)) {
            response.sendRedirect(request.getContextPath() + CommandEnum.ORDERS.getUrl());
        } else {
            request.setAttribute("error", "Could not delete order");
            return configurationManager.getProperty(configurationManager.PAGE_PROPERTIES.ORDERS_PAGE_PATH);
        }
        return null;
    }
}
