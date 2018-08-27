package com.gmail.evanloafakahaitao.services.impl;

import com.gmail.evanloafakahaitao.dao.FeedbackDao;
import com.gmail.evanloafakahaitao.dao.connection.ConnectionService;
import com.gmail.evanloafakahaitao.dao.impl.FeedbackDaoImpl;
import com.gmail.evanloafakahaitao.dao.model.Feedback;
import com.gmail.evanloafakahaitao.dao.model.User;
import com.gmail.evanloafakahaitao.services.FeedbackService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FeedbackServiceImpl implements FeedbackService {

    private FeedbackDao feedbackDao = new FeedbackDaoImpl();

    @Override
    public Integer save(Long userId, String message) {
        Connection connection = ConnectionService.getInstance().getConnection();
        Feedback feedback = Feedback.newBuilder()
                .withUser(User.newBuilder()
                        .withId(userId)
                        .build())
                .withMessage(message)
                .build();
        Integer feedbackSaved = null;
        try {
            System.out.println("Saving feedback ...");
            connection.setAutoCommit(false);
            feedbackSaved = feedbackDao.save(connection, feedback);
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return feedbackSaved;
    }

    @Override
    public List<Feedback> findAll() {
        Connection connection = ConnectionService.getInstance().getConnection();
        List<Feedback> feedbackList = null;
        try {
            System.out.println("Retrieving all feedback ...");
            connection.setAutoCommit(false);
            feedbackList = feedbackDao.findAll(connection);
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return feedbackList;
    }
}
