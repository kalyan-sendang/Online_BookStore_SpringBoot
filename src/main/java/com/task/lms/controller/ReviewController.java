package com.task.lms.controller;

import com.task.lms.dto.ReqReviewDto;
import com.task.lms.dto.ReviewDto;
import com.task.lms.model.Review;

import com.task.lms.service.ReviewService;
import com.task.lms.utils.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.validation.Valid;

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

   /* @GetMapping("/review/{userId}")
    public ResponseEntity<ResponseWrapper>getSingleReview(@PathVariable Integer userId){
        Review review = reviewService.getReviewByUserId(userId);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Review retrieved successfully");
        response.setSuccess(true);
        response.setResponse(review);
        return ResponseEntity.ok(response);
    }

    @GetMapping("review/{bookId}")
    public ResponseEntity<ResponseWrapper>getAllReview(@PathVariable Integer bookId){
        List<ReviewDto> listReview = reviewService.getAllReviewByBook(bookId);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Reviews retrieved successfully");
        response.setSuccess(true);
        response.setResponse(listReview);
        return ResponseEntity.ok(response);
    }*/

    @PostMapping("/review")
    public ResponseEntity<ResponseWrapper> addReview(@RequestBody ReqReviewDto reqReviewDto, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        Review prevReview = reviewService.getReviewByUserIdAndBookId(userId, reqReviewDto.getBookId());

        ResponseWrapper response = new ResponseWrapper();
        if(prevReview == null){
            ReviewDto newReview = reviewService.addReview(reqReviewDto, userId);
            if(newReview == null){
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Error adding Error");
                response.setSuccess(false);
                response.setResponse(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }else{
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Review added Successfully");
                response.setSuccess(true);
                response.setResponse(newReview);
                return ResponseEntity.ok(response);
            }
        }else{
            System.out.println("asfsa "+ prevReview.getReviewId());
            return reviewService.editReview(reqReviewDto, userId, prevReview.getReviewId());
        }
    }
}
