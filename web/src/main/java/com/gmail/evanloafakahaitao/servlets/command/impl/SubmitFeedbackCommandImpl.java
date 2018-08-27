package com.gmail.evanloafakahaitao.servlets.command.impl;

import com.gmail.evanloafakahaitao.config.ConfigurationManager;
import com.gmail.evanloafakahaitao.services.FeedbackService;
import com.gmail.evanloafakahaitao.services.impl.FeedbackServiceImpl;
import com.gmail.evanloafakahaitao.servlets.command.Command;
import com.gmail.evanloafakahaitao.servlets.model.CommandEnum;
import com.gmail.evanloafakahaitao.servlets.model.UserPrincipal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SubmitFeedbackCommandImpl implements Command {

    private ConfigurationManager configurationManager = ConfigurationManager.getInstance();
    private FeedbackService feedbackService = new FeedbackServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String feedback = request.getParameter("feedback");
        if (feedback == null || feedback.equals("")) {
            request.setAttribute("error", "You did not enter any feedback");
            return configurationManager.getProperty(configurationManager.PAGE_PROPERTIES.SEND_FEEDBACK_PAGE_PATH);
        } else if (feedback.trim().length() > 200) {
            request.setAttribute("error", "Feedback too long");
            request.setAttribute("feedback", feedback);
            return configurationManager.getProperty(configurationManager.PAGE_PROPERTIES.SEND_FEEDBACK_PAGE_PATH);
        }
        HttpSession session = request.getSession();
        UserPrincipal userPrincipal = (UserPrincipal) session.getAttribute("user");
        Long userId = userPrincipal.getId();
        Integer feedbackSaved = feedbackService.save(userId, feedback.trim());
        if (feedbackSaved != null && !feedbackSaved.equals(0)) {
            response.sendRedirect(request.getContextPath() + CommandEnum.ITEMS.getUrl());
        } else {
            request.setAttribute("error", "Error saving your feedback");
            request.setAttribute("feedback", feedback);
            return configurationManager.getProperty(configurationManager.PAGE_PROPERTIES.SEND_FEEDBACK_PAGE_PATH);
        }
        return null;
    }
}
