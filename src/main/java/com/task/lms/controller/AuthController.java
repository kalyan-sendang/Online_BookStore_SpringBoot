package com.task.lms.controller;

import com.task.lms.service.JwtService;
import com.task.lms.model.Book;
import com.task.lms.model.User;
import com.task.lms.service.BookService;
import com.task.lms.service.UserService;
import com.task.lms.utils.CustomException;
import com.task.lms.utils.ResponseWrapper;
import com.task.lms.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/user")
    private ResponseEntity<ResponseWrapper> insertUser(@Valid @RequestBody User user) throws CustomException {
        try {
            UserDTO createdUserDTO = userService.insertUser(user);
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setMessage("User created successfully");
            response.setResponse(createdUserDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch(CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }

    @GetMapping("/user")
    private ResponseEntity<ResponseWrapper> getAllUser() {
        List<UserDTO> users = userService.getAllUser();
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Users retrieved successfully");
        response.setResponse(users);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/user/{id}")
    private ResponseEntity<ResponseWrapper> updateUser( @PathVariable("userId") int id,@Valid @RequestBody User user) {
        UserDTO updatedUserDTO = userService.update(id, user);
        ResponseWrapper response = new ResponseWrapper();
        if (updatedUserDTO.getUserId() != null) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("User updated successfully");
            response.setResponse(updatedUserDTO);
            return ResponseEntity.ok(response);
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/user/{userId}")
    private ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("userId") int id) {
        userService.deleteUser(id);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User deleted successfully");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/book")
    private ResponseEntity<ResponseWrapper> updateBook(@Valid @RequestBody Book book){
        Book newBook = bookService.insertBook(book);
        ResponseWrapper response = new ResponseWrapper();
        if (newBook != null) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Book added successfully");
            response.setSuccess(true);
            response.setResponse(newBook);
            return ResponseEntity.ok(response);
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Book not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/book/{bookId}")
    private ResponseEntity<ResponseWrapper> updateBook(@Valid @PathVariable("bookId")int id, @RequestBody Book book){
        Book updatedBook = bookService.updateBook(id, book);
        ResponseWrapper response = new ResponseWrapper();
        if (updatedBook.getBookId() != null) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Book updated successfully");
            response.setSuccess(true);
            response.setResponse(updatedBook);
            return ResponseEntity.ok(response);
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Book not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @DeleteMapping("/book/{bookId}")
    private ResponseEntity<ResponseWrapper> deleteBook(@PathVariable("bookId")int id){
        bookService.deleteBook(id);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setSuccess(true);
        response.setMessage("User deleted successfully");
        return ResponseEntity.ok(response);
    }

}

