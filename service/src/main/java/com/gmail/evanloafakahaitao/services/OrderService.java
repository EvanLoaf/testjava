package com.gmail.evanloafakahaitao.services;

import com.gmail.evanloafakahaitao.dao.model.Order;

import java.util.List;

public interface OrderService {

    Integer save(Long userId, Long vendorCode);

    List<Order> findByUserId(Long id);

    Integer deleteByUuid(String uuid);
}
