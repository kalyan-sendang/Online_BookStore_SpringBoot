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

    @Min(value = 0, message = "Invalid book id")
    private Integer reviewId;

    private Integer bookId;

    @Min(value = 0, message = "Rating should be greater than 0")
    @Max(value = 5, message = "Rating should be less than 6")
    private int rating;

    private String comment;

    private String username;

    private long userId;

    private LocalDateTime date;

    public ReviewDto(Integer bookId, int rating, String comment) {
        this.bookId = bookId;
        this.rating = rating;
        this.comment = comment;
    }
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
