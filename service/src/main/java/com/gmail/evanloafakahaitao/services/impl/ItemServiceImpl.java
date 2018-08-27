package com.gmail.evanloafakahaitao.services.impl;

import com.gmail.evanloafakahaitao.dao.ItemDao;
import com.gmail.evanloafakahaitao.dao.connection.ConnectionService;
import com.gmail.evanloafakahaitao.dao.impl.ItemDaoImpl;
import com.gmail.evanloafakahaitao.dao.model.Item;
import com.gmail.evanloafakahaitao.services.ItemService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ItemServiceImpl implements ItemService {

    private ItemDao itemDao = new ItemDaoImpl();

    @Override
    public Integer save(List<Item> itemList) {
        Connection connection = ConnectionService.getInstance().getConnection();
        Integer savedItems = null;
        try {
            System.out.println("Saving list of items ...");
            connection.setAutoCommit(false);
            savedItems = itemDao.save(connection, itemList);
            connection.commit();
        } catch (SQLException e) {
            System.out.printf("Error saving List of %d items\n", itemList.size());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return savedItems;
    }

    @Override
    public List<Item> findAll() {
        Connection connection = ConnectionService.getInstance().getConnection();
        List<Item> itemList = null;
        try {
            System.out.println("Finding all items ...");
            connection.setAutoCommit(false);
            itemList = itemDao.findAll(connection);
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error retrieving all Items");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return itemList;
    }

    @Override
    public Item findByVendorCode(Long vendorCode) {
        Connection connection = ConnectionService.getInstance().getConnection();
        Item item = null;
        try {
            System.out.println("Finding item by vendor code ...");
            connection.setAutoCommit(false);
            item = itemDao.findByVendorCode(connection, vendorCode);
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error retrieving item by vendor code");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return item;
    }
}
