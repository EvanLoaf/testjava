package com.gmail.evanloafakahaitao.services;

import com.gmail.evanloafakahaitao.dao.model.Feedback;

import java.util.List;

public interface FeedbackService {

    Integer save(Long userId, String message);

    List<Feedback> findAll();
}
