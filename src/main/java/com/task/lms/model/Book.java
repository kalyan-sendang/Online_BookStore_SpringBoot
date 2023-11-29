package com.task.lms.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
 @Getter
 @Setter
 @Entity
 @Table(name = "Book" )
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;
    @Column
    private String author;
    @Column
    private String title;
    @Column
    private String genre;
    @Column
    private Float price;
    @Column
    private Integer qty;
    @Column(length = 200)
     private String detail;

}
