package com.gmail.evanloafakahaitao.servlets.command.impl;

import com.gmail.evanloafakahaitao.config.ConfigurationManager;
import com.gmail.evanloafakahaitao.dao.model.Feedback;
import com.gmail.evanloafakahaitao.services.FeedbackService;
import com.gmail.evanloafakahaitao.services.impl.FeedbackServiceImpl;
import com.gmail.evanloafakahaitao.servlets.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowFeedbackCommandImpl implements Command {

    private ConfigurationManager configurationManager = ConfigurationManager.getInstance();
    private FeedbackService feedbackService = new FeedbackServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Feedback> feedbackList = feedbackService.findAll();
        if (feedbackList == null) {
            request.setAttribute("error", "Error retrieving feedback");
            return configurationManager.getProperty(configurationManager.PAGE_PROPERTIES.USERS_PAGE_PATH);
        } else {
            request.setAttribute("feedback", feedbackList);
            return configurationManager.getProperty(configurationManager.PAGE_PROPERTIES.SHOW_FEEDBACK_PAGE_PATH);
        }
    }
}
