package com.example.realestate.controller;

import com.example.realestate.models.Review;
import com.example.realestate.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    // Get all reviews
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {

        return ResponseEntity.ok(reviewService.getAllReviews());
    }


    // Create new review
    @PostMapping
    public ResponseEntity<Map<String, String>> createReview(
            @RequestBody Review review) {

        Map<String, String> response = new HashMap<>();

        reviewService.saveReview(review);

        response.put("status", "success");
        response.put("message", "Review added successfully.");

        return ResponseEntity.ok(response);
    }


    // Update review
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateReview(
            @PathVariable Long id,
            @RequestBody Review updatedReview) {

        Map<String, String> response = new HashMap<>();

        String result = reviewService.updateReview(id, updatedReview);

        if (result.equals("NOT_FOUND")) {

            response.put("status", "error");
            response.put("message", "Review not found.");

            return ResponseEntity.status(404).body(response);
        }

        response.put("status", "success");
        response.put("message", "Review updated successfully.");

        return ResponseEntity.ok(response);
    }


    // Delete review
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteReview(
            @PathVariable Long id) {

        Map<String, String> response = new HashMap<>();

        String result = reviewService.deleteReview(id);

        if (result.equals("NOT_FOUND")) {

            response.put("status", "error");
            response.put("message", "Review not found.");

            return ResponseEntity.status(404).body(response);
        }

        response.put("status", "success");
        response.put("message", "Review deleted successfully.");

        return ResponseEntity.ok(response);
    }
}