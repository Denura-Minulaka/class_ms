package com.class_ms.dto;

public class RatingRequest {

    private Double rating;
    private String review;

    // Getters and Setters

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
