package com.task.lms.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "price")
    private double price;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "date")
    private LocalDateTime date = LocalDateTime.now();

    @Column(name = "status")
    private String status;

    @Column(name = "shipping_time")
    private LocalDateTime shippedTime;

    @Column(name = "shipping_address")
    private String shippingAddress;

    public Order(Cart cart) {
        this.user = cart.getUser();
        this.book = cart.getBook();
        this.qty = cart.getQty();
        this.price = cart.getBook().getPrice();
        this.totalPrice = cart.getQty() * cart.getBook().getPrice();
        this.status = "processing";
        this.shippedTime = LocalDateTime.now();
    }
    public Order(User user, Book book, int qty, double totalPrice, String status, LocalDateTime shippedTime, String shippingAddress) {
        this.user = user;
        this.book = book;
        this.qty = qty;
        this.price = book.getPrice();
        this.totalPrice = totalPrice;
        this.date =  LocalDateTime.now();
        this.status = status;
        this.shippedTime = shippedTime;
        this.shippingAddress = shippingAddress;
    }

    public Order() {

    }
}

