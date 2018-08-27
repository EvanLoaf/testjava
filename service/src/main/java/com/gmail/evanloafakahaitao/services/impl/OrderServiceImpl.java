package com.gmail.evanloafakahaitao.services.impl;

import com.gmail.evanloafakahaitao.dao.OrderDao;
import com.gmail.evanloafakahaitao.dao.connection.ConnectionService;
import com.gmail.evanloafakahaitao.dao.impl.OrderDaoImpl;
import com.gmail.evanloafakahaitao.dao.model.Item;
import com.gmail.evanloafakahaitao.dao.model.Order;
import com.gmail.evanloafakahaitao.dao.model.User;
import com.gmail.evanloafakahaitao.services.ItemService;
import com.gmail.evanloafakahaitao.services.OrderService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao = new OrderDaoImpl();
    private ItemService itemService = new ItemServiceImpl();

    @Override
    public Integer save(Long userId, Long vendorCode) {
        Connection connection = ConnectionService.getInstance().getConnection();
        String uuid = UUID.randomUUID().toString();
        User user = User.newBuilder()
                .withId(userId)
                .build();
        Integer savedOrders = null;
        try {
            Item item = itemService.findByVendorCode(vendorCode);
            Order order = Order.newBuilder()
                    .withUser(user)
                    .withOrderUuid(uuid)
                    .build();
            System.out.println("Saving order...");
            connection.setAutoCommit(false);
            savedOrders = orderDao.save(connection, order, item);
            connection.commit();
        } catch (SQLException e) {
            System.out.printf("Error saving order from User ID %d\n", user.getId());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return savedOrders;
    }

    @Override
    public List<Order> findByUserId(Long id) {
        Connection connection = ConnectionService.getInstance().getConnection();
        List<Order> orderList = null;
        try {
            System.out.println("Finding orders by user id ...");
            connection.setAutoCommit(false);
            orderList = orderDao.findByUserId(connection, id);
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error retrieving orders by User id");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return orderList;
    }

    @Override
    public Integer deleteByUuid(String uuid) {
        Connection connection = ConnectionService.getInstance().getConnection();
        Integer deletedOrders = null;
        try {
            System.out.println("Deleting order by uuid and items from order ...");
            connection.setAutoCommit(false);
            deletedOrders = orderDao.deleteByUuid(connection, uuid);
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error deleting order");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return deletedOrders;
    }
}
