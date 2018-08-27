package com.gmail.evanloafakahaitao.dao;

import com.gmail.evanloafakahaitao.dao.model.Feedback;

import java.sql.Connection;
import java.util.List;

public interface FeedbackDao {

    Integer save(Connection connection, Feedback feedback);

    List<Feedback> findAll(Connection connection);
}
