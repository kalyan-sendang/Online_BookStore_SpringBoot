package com.task.lms.service;

import com.task.lms.dto.ReqReviewDto;
import com.task.lms.dto.ReviewDto;

import com.task.lms.model.Book;
import com.task.lms.model.Review;
import com.task.lms.model.User;
import com.task.lms.repository.ReviewRepository;

import com.task.lms.repository.UserRepository;
import com.task.lms.utils.ResponseWrapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.*;

@Service
public class ReviewService {
    private  ReviewRepository reviewRepository;

    private UserRepository userRepository;


    public ReviewService(ReviewRepository reviewRepository,UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    public Review getReviewByUserIdAndBookId(Integer userId, Integer bookId){
        return reviewRepository.getReviewByUserIdAndBookId(userId, bookId);
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

    public ReviewDto addReview(ReqReviewDto reqReviewDto, Integer userId) {
        Book book = new Book();
        book.setBookId(reqReviewDto.getBookId());

        Optional<User> newUser = Optional.ofNullable(userRepository.findById(userId).orElse(null));
            User user = newUser.get();
            Review newReview = new Review(user, book, reqReviewDto.getRating(), reqReviewDto.getComment());
            try {
                Review review = reviewRepository.save(newReview);
                return new ReviewDto(review);
            } catch (Exception exception) {
                return null;
            }

    }

    public ResponseEntity<ResponseWrapper> editReview(ReqReviewDto reqReviewDto, Integer userId, Integer reviewId) {
        Optional<Review> prevReview = reviewRepository.findById(reviewId);
        ResponseWrapper response = new ResponseWrapper();
        if (prevReview.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("Cannot found review");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return ResponseEntity.ok(response);
        } else {
            Review newReview = prevReview.get();
            if (!newReview.getUser().getUserId().equals(userId)) {
                response.setSuccess(false);
                response.setMessage("Cannot found review");
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {
                newReview.setRating(reqReviewDto.getRating());
                newReview.setComment(reqReviewDto.getComment());

                Review updatedReview =  reviewRepository.save(newReview);
                ReviewDto newReviewDto = new ReviewDto(updatedReview);
                response.setSuccess(true);
                response.setMessage("Updated Successfully");
                response.setStatusCode(HttpStatus.OK.value());
                response.setResponse(newReviewDto);
                return ResponseEntity.ok(response);
            }
        }

    }

    public Map<String, Object> getSingleBookRating(Integer bookId) {
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

    public List<Map<String, Object>> getRatingOfBooks(List<Integer> bookIds) {
        return reviewRepository.getOverallRatingAndNumReviewsForBooks(bookIds);
    }
}
