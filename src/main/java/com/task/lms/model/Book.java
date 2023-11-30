package com.task.lms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
    @Min(value = 0, message = "Qty must be greater than or equal to 0")
    private Integer qty;
    @Column(length = 200)
     private String detail;

}
