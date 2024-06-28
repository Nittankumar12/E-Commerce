package com.nittan.e_commerce.controller;

import com.nittan.e_commerce.dto.OrderDto;
import com.nittan.e_commerce.dto.OrderResponseDto;
import com.nittan.e_commerce.entity.Order;
import com.nittan.e_commerce.service.OrderService;
import org.apache.http.protocol.ResponseServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("create")
    public OrderResponseDto createOrder(@RequestBody OrderDto orderDto) {
        System.out.println("in order creation endpoint");
        OrderResponseDto order = orderService.createOrder(orderDto);
        System.out.println("got response");
        return order;
    }

    @GetMapping("get/{id}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long id) {
        OrderResponseDto order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
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

    @GetMapping("orders")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(){
        List<OrderResponseDto> orderResponseDtos = orderService.getAllOrders();
        return  new ResponseEntity<>(orderResponseDtos,HttpStatus.OK);
    }
}
