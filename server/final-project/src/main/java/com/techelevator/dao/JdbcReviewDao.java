package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Review;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcReviewDao implements ReviewDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // Create and add a new review for an event - registered users and admin
    @Override
    public Review createReview(Review newReview) {
        String sql = "INSERT INTO reviews (event_id, review_text, event_rating, dance_hall_id, user_id) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING review_id; ";

        Review review = null;
        try {
            int newReviewId = jdbcTemplate.queryForObject(sql, int.class,
                    newReview.getEventId(),
                    newReview.getReviewText(),
                    newReview.getEventRating(),
                    newReview.getDanceHallId(),
                    newReview.getUserId());

            review = getReviewById(newReviewId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation while adding review", e);
        } catch (DataAccessException e) {
            throw new DaoException("Data access error while adding review", e);
        } 
        return review;
    }

    // Retrieve a list of all reviews - everyone
    @Override
    public List<Review> getReviews() {

        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews ";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                reviews.add(mapRowToReview(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        } catch (EmptyResultDataAccessException e) {
            throw new DaoException("No reviews found", e);
        } catch (DataAccessException e) {
            throw new DaoException("Data access error while retrieving reviews", e);
        }
        return reviews;
    }

    // Retrieve an existing review by review id - everyone
    @Override
    public Review getReviewById(int id) {

        Review review = null;
        String sql = "SELECT * FROM reviews WHERE review_id = ? ;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                review = mapRowToReview(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        } catch (EmptyResultDataAccessException e) {
            throw new DaoException("No review found with ID " + id, e);
        } catch (DataAccessException e) {
            throw new DaoException("Data access error while retrieving review", e);
        }
        return review;
    }

    // Retrieve a list of reviews for an event by event id - everyone
    @Override
    public List<Review> getReviewsByEventId(int eventId) {

        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE event_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, eventId);
            while (results.next()) {
                reviews.add(mapRowToReview(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        } catch (EmptyResultDataAccessException e) {
            throw new DaoException("No reviews found for event ID " + eventId, e);
        } catch (DataAccessException e) {
            throw new DaoException("Data access error while retrieving reviews for event", e);
        }
        return reviews;
    }

    // Retrieve a list of reviews for a dance hall by dance hall id - everyone
    @Override
    public List<Review> getReviewsByDanceHallId(int danceHallId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE dance_hall_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, danceHallId);
            while (results.next()) {
                reviews.add(mapRowToReview(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        } catch (EmptyResultDataAccessException e) {
            throw new DaoException("No reviews found for dance hall ID " + danceHallId, e);
        } catch (DataAccessException e) {
            throw new DaoException("Data access error while retrieving reviews for dance hall", e);
        }
        return reviews;
    }

    // Retrieve a list of reviews posted by a specific user
    @Override
    public List<Review> getReviewsByUserId(int userId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE user_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                reviews.add(mapRowToReview(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        } catch (EmptyResultDataAccessException e) {
            throw new DaoException("No reviews found for user ID " + userId, e);
        } catch (DataAccessException e) {
            throw new DaoException("Data access error while retrieving reviews for user", e);
        }
        return reviews;
    }

    // Update an existing review by review id - registered users and admin
    @Override
    public Review updateReview(Review updatedReview) {

        String sql = "UPDATE reviews " +
                " SET review_text=?, event_rating=? " +
                "WHERE review_id=?;";

        try {
            int rowsAffected =
                    jdbcTemplate.update(sql,
                            updatedReview.getReviewText(),
                            updatedReview.getEventRating(),
                            updatedReview.getReviewId());
            if (rowsAffected == 0) {
                throw new DaoException("Error updating review");
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return getReviewById(updatedReview.getReviewId());
    }

    // Delete an existing review by review id - admin only
    @Override
    public void deleteReviewById(int id) {

        String sql1 = "DELETE FROM reviews WHERE review_id= ? ;";

        try {
            int rowsAffected = jdbcTemplate.update(sql1, id);

            if (rowsAffected == 0)
                throw new DaoException("Error deleting review " + id);

        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error deleting review " + id, e);
        } catch (DataAccessException e) {
            throw new DaoException("Unable to delete review " + id, e);
        }
    }

    private Review mapRowToReview(SqlRowSet rowSet) {
        Review review = new Review();
        review.setReviewId(rowSet.getInt("review_id"));
        review.setEventId(rowSet.getInt("event_id"));
        review.setReviewText(rowSet.getString("review_text"));
        review.setEventRating(rowSet.getInt("event_rating"));
        review.setDanceHallId(rowSet.getInt("dance_hall_id"));
        review.setUserId(rowSet.getInt("user_id"));
        return review;
    }
}
