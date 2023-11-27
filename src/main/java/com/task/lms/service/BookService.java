package com.task.lms.service;

import com.task.lms.model.Book;
import com.task.lms.model.User;
import com.task.lms.repository.BookRepository;
import com.task.lms.utils.CustomException;
import com.task.lms.utils.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
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
    public List<Book> getAllBook() {
        return bookRepository.findAll(Sort.by("id"));
    }

    //update book
    public Book updateBook(int id, Book updatedBook) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();

            //updating existing user from updatedBook
            existingBook.setId(id);
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setGenre(updatedBook.getGenre());
            existingBook.setPrice(updatedBook.getPrice());
            existingBook.setAvailable(updatedBook.getAvailable());
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
}