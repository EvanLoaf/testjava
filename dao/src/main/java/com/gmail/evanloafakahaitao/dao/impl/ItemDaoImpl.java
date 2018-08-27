package com.gmail.evanloafakahaitao.dao.impl;

import com.gmail.evanloafakahaitao.dao.ItemDao;
import com.gmail.evanloafakahaitao.dao.model.Item;
import com.gmail.evanloafakahaitao.dao.util.ItemProcessor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {

    ItemProcessor itemProcessor = ItemProcessor.getInstance();

    @Override
    public Integer save(Connection connection, List<Item> itemList) {
        String saveItem = "insert into item(name, vendor_code, description, price) " +
                "values(?, ?, ?, ?) on duplicate key update vendor_code = vendor_code";
        Integer changedRows = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(saveItem)) {
            for (Item item : itemList) {
                preparedStatement.setString(1, item.getName());
                preparedStatement.setLong(2, item.getVendorCode());
                preparedStatement.setString(3, item.getDescription());
                preparedStatement.setBigDecimal(4, item.getPrice());
                preparedStatement.addBatch();
            }
            int[] changedRowsBatchArray = preparedStatement.executeBatch();
            for (int i : changedRowsBatchArray) {
                if (changedRows == null) {
                    changedRows = i;
                } else {
                    changedRows += i;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return changedRows;
    }

    @Override
    public List<Item> findAll(Connection connection) {
        String findAllItems = "select i.id as item_id, i.name as item_name, i.vendor_code, i.description, i.price from item i";
        List<Item> itemList = new ArrayList<>();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(findAllItems)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                itemProcessor.getValues(resultSet);
                Item item = itemProcessor.createItem();
                itemList.add(item);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return itemList;
    }

    @Override
    public Item findByVendorCode(Connection connection, Long vendorCode) {
        String findByVendorCode = "select i.id as item_id, i.name as item_name, i.vendor_code, i.description, i.price from item i where i.vendor_code = ?";
        ResultSet resultSet = null;
        Item item = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(findByVendorCode)) {
            preparedStatement.setLong(1, vendorCode);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                itemProcessor.getValues(resultSet);
                item = itemProcessor.createItem();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return item;
    }
}
