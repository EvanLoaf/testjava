package com.gmail.evanloafakahaitao.services.impl;

import com.gmail.evanloafakahaitao.dao.SqlDao;
import com.gmail.evanloafakahaitao.dao.connection.ConnectionService;
import com.gmail.evanloafakahaitao.dao.impl.SqlDaoImpl;
import com.gmail.evanloafakahaitao.services.SqlService;
import com.gmail.evanloafakahaitao.services.util.SqlFileReader;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SqlServiceImpl implements SqlService {

    private SqlDao sqlDao = new SqlDaoImpl();
    private SqlFileReader sqlFileReader = new SqlFileReader();

    @Override
    public void executeSqlFile(String sqlFilePath) {
        Connection connection = ConnectionService.getInstance().getConnection();
        File sqlFile = sqlDao.getSqlFile(sqlFilePath);
        List<String> sqlCommandsList = sqlFileReader.read(sqlFile);
        try {
            System.out.println("Executing boot.sql ...");
            connection.setAutoCommit(false);
            sqlDao.executeSqlCommands(connection, sqlCommandsList);
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
