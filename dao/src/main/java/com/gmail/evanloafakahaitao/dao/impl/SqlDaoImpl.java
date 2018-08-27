package com.gmail.evanloafakahaitao.dao.impl;

import com.gmail.evanloafakahaitao.dao.SqlDao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SqlDaoImpl implements SqlDao {
    @Override
    public File getSqlFile(String sqlFilePath) {
        File sqlFile = new File(sqlFilePath);
        return sqlFile;
    }

    @Override
    public void executeSqlCommands(Connection connection, List<String> sqlCommandsList) {
        try (Statement statement = connection.createStatement()) {
            for (String s : sqlCommandsList) {
                statement.addBatch(s);
            }
            statement.executeBatch();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
