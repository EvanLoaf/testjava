package com.gmail.evanloafakahaitao.dao.impl;

import com.gmail.evanloafakahaitao.dao.OrderDao;
import com.gmail.evanloafakahaitao.dao.model.Item;
import com.gmail.evanloafakahaitao.dao.model.Order;
import com.gmail.evanloafakahaitao.dao.util.ItemProcessor;
import com.gmail.evanloafakahaitao.dao.util.OrderProcessor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    OrderProcessor orderProcessor = OrderProcessor.getInstance();
    ItemProcessor itemProcessor = ItemProcessor.getInstance();

    @Override
    public Integer save(Connection connection, Order order, Item item) {
        String saveOrder = "insert into `order`(user_id, uuid) values(?, ?)";
        String addItemToOrder = "insert into item_in_order values(?, (select id from item where vendor_code = ?))";
        Integer changedRows = null;
        ResultSet generatedKey = null;
        try (
                PreparedStatement preparedStatementSave = connection.prepareStatement(saveOrder, PreparedStatement.RETURN_GENERATED_KEYS);
                PreparedStatement preparedStatementItemToOrder = connection.prepareStatement(addItemToOrder)
        ) {
            preparedStatementSave.setLong(1, order.getUser().getId());
            preparedStatementSave.setString(2, order.getOrderUuid());
            changedRows = preparedStatementSave.executeUpdate();
            generatedKey = preparedStatementSave.getGeneratedKeys();
            if (generatedKey.next()) {
                Long idKey = generatedKey.getLong(1);
                preparedStatementItemToOrder.setLong(1, idKey);
                preparedStatementItemToOrder.setLong(2, item.getVendorCode());
                preparedStatementItemToOrder.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (generatedKey != null) {
                    generatedKey.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return changedRows;
    }

    @Override
    public List<Order> findByUserId(Connection connection, Long id) {
        String findOrdersByUserId = "select id as order_id, uuid from `order` where user_id = ?";
        String findItemInOrder = "select i.name as item_name, i.description, i.vendor_code, i.price from item_in_order iio join item i on iio.item_id = i.id where order_id = ?";
        List<Order> orderList = new ArrayList<>();
        ResultSet resultSetOrder = null;
        ResultSet resultSetItem = null;
        try (
                PreparedStatement preparedStatementOrders = connection.prepareStatement(findOrdersByUserId);
                PreparedStatement preparedStatementItemsInOrders = connection.prepareStatement(findItemInOrder)
        ) {
            preparedStatementOrders.setLong(1, id);
            resultSetOrder = preparedStatementOrders.executeQuery();
            while (resultSetOrder.next()) {
                orderProcessor.getValues(resultSetOrder);
                Order order = orderProcessor.createOrder();
                preparedStatementItemsInOrders.setLong(1, resultSetOrder.getLong("order_id"));
                resultSetItem = preparedStatementItemsInOrders.executeQuery();
                if (resultSetItem.next()) {
                    itemProcessor.getValues(resultSetItem);
                    Item item = itemProcessor.createItem();
                    order.setItem(item);
                }
                orderList.add(order);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSetOrder != null) {
                    resultSetOrder.close();
                }
                if (resultSetItem != null) {
                    resultSetItem.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orderList;
    }

    @Override
    public Integer deleteByUuid(Connection connection, String uuid) {
        String deleteItemInOrder = "delete from item_in_order where order_id = (select id from `order` where uuid = ?)";
        String deleteOrderByUuid = "delete from `order` where uuid = ?";
        Integer changedOrderRows = null;
        try (
                PreparedStatement preparedStatementDeleteItemInOrder = connection.prepareStatement(deleteItemInOrder);
                PreparedStatement preparedStatementDeleteOrderByUuid = connection.prepareStatement(deleteOrderByUuid)
        ) {
            preparedStatementDeleteItemInOrder.setString(1, uuid);
            preparedStatementDeleteItemInOrder.executeUpdate();
            preparedStatementDeleteOrderByUuid.setString(1, uuid);
            changedOrderRows = preparedStatementDeleteOrderByUuid.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return changedOrderRows;
    }
}
