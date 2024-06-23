package com.nittan.e_commerce.controller;

import com.nittan.e_commerce.dto.OrderDto;
import com.nittan.e_commerce.dto.OrderResponseDto;
import com.nittan.e_commerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("create")
    public OrderResponseDto createOrder(@RequestBody OrderDto orderDto) {
        System.out.println("in order creation endpoint");
        OrderResponseDto orderResponseDto = orderService.createOrder(orderDto);
        return orderResponseDto;
    }

    @GetMapping("get/{id}")
    public OrderResponseDto getOrder(@PathVariable Long id) {
        OrderResponseDto orderResponseDto = orderService.getOrderById(id);
        return orderResponseDto;
    }

    @PutMapping("updateStatus/{id}")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        String response = orderService.updateOrderStatus(id, status);
        return response;
    }

    @DeleteMapping("delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        String response = orderService.deleteOrder(id);
        return response;
    }
}
