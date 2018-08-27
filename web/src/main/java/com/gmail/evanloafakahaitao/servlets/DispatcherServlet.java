package com.gmail.evanloafakahaitao.servlets;

import com.gmail.evanloafakahaitao.servlets.command.Command;
import com.gmail.evanloafakahaitao.servlets.command.impl.*;
import com.gmail.evanloafakahaitao.servlets.model.CommandEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private Map<CommandEnum, Command> commands = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        Command commandAction = null;
        try {
            commandAction = commands.get(CommandEnum.getCommand(command));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        if (commandAction != null) {
            try {
                String page = commandAction.execute(req, resp);
                if (page != null) {
                    getServletContext().getRequestDispatcher(page).forward(req, resp);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.printf("Command -- %s -- does not exist\n", command);
        }
    }

    @Override
    public void init() throws ServletException {
        commands.put(CommandEnum.LOGIN, new LoginCommandImpl());
        commands.put(CommandEnum.ITEMS, new ItemsCommandImpl());
        commands.put(CommandEnum.ORDERS, new OrdersCommandImpl());
        commands.put(CommandEnum.ADD_ORDER, new AddOrderCommandImpl());
        commands.put(CommandEnum.DELETE_ORDER, new DeleteOrderCommandImpl());
        commands.put(CommandEnum.LOAD_ITEMS, new LoadItemsCommandImpl());
        commands.put(CommandEnum.USERS, new UsersCommandImpl());
        commands.put(CommandEnum.SEND_FEEDBACK, new SendFeedbackCommandImpl());
        commands.put(CommandEnum.SUBMIT_FEEDBACK, new SubmitFeedbackCommandImpl());
        commands.put(CommandEnum.SHOW_FEEDBACK, new ShowFeedbackCommandImpl());
    }
}
