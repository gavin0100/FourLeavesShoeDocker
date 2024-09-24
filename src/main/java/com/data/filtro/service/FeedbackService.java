package com.data.filtro.service;

import com.data.filtro.model.Feedback;
import com.data.filtro.model.User;
import com.data.filtro.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import java.util.Date;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public List<Feedback> getAllFeedbackByProduct(int productId) {
        return feedbackRepository.findAllByProductId(productId);
    }
    public List<Feedback> getAllFeedBackByProductId(int id) {
        return feedbackRepository.findAllByProductId(id);
    }

    public Feedback addFeedback(Feedback feedback) {

        return feedbackRepository.save(feedback);
    }
    public void createFeedback(Feedback feedback) {
        Feedback newFeedback = new Feedback();
        newFeedback.setProduct(feedback.getProduct());
        newFeedback.setUser(feedback.getUser());
        newFeedback.setContent(feedback.getContent());
        newFeedback.setDate(Instant.now());
        feedbackRepository.save(newFeedback);
    }

}