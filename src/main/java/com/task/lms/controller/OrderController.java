package com.task.lms.controller;

import com.task.lms.model.Order;
import com.task.lms.service.OrderService;
import com.task.lms.utils.CustomException;
import com.task.lms.utils.ResponseWrapper;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public ResponseEntity<ResponseWrapper> getAllOrdersByUserId(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        List<Order> orderList = orderService.getAllOrdersByUserId(userId);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Order retrieved successfully");
        response.setSuccess(true);
        response.setResponse(orderList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/order")
    public ResponseEntity<ResponseWrapper> addOrder(@RequestBody String shippingAddress, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        ResponseWrapper response = new ResponseWrapper();
        try{
            List<Order> orderList = orderService.placeOrder(userId, shippingAddress);
            if(orderList.isEmpty()){
                response.setStatusCode(HttpStatus.NO_CONTENT.value());
                response.setMessage("No Order to place!!!");
                response.setResponse(null);
                return ResponseEntity.ok(response);
            }else{
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Order Placed successfully");
                response.setSuccess(true);
                response.setResponse(orderList);
                return ResponseEntity.ok(response);
            }
        }catch(CustomException e){
            throw new CustomException(e.getMessage());
        }
    }

}
