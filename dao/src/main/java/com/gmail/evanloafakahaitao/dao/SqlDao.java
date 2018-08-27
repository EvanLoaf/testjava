package com.gmail.evanloafakahaitao.dao;

import java.io.File;
import java.sql.Connection;
import java.util.List;

public interface SqlDao {

    File getSqlFile(String sqlFilePath);

    void executeSqlCommands(Connection connection, List<String> sqlCommandsList);
}
