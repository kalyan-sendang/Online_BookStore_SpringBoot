package com.task.lms.repository;

import com.task.lms.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "SELECT * FROM orders WHERE user_id = :userId", nativeQuery = true)
    List<Order> getOrdersByUser(Integer userId);
    @Query(value = "SELECT * FROM orders Where order_id = :orderId", nativeQuery = true)
    Order getOrderByOrderId(Integer orderId);
}
