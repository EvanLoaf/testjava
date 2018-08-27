package com.gmail.evanloafakahaitao.services.impl;

import com.gmail.evanloafakahaitao.dao.UserDao;
import com.gmail.evanloafakahaitao.dao.connection.ConnectionService;
import com.gmail.evanloafakahaitao.dao.impl.UserDaoImpl;
import com.gmail.evanloafakahaitao.dao.model.User;
import com.gmail.evanloafakahaitao.services.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public List<User> findAll() {
        Connection connection = ConnectionService.getInstance().getConnection();
        List<User> listOfUsers = null;
        try {
            System.out.println("Retrieving all users...");
            connection.setAutoCommit(false);
            listOfUsers = userDao.findAll(connection);
            connection.commit();
            System.out.println("Users found : " + listOfUsers.size());
        } catch (SQLException e) {
            System.out.println("Error retrieving all Users with full info");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return listOfUsers;
    }

    @Override
    public User findByEmail(String email) {
        Connection connection = ConnectionService.getInstance().getConnection();
        User user = null;
        try {
            System.out.println("Finding user by email...");
            connection.setAutoCommit(false);
            user = userDao.findByEmail(connection, email);
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error retrieving User by email");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return user;
    }
}
