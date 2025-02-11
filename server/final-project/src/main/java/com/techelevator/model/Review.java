package com.techelevator.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents a review for an event.
 */
public class Review {

    private int reviewId;

    @NotNull
    private int eventId;

    @NotNull
    @Size(max=255)
    private String reviewText;

    @Min(1)
    @Max(5)
    private int eventRating;

    @NotNull
    private int danceHallId;

    @NotNull
    private int userId;

    // Constructor
    public Review() {
    }

    public Review(int reviewId, int eventId, String reviewText, int eventRating, int danceHallId, int userId) {
        this.reviewId = reviewId;
        this.eventId = eventId;
        this.reviewText = reviewText;
        this.eventRating = eventRating;
        this.danceHallId = danceHallId;
        this.userId = userId;
    }

    // Getters and Setters
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getEventRating() {
        return eventRating;
    }

    public void setEventRating(int eventRating) {
        this.eventRating = eventRating;
    }

    public int getDanceHallId() {
        return danceHallId;
    }

    public void setDanceHallId(int danceHallId) {
        this.danceHallId = danceHallId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}


