package com.task.lms.dto;

import com.task.lms.model.Review;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.id.IntegralDataTypeHolder;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDto {


    private Integer reviewId;
    private Integer bookId;
    private Float rating;

    private String comment;

    private String username;

    private Integer userId;

    private LocalDateTime date;

    public ReviewDto(Review review){
        this.reviewId = review.getReviewId();
        this.bookId = review.getBook().getBookId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.userId = review.getUser().getUserId();
        this.username = review.getUser().getUserName();
        this.date = review.getDate();
    }


}
