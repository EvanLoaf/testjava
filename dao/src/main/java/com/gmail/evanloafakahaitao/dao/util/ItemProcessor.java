package com.gmail.evanloafakahaitao.dao.util;


import com.gmail.evanloafakahaitao.dao.model.*;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemProcessor {

    private static volatile ItemProcessor instance = null;

    private String name;
    private Long vendorCode;
    private String description;
    private BigDecimal price;

    private ItemProcessor() {
    }

    public static synchronized ItemProcessor getInstance() {
        if (instance == null) {
            synchronized (ItemProcessor.class) {
                if (instance == null) {
                    instance = new ItemProcessor();
                }
            }
        }
        return instance;
    }

    public Long getVendorCode() {
        return vendorCode;
    }

    public void getValues(ResultSet resultSet) {
        name = null;
        vendorCode = null;
        description = null;
        price = null;
        try {
            name = resultSet.getString("item_name");
            vendorCode = resultSet.getLong("vendor_code");
            description = resultSet.getString("description");
            price = resultSet.getBigDecimal("price");
        } catch (SQLException e) {
            System.out.println("Error extracting values from result set into ItemProcessor");
            e.printStackTrace();
        }
    }

    public Item createItem() {
        Item item = Item.newBuilder()
                .withName(name)
                .withVendorCode(vendorCode)
                .withDescription(description)
                .withPrice(price)
                .build();
        return item;
    }
}
