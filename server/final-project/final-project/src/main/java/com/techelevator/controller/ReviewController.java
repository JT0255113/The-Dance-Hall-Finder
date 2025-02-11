package com.techelevator.controller;

import com.techelevator.dao.ReviewDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Event;
import com.techelevator.model.Review;
import com.techelevator.model.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(path = "/reviews")

public class ReviewController {

    private ReviewDao reviewDao;

    public ReviewController(ReviewDao reviewDao){
        this.reviewDao = reviewDao;
    }

    //create and add a new review for an event - registered users and admin
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Review add(@Valid @RequestBody Review review, Principal principal) {
        System.out.println(principal.getName());
        return reviewDao.createReview(review);
    }

    //retrieve a list of all reviews - everyone
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Review> list() {
        return reviewDao.getReviews();
    }

    //retrieve an existing review by review id - everyone
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Review get(@PathVariable int id) {
        Review review = reviewDao.getReviewById(id);
        if (review == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
        return review;

    }

    //retrieve a list of reviews for an event by event id - everyone
    //List<Review> getReviewsByEventId(int eventId);
    @RequestMapping(path = "/event/{eventId}", method = RequestMethod.GET)
    public List<Review> getReviewsByEventId(@PathVariable int eventId) {
        List<Review> reviews = reviewDao.getReviewsByEventId(eventId);
        if (reviews.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reviews found for this event");
        }
        return reviews;
    }

    //retrieve a list of reviews for a dance hall by dance hall id - everyone
    @RequestMapping(path = "/dancehall/{danceHallId}", method = RequestMethod.GET)
    public List<Review> getReviewsByDanceHallId(@PathVariable int danceHallId) {
        List<Review> reviews = reviewDao.getReviewsByDanceHallId(danceHallId);
        if (reviews.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reviews found for this dance hall");
        }
        return reviews;
    }





    //update an existing review by review id - registered users and admin
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Review update(@PathVariable int id, @Valid @RequestBody Review review) {

        //check that the user exists
        Review existingReview = reviewDao.getReviewById(id);
        if (existingReview == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Review not found");
        }

        // The id on the path takes precedence over the id in the request body, if any
        review.setReviewId(id);
        try {
            Review updatedReview = reviewDao.updateReview(review);
            return updatedReview;
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
    }

    //delete an existing review by review id - admin only
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete( @PathVariable int id) {
        if (reviewDao.getReviewById(id) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Review not found");
        }
        reviewDao.deleteReviewById(id);
    }

}
