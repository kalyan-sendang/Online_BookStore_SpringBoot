package com.task.lms.service;

import com.task.lms.model.Cart;
import com.task.lms.model.Order;
import com.task.lms.repository.CartRepository;
import com.task.lms.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    private CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;

    }

    public List<Order>getAllOrdersByUserId(Integer id){
        return orderRepository.getOrdersByUser(id);
    }

    public List<Order>placeOrder(Integer id){
        List<Cart> cartList = cartRepository.findAllBooksByUser(id);
        if(cartList == null){
            return null;
        }else{
            List<Order>orderList = new ArrayList<>();
            for(Cart cart : cartList){
                Order order = new Order(cart);
                orderList.add(order);
            }
            cartRepository.deleteAll(cartList);
            return orderRepository.saveAll(orderList);
        }
    }

    public Order updateOrder(Integer orderId, String status){
        Optional<Order> optionalOrder = Optional.ofNullable(orderRepository.getOrderByOrderId(orderId));
        if(optionalOrder.isEmpty()){
            return null;
        }else{
            Order order = optionalOrder.get();
            order.setStatus(status);
            order.setShippedTime(LocalDateTime.now());
            return orderRepository.save(order);
        }
    }
}
