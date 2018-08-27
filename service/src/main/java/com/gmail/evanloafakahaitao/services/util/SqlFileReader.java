package com.gmail.evanloafakahaitao.services.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SqlFileReader {

    public List<String> read(File sqlFile) {
        List<String> sqlCommandList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(sqlFile))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if (!line.contains(";")) {
                    sb.append(line);
                } else {
                    sb.append(line);
                    sqlCommandList.add(sb.toString());
                    sb.setLength(0);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Sql boot file not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading sql boot file");
            e.printStackTrace();
        }
        return sqlCommandList;
    }
}
