package com.task.lms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqReviewDto {
    @Min(value = 0, message = "Invalid book id")
    private Integer bookId;
    @Min(value = 0, message = "Rating should be greater than 0")
    @Max(value = 5, message = "Rating should be less than 6")
    private Float rating;
    private String comment;


    public ReqReviewDto(Integer bookId, Float rating, String comment) {
        this.bookId = bookId;
        this.rating = rating;
        this.comment = comment;
    }
}
