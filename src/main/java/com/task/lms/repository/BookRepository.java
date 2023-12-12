package com.task.lms.repository;

import com.task.lms.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "SELECT * FROM Book WHERE LOWER(title) LIKE LOWER(CONCAT('%', :title, '%')) OR LOWER(author) LIKE LOWER(CONCAT('%', :author, '%'))OR LOWER(genre) LIKE LOWER(CONCAT('%', :genre, '%')) OR LOWER(detail) LIKE LOWER(CONCAT('%', :detail, '%'))", nativeQuery = true)
    Page<Book> searchBooks(String title, String author, String genre, String detail, Pageable pageable);


}
