package com.task.lms.dto;

import com.task.lms.model.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
    private Integer cartId;
    private Integer qty;
    private Book book;

    public CartDto(Integer cartId, Integer qty, Book book) {
        this.cartId = cartId;
        this.qty = qty;
        this.book = book;
    }
}
