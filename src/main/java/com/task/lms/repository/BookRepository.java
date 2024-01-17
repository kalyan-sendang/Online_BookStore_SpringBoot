package com.task.lms.repository;

import com.task.lms.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Integer> {
    Page<Book> findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCaseOrPrice(String title, String author, String genre, Float price,Pageable pageable);

    Page<Book> findAll(Pageable pageable);

}
