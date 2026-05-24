package com.example.realestate.service;

import com.example.realestate.models.Review;


import com.example.realestate.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    // Get all reviews
    public List<Review> getAllReviews() {

        return reviewRepository.findAll();
    }


    // Save new review
    public void saveReview(Review review) {

        reviewRepository.save(review);
    }


    // Update review
    public String updateReview(Long id, Review updatedReview) {

        Optional<Review> optionalReview = reviewRepository.findById(id);

        if (optionalReview.isEmpty()) {
            return "NOT_FOUND";
        }

        Review review = optionalReview.get();

        review.setComment(updatedReview.getComment());
        review.setRating(updatedReview.getRating());

        reviewRepository.save(review);

        return "UPDATED";
    }


    // Delete review
    public String deleteReview(Long id) {

        Optional<Review> optionalReview = reviewRepository.findById(id);

        if (optionalReview.isEmpty()) {
            return "NOT_FOUND";
        }

        reviewRepository.deleteById(id);

        return "DELETED";
    }
}