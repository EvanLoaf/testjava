package com.gmail.evanloafakahaitao.dao.impl;

import com.gmail.evanloafakahaitao.dao.XmlDao;

import java.io.File;

public class XmlDaoImpl implements XmlDao {
    @Override
    public File getXmlFile(String xmlFilePath) {
        File xmlFile = new File(xmlFilePath);
        return xmlFile;
    }
}
