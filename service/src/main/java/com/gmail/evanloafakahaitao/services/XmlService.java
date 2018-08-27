package com.gmail.evanloafakahaitao.services;

import com.gmail.evanloafakahaitao.services.model.ItemXml;

import java.util.List;

public interface XmlService {

    List<ItemXml> getXmlItems(String xmlFilePath, String schemaFilePath);
}
