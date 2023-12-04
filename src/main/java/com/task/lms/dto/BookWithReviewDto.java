package com.task.lms.dto;

import com.task.lms.model.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookWithReviewDto {
    private Integer bookId;
    private String title;
    private String detail;
    private String author;
    private String genre;
    private Float price;
    private Integer qty;
    private Float overallRating;
    private Integer numberOfReviews;
    private long totalItems;
    private long totalPages;

    private List<ReviewDto> reviews;

    public BookWithReviewDto(Book book, Float overallRating, Integer numberOfReviews) {
        this.bookId = book.getBookId();
        this.title = book.getTitle();
        this.detail = book.getDetail();
        this.author = book.getAuthor();
        this.genre = book.getGenre();
        this.price = book.getPrice();
        this.qty = book.getQty();
        this.overallRating = overallRating;
        this.numberOfReviews = numberOfReviews;
    }

//    public BookWithReviewDto(List<Book> books ,long totalItems, long totalPages) {
//        this.books = books;
//        this.totalItems = totalItems;
//        this.totalPages = totalPages;
//    }
}
