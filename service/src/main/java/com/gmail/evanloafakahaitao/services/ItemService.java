package com.gmail.evanloafakahaitao.services;

import com.gmail.evanloafakahaitao.dao.model.Item;

import java.util.List;

public interface ItemService {

    Integer save(List<Item> itemList);

    List<Item> findAll();

    Item findByVendorCode(Long vendorCode);
}
