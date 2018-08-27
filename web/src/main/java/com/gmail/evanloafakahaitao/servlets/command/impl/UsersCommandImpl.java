package com.gmail.evanloafakahaitao.servlets.command.impl;

import com.gmail.evanloafakahaitao.config.ConfigurationManager;
import com.gmail.evanloafakahaitao.dao.model.User;
import com.gmail.evanloafakahaitao.services.UserService;
import com.gmail.evanloafakahaitao.services.impl.UserServiceImpl;
import com.gmail.evanloafakahaitao.servlets.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UsersCommandImpl implements Command {

    UserService userService = new UserServiceImpl();
    ConfigurationManager configurationManager = ConfigurationManager.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<User> users = userService.findAll();
        if (users != null) {
            request.setAttribute("users", users);
        } else {
            request.setAttribute("error", "Error retrieving users");
        }
        return configurationManager.getProperty(configurationManager.PAGE_PROPERTIES.USERS_PAGE_PATH);
    }
}
