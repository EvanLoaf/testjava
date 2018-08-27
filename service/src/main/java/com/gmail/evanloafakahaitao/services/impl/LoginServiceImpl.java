package com.gmail.evanloafakahaitao.services.impl;

import com.gmail.evanloafakahaitao.dao.model.User;
import com.gmail.evanloafakahaitao.services.LoginService;
import com.gmail.evanloafakahaitao.services.UserService;

public class LoginServiceImpl implements LoginService {

    private UserService userService = new UserServiceImpl();

    @Override
    public boolean validate(String email, String password) {
        if (email != null && !email.equals("") && password != null && !password.equals("")) {
            if (email.trim().length() <= 30 && password.trim().length() <= 20) {
                User userByEmail = userService.findByEmail(email.trim());
                if (userByEmail != null) {
                    if (userByEmail.getPassword().equals(password.trim())) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
