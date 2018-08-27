package com.gmail.evanloafakahaitao.servlets.command.impl;

import com.gmail.evanloafakahaitao.config.ConfigurationManager;
import com.gmail.evanloafakahaitao.dao.model.RoleEnum;
import com.gmail.evanloafakahaitao.dao.model.User;
import com.gmail.evanloafakahaitao.services.LoginService;
import com.gmail.evanloafakahaitao.services.UserService;
import com.gmail.evanloafakahaitao.services.impl.LoginServiceImpl;
import com.gmail.evanloafakahaitao.services.impl.UserServiceImpl;
import com.gmail.evanloafakahaitao.servlets.command.Command;
import com.gmail.evanloafakahaitao.servlets.model.CommandEnum;
import com.gmail.evanloafakahaitao.servlets.util.UserPrincipalConverter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommandImpl implements Command {

    private LoginService loginService = new LoginServiceImpl();
    private UserService userService = new UserServiceImpl();
    private ConfigurationManager configurationManager = ConfigurationManager.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        boolean loginSuccess = loginService.validate(email, password);
        if (loginSuccess) {
            User user = userService.findByEmail(email);
            HttpSession session = request.getSession();
            session.setAttribute("user", UserPrincipalConverter.toUserPrincipal(user));
            if (user.getRole() == RoleEnum.ADMIN) {
                response.sendRedirect(request.getContextPath() + CommandEnum.USERS.getUrl());
            } else {
                response.sendRedirect(request.getContextPath() + CommandEnum.ITEMS.getUrl());
            }
        } else {
            request.setAttribute("error", "Invalid email or password");
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            return configurationManager.getProperty(configurationManager.PAGE_PROPERTIES.LOGIN_PAGE_PATH);
        }
        return null;
    }
}
