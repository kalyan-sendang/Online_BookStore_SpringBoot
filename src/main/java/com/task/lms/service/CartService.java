package com.task.lms.service;

import com.task.lms.model.Book;
import com.task.lms.model.Cart;
import com.task.lms.repository.BookRepository;
import com.task.lms.repository.CartRepository;
import com.task.lms.utils.CartDto;

import com.task.lms.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    @Autowired
    public CartService(CartRepository cartRepository, BookRepository bookRepository) {
        this.cartRepository = cartRepository;
        this.bookRepository =bookRepository;
    }

    public Cart checkBookInCart(Integer userId, Integer bookId){
        Cart cart = cartRepository.findCartByUserIdAndBookId(userId, bookId);
        return cart;
    }

    public List<CartDto> getBookInCart(Integer userId) {
        List<Cart> carts = cartRepository.findAllBooksByUser(userId);
        return carts.stream()
                .map(cart -> new CartDto(cart.getCartId(), cart.getQty(), cart.getBook()))
                .collect(Collectors.toList());

    }

    public CartDto addBookToCart(Cart cart) {
        Cart newCart = cartRepository.save(cart);
//        subtractQtyBook(newCart);

        return new CartDto(newCart.getCartId(), newCart.getQty(), newCart.getBook());
    }

    public CartDto updateCart(Integer id, Integer qty, Integer userId) {
        Optional<Cart> optionalCart = Optional.ofNullable(cartRepository.findById(id).orElse(null));
        Cart existingCart = optionalCart.get();
            if (existingCart.getUser().getUserId().equals(userId)) {
                existingCart.setQty(qty);
                Cart cart = cartRepository.save(existingCart);
//                subtractQtyBook(cart);
                return new CartDto(cart.getCartId(), cart.getQty(), cart.getBook());
            } else {
                return null;
            }
    }

    public void deleteCart(Integer id, Integer userId) {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isPresent()) {
            Cart existingCart = optionalCart.get();
            if (existingCart.getUser().getUserId().equals(userId)) {
                cartRepository.deleteById(id);
            } else {
                throw new CustomException("User not found with ID: " + id);
            }
        }
    }

//    public void subtractQtyBook(Cart newCart){
//        // Subtract the quantity from the Book entity
//        Integer bookId = newCart.getBook().getBookId();
//
//        Optional<Book> optionalBook = bookRepository.findById(bookId);
//        if(optionalBook.isPresent()){
//            Book book = optionalBook.get();
//            System.out.println(book.getQty());
//            book.setQty(book.getQty() - newCart.getQty());
//            bookRepository.save(book);
//        }
//
//    }
}
