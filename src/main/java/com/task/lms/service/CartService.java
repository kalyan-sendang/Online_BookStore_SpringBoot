package com.task.lms.service;

import com.task.lms.model.Cart;
import com.task.lms.model.User;
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

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<CartDto> getBookInCart(Integer userId) {
        List<Cart> carts = cartRepository.findAllBooksByUser(userId);
        return carts.stream()
                .map(cart -> new CartDto(cart.getCartId(), cart.getQty(), cart.getBook()))
                .collect(Collectors.toList());

    }

    public CartDto addBookToCart(Cart cart) {
        Cart newCart = cartRepository.save(cart);
        return new CartDto(newCart.getCartId(), newCart.getQty(), newCart.getBook());
    }

    public CartDto updateCart(Integer id, Integer qty, Integer userId) {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isPresent()) {
            Cart existingCart = optionalCart.get();
            if (existingCart.getUser().getUserId().equals(userId)) {

                existingCart.setQty(qty);
                Cart cart = cartRepository.save(existingCart);
                return new CartDto(cart.getCartId(), cart.getQty(), cart.getBook());
            } else {
                return null;
            }

        }
        return null;
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
}
