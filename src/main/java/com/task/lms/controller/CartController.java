package com.task.lms.controller;

import com.task.lms.model.Book;
import com.task.lms.model.Cart;
import com.task.lms.model.User;
import com.task.lms.service.CartService;
import com.task.lms.utils.CartDto;
import com.task.lms.utils.CustomException;
import com.task.lms.utils.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    private ResponseEntity<ResponseWrapper> getBookInCart(HttpServletRequest request){
       Integer id = (Integer) request.getAttribute("userId");

        if(id != null) {
            List<CartDto>cart = cartService.getBookInCart(id);
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Carts retrieved successfully");
            response.setResponse(cart);
            return ResponseEntity.ok(response);
        }
        else{
            throw new CustomException("Database is empty");
        }
    }

    @PostMapping("/cart")
    private ResponseEntity<ResponseWrapper> addToCart(@RequestBody Integer bookId,   HttpServletRequest request) throws CustomException{
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            if(cartService.checkBookInCart(userId, bookId)!= null){
                ResponseWrapper response = new ResponseWrapper();
                response.setStatusCode(HttpStatus.CREATED.value());
                response.setMessage("Book is already present in Cart");
                response.setResponse(null);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }else{

            Cart newCart = new Cart();
            Book book = new Book();
            User user = new User();


            book.setBookId(bookId);

            user.setUserId(userId);


            newCart.setQty(1);
            newCart.setBook(book);
            newCart.setUser(user);
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setMessage("Cart created successfully");
            response.setResponse(cartService.addBookToCart(newCart));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        }catch(CustomException e) {
            throw new CustomException(e.getMessage());
        }
        }

    @PutMapping("/cart/{cartId}")
    public ResponseEntity<ResponseWrapper> editCart(@PathVariable Integer cartId, @RequestBody Integer qty,  HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            CartDto newCardDto =cartService.updateCart(cartId, qty, userId);
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setMessage("Cart updated successfully");
            response.setResponse(newCardDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch(CustomException e){
            throw new CustomException(e.getMessage());
        }
    }


    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity<ResponseWrapper> deleteCart(@PathVariable Integer cartId, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        cartService.deleteCart(cartId, userId);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User deleted successfully");
        return ResponseEntity.ok(response);

    }


}
