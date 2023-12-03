package com.task.lms.service;

import com.task.lms.dto.ReviewDto;

import com.task.lms.model.Book;
import com.task.lms.model.Review;
import com.task.lms.model.User;
import com.task.lms.repository.ReviewRepository;

import com.task.lms.utils.ResponseWrapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {
    private  ReviewRepository reviewRepository;


    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review getReviewByUserId(Integer userId){
        return reviewRepository.getReviewByUserId(userId);
    }

    public List<ReviewDto> getAllReviewByBook(Integer bookId){
        List<Review> listReview = reviewRepository.getReviewByBookId(bookId);
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for(Review review : listReview){
            ReviewDto reviewDto = new ReviewDto(review);
            reviewDtoList.add(reviewDto);
        }
        return reviewDtoList;
    }

    public ReviewDto addReview(ReviewDto reviewDto, User newUser) {
        Book book = new Book();
        book.setBookId(reviewDto.getBookId());

        User user = new User();
        user.setUserId(newUser.getUserId());
        user.setUserName(newUser.getUserName());

        Review newReview = new Review(user, book, reviewDto.getRating(), reviewDto.getComment());
        try {
            Review review = reviewRepository.save(newReview);
            return new ReviewDto(review);
        } catch (Exception exception) {
            return null;
        }
    }

    public ResponseEntity<ResponseWrapper> editReview(ReviewDto reviewDto, Integer userId, Integer reviewId) {
        Review prevReview = reviewRepository.findById(reviewId).orElse(null);
        ResponseWrapper response = new ResponseWrapper();
        if (prevReview == null) {
            response.setSuccess(false);
            response.setMessage("Cannot found review");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return ResponseEntity.ok(response);
        } else {
            if (prevReview.getUser().getUserId() != userId) {
                response.setSuccess(false);
                response.setMessage("Cannot found review");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {
                prevReview.setRating(reviewDto.getRating());
                prevReview.setComment(reviewDto.getComment());

                Review newReview =  reviewRepository.save(prevReview);

                ReviewDto reviewDto1 = new ReviewDto(newReview);
                response.setSuccess(true);
                response.setMessage("Updated Successfully");
                response.setStatusCode(HttpStatus.OK.value());
                response.setResponse(reviewDto1);
                return ResponseEntity.ok(response);

            }
        }

    }

   /* public Map<String, Object> getSingleBookRating(Integer bookId) {
        List<Map<String, Object>> reviews = reviewRepository.getOverallRatingAndNumReviewsForSingleBook(bookId);
        try {
            return reviews.get(0);
        } catch (IndexOutOfBoundsException ex) {
            Map<String, Object> emptyRating = new HashMap<>();
            emptyRating.put("book_id", bookId);
            emptyRating.put("overall_rating", 0);
            emptyRating.put("num_reviews", 0);
            return emptyRating;
        }
    }

    public List<Map<String, Object>> getRatingOfBooks(List<Long> bookIds) {
        return reviewRepository.getOverallRatingAndNumReviewsForBooks(bookIds);
    }*/
}
