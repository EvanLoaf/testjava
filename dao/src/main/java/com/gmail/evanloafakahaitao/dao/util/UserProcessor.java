package com.gmail.evanloafakahaitao.dao.util;

import com.gmail.evanloafakahaitao.dao.model.RoleEnum;
import com.gmail.evanloafakahaitao.dao.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProcessor {

    private static volatile UserProcessor instance = null;

    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String addInfo;
    private RoleEnum role;

    private UserProcessor() {
    }

    public static synchronized UserProcessor getInstance() {
        if (instance == null) {
            synchronized (UserProcessor.class) {
                if (instance == null) {
                    instance = new UserProcessor();
                }
            }
        }
        return instance;
    }

    public void getValues(ResultSet resultSet) {
        id = null;
        email = null;
        password = null;
        firstName = null;
        lastName = null;
        phoneNumber = null;
        addInfo = null;
        role = null;
        try {
            id = resultSet.getLong("id");
            email = resultSet.getString("email");
            password = resultSet.getString("password");
            firstName = resultSet.getString("first_name");
            lastName = resultSet.getString("last_name");
            phoneNumber = resultSet.getString("phone_number");
            addInfo = resultSet.getString("add_info");
            role = RoleEnum.getRole(resultSet.getString("role"));
        } catch (SQLException e) {
            System.out.println("Error extracting values from result set into UserProcessor");
            e.printStackTrace();
        }
    }

    public User createUser() {
        User user = User.newBuilder()
                .withId(id)
                .withEmail(email)
                .withPassword(password)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withPhoneNumber(phoneNumber)
                .withAddInfo(addInfo)
                .withRole(role)
                .build();
        return user;
    }
}
