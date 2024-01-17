package com.task.lms.service;

import com.task.lms.dto.BookResDto;
import com.task.lms.dto.BookWithReviewDto;
import com.task.lms.model.Book;
import com.task.lms.model.User;
import com.task.lms.repository.BookRepository;
import com.task.lms.utils.CustomException;
import com.task.lms.utils.ResponseWrapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    int pageSize = 4;
    //insert Book
    public Book insertBook(Book book) {
        return bookRepository.save(book);
    }

    //get a book
    public Book getABook(int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
            return optionalBook.orElse(null);
    }
    //get all book
    public Page<Book> getAllBook(int pageNo, String title, String author, String genre, Float price ) {
        PageRequest pageable = PageRequest.of(pageNo -1 , pageSize);
        return bookRepository.findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCaseOrPrice(title,author, genre,price,pageable);
    }
    public Page<Book> getAllBooks(int pageNo) {
        PageRequest pageable = PageRequest.of(pageNo -1 , pageSize);
        return bookRepository.findAll(pageable);
    }



    //update book
    public Book updateBook(int id, Book updatedBook) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();

            //updating existing user from updatedBook
            existingBook.setBookId(id);
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setGenre(updatedBook.getGenre());
            existingBook.setPrice(updatedBook.getPrice());
            existingBook.setDetail(updatedBook.getDetail());
            existingBook.setQty(updatedBook.getQty());
            return bookRepository.save(existingBook);
        }
        return updatedBook;

    }

    public void deleteBook(int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            bookRepository.deleteById(id);

        } else {
            throw new CustomException("Book not found with ID: " + id);
        }
    }

    public List<BookWithReviewDto> combineBooksWithReviews(List<Book> books, List<Map<String, Object>> ratings) {
        return books.stream().map(book -> {
            Optional<Map<String, Object>> matchingRating = ratings.stream()
                    .filter(rating -> book.getBookId().equals(rating.get("book_id")))
                    .findFirst();

            float overallRating = matchingRating.map(rating -> Float.parseFloat(rating.get("overall_rating").toString()))
                    .orElse(0.0F);
            int numRatings = matchingRating.map(rating -> Integer.parseInt(rating.get("num_reviews").toString()))
                    .orElse(0);

            return new BookWithReviewDto(book, overallRating, numRatings);
        }).collect(Collectors.toList());
    }

}