package com.gmail.evanloafakahaitao.servlets.command.impl;

import com.gmail.evanloafakahaitao.config.ConfigurationManager;
import com.gmail.evanloafakahaitao.dao.model.Item;
import com.gmail.evanloafakahaitao.services.ItemService;
import com.gmail.evanloafakahaitao.services.XmlService;
import com.gmail.evanloafakahaitao.services.impl.ItemServiceImpl;
import com.gmail.evanloafakahaitao.services.impl.XmlServiceImpl;
import com.gmail.evanloafakahaitao.services.model.ItemXml;
import com.gmail.evanloafakahaitao.services.util.ItemConverter;
import com.gmail.evanloafakahaitao.servlets.command.Command;
import com.gmail.evanloafakahaitao.servlets.model.CommandEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LoadItemsCommandImpl implements Command {

    private ItemService itemService = new ItemServiceImpl();
    private XmlService xmlService = new XmlServiceImpl();
    private ConfigurationManager configurationManager = ConfigurationManager.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ItemXml> itemXmlList = xmlService.getXmlItems(
                configurationManager.getProperty(configurationManager.FILE_PROPERTIES.XML_FILE_PATH),
                configurationManager.getProperty(configurationManager.FILE_PROPERTIES.SCHEMA_FILE_PATH)
        );
        List<Item> itemList = ItemConverter.toItems(itemXmlList);
        Integer savedItems = itemService.save(itemList);
        if (savedItems == null) {
            request.setAttribute("error", "No new items saved, error occurred");
            return configurationManager.getProperty(configurationManager.PAGE_PROPERTIES.USERS_PAGE_PATH);
        } else {
            response.sendRedirect(request.getContextPath() + CommandEnum.ITEMS.getUrl());
        }
        return null;
    }
}
