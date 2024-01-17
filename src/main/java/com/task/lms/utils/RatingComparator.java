package com.task.lms.utils;

import com.task.lms.dto.BookWithReviewDto;

import java.util.Comparator;

public class RatingComparator implements Comparator<BookWithReviewDto> {

    @Override
    public int compare(BookWithReviewDto book1, BookWithReviewDto book2) {
        return book2.getOverallRating().compareTo(book1.getOverallRating());
    }

}
