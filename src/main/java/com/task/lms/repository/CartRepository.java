package com.task.lms.repository;


import com.task.lms.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query(value = "SELECT * FROM cart WHERE userId = :userId", nativeQuery = true)
    List<Cart> findAllBooksByUser(long userId);
}