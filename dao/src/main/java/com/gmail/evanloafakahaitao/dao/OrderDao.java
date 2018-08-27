package com.gmail.evanloafakahaitao.dao;

import com.gmail.evanloafakahaitao.dao.model.Item;
import com.gmail.evanloafakahaitao.dao.model.Order;

import java.sql.Connection;
import java.util.List;

public interface OrderDao {

    Integer save(Connection connection, Order order, Item item);

    List<Order> findByUserId(Connection connection, Long id);

    Integer deleteByUuid(Connection connection, String uuid);
}
