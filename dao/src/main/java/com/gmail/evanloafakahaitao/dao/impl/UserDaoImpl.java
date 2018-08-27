package com.gmail.evanloafakahaitao.dao.impl;

import com.gmail.evanloafakahaitao.dao.UserDao;
import com.gmail.evanloafakahaitao.dao.model.User;
import com.gmail.evanloafakahaitao.dao.util.UserProcessor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    UserProcessor userProcessor = UserProcessor.getInstance();

    @Override
    public List<User> findAll(Connection connection) {
        String findAllUsers = "select u.id, u.email, u.password, u.first_name, u.last_name, u.phone_number, u.add_info, r.name as role from user u join role r on u.role_id = r.id";
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(findAllUsers)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userProcessor.getValues(resultSet);
                User user = userProcessor.createUser();
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userList;
    }

    @Override
    public User findByEmail(Connection connection, String email) {
        String findUserByEmail = "select u.id, u.email, u.password, u.first_name, u.last_name, u.phone_number, u.add_info, r.name as role from user u join role r on u.role_id = r.id where u.email = ?";
        ResultSet resultSet = null;
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(findUserByEmail)) {
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userProcessor.getValues(resultSet);
                user = userProcessor.createUser();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}
