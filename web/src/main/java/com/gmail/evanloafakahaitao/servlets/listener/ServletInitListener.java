package com.gmail.evanloafakahaitao.servlets.listener;

import com.gmail.evanloafakahaitao.config.ConfigurationManager;
import com.gmail.evanloafakahaitao.dao.connection.ConnectionService;
import com.gmail.evanloafakahaitao.services.SqlService;
import com.gmail.evanloafakahaitao.services.impl.SqlServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        SqlService sqlService = new SqlServiceImpl();
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        sqlService.executeSqlFile(configurationManager.getProperty(configurationManager.FILE_PROPERTIES.SQL_FILE_PATH));
        System.out.println("Start-up SQL File Executed, Running APP");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionService.getInstance().closeConnection();
        System.out.println("App shutdown. -- Listener");
    }
}
