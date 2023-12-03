package com.task.lms.dto;

import com.task.lms.model.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookResDto {
    private List<Book> books;
    private long totalItems;
    private long totalPages;

    // Constructors, getters, and setters

    // Constructor for convenience
    public BookResDto(List<Book> books, long totalItems, long totalPages) {
        this.books = books;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
