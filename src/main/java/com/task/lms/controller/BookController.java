package com.task.lms.controller;

import com.task.lms.dto.BookWithReviewDto;
import com.task.lms.dto.ReviewDto;
import com.task.lms.model.Book;
import com.task.lms.service.BookService;
import com.task.lms.service.ReviewService;
import com.task.lms.utils.CustomException;
import com.task.lms.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@Controller
@RestController
@RequestMapping("/api")
@Tag(name = "Book Controller", description = "This is book api for books")

public class BookController {

    BookService bookService;
   ReviewService reviewService;

    public BookController(BookService bookService, ReviewService reviewService) {
        this.bookService = bookService;
        this.reviewService = reviewService;
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<ResponseWrapper> getBook(@PathVariable("bookId")int id){
        Book book = bookService.getABook(id);
        if (book != null) {
            try {
                Map<String, Object> rating = reviewService.getSingleBookRating(book.getBookId());
                List<ReviewDto> reviews = reviewService.getAllReviewByBook(book.getBookId());
                float overallRating = Float.parseFloat(rating.get("overall_rating").toString());
                Integer numRatings = Integer.parseInt(rating.get("num_reviews").toString());
                BookWithReviewDto bookWithReviewDto = new BookWithReviewDto(book, overallRating, numRatings);
                bookWithReviewDto.setReviews(reviews);
                ResponseWrapper response = new ResponseWrapper();
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Book retrieved successfully");
                response.setSuccess(true);
                response.setResponse(bookWithReviewDto);
                return ResponseEntity.ok(response);
            }catch(CustomException e){
                throw new CustomException(e.getMessage());
            }
        } else {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Book not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @GetMapping("/book")
    public ResponseEntity<ResponseWrapper> getAllBook(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
                                                       @RequestParam(required = false) String title,
                                                       @RequestParam(required = false) String author,
                                                       @RequestParam(required = false)String genre,
                                                       @RequestParam(required = false)Float price) {
        Page<Book> bookPage;
        if(title == null || author == null || genre== null || price == null){
            bookPage = bookService.getAllBooks(pageNo);
        }else{
            bookPage = bookService.getAllBook(pageNo, title, author, genre, price);
        }
        return getResponseWrapperResponseEntity(bookPage);


    }
    public ResponseEntity<ResponseWrapper> getResponseWrapperResponseEntity(Page<Book> bookPage) {
        List<Book> books = bookPage.getContent();
        long totalBooks = bookPage.getTotalElements();
        int totalPages = bookPage.getTotalPages();
        List<Integer> bookIds = books.stream().map(Book::getBookId).toList();

        List<Map<String, Object>> ratings = reviewService.getRatingOfBooks(bookIds);

        List<BookWithReviewDto> bookWithReviewDto = bookService.combineBooksWithReviews(books, ratings);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Books retrieved successfully");
        response.setSuccess(true);
        response.setTotalItems(totalBooks);
        response.setTotalPages(totalPages);
        response.setResponse(bookWithReviewDto);
        return ResponseEntity.ok(response);
    }



    /*@GetMapping("/book")
    private ResponseEntity<ResponseWrapper> getAllBook(@RequestParam(name ="pageNo",defaultValue = "1")int  pageNo){
        BookResDto bookResDto = bookService.getAllBook(pageNo);
        List<Book> books = bookResDto.getBooks();
        List<Integer> bookIds = new ArrayList<>();
        books.forEach(book -> {
            bookIds.add(book.getBookId());
        });

        List<Map<String, Object>> ratings = reviewService.getRatingOfBooks(bookIds);

        List<BookWithReviewDto> bookWithReviewDtos = new ArrayList<>();

        for (Book book : books) {
            boolean added = false;
            for (Map<String, Object> rating : ratings) {
                Integer bookId = (Integer) rating.get("book_id");
                if (book.getBookId().equals(bookId)) {
                    Float overallRating = Float.parseFloat(rating.get("overall_rating").toString());
                    Integer numRatings = Integer.parseInt(rating.get("num_reviews").toString());
                    BookWithReviewDto bookWithReviewDto = new bookWithReviewDto(book, 5.0F, 5);
                    bookWithReviewDtos.add(bookWithReviewDto);
                    added = true;
                    break;
                }
            }

            if (!added) {
                BookWithReviewDto bookWithReviewDto = new bookWithReviewDto(book, 0, 0);
                bookWithReviewDtos.add(bookWithReviewDto);
            }
        }
                ResponseWrapper response = new ResponseWrapper();
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Books retrieved successfully");
                response.setSuccess(true);
                response.setResponse(bookResDto);
                return ResponseEntity.ok(response);

    }
*/
}
