package com.gmail.evanloafakahaitao.dao.util;

import com.gmail.evanloafakahaitao.dao.model.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderProcessor {

    private static volatile OrderProcessor instance = null;

    private Long id;
    private String orderUuid;

    private OrderProcessor() {
    }

    public static synchronized OrderProcessor getInstance() {
        if (instance == null) {
            synchronized (OrderProcessor.class) {
                if (instance == null) {
                    instance = new OrderProcessor();
                }
            }
        }
        return instance;
    }


    public void getValues(ResultSet resultSet) {
        id = null;
        orderUuid = null;
        try {
            id = resultSet.getLong("order_id");
            orderUuid = resultSet.getString("uuid");

        } catch (SQLException e) {
            System.out.println("Error extracting values from result set into OrderProcessor");
            e.printStackTrace();
        }
    }


    public Order createOrder() {
        Order order = Order.newBuilder()
                .withId(id)
                .withOrderUuid(orderUuid)
                .build();
        return order;
    }
}
