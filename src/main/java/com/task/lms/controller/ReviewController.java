package com.task.lms.controller;

import com.task.lms.dto.ReviewDto;
import com.task.lms.model.Review;
import com.task.lms.model.User;
import com.task.lms.service.ReviewService;
import com.task.lms.utils.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<ResponseWrapper>getSingleReview(@PathVariable Integer userId){
        Review review = reviewService.getReviewByUserId(userId);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Review retrieved successfully");
        response.setSuccess(true);
        response.setResponse(review);
        return ResponseEntity.ok(response);
    }

    @GetMapping("reviews/{id}")
    public ResponseEntity<ResponseWrapper>getAllReview(@PathVariable Integer bookId){
        List<ReviewDto> listReview = reviewService.getAllReviewByBook(bookId);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Reviews retrieved successfully");
        response.setSuccess(true);
        response.setResponse(listReview);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reviews")
    public ResponseEntity<ResponseWrapper> addReview(@Valid @RequestBody ReviewDto ReviewDto, HttpServletRequest request){
        User user = (User) request.getAttribute("userId");

        Review prevReview = reviewService.getReviewByUserId(user.getUserId());
        ResponseWrapper response = new ResponseWrapper();
        if(prevReview == null){
            ReviewDto newReview = reviewService.addReview(ReviewDto, user);

            if(newReview == null){
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Error Error Error");
                response.setSuccess(false);
                response.setResponse(prevReview);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }else{
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Review added Successfully");
                response.setSuccess(true);
                response.setResponse(newReview);
                return ResponseEntity.ok(response);
            }
        }else{
            return reviewService.editReview(ReviewDto, user.getUserId(), prevReview.getReviewId());
        }
    }
}
