package com.nittan.e_commerce.controller;

import com.nittan.e_commerce.dto.OrderDto;
import com.nittan.e_commerce.dto.OrderResponseDto;
import com.nittan.e_commerce.entity.Order;
import com.nittan.e_commerce.entity.Product;
import com.nittan.e_commerce.exception.OrderNotFoundException;
import com.nittan.e_commerce.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.apache.http.protocol.ResponseServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    public int attempt = 1;

    public static final String ORDER_SERVICE = "orderService";

    // create order
    @PostMapping("create")
    public OrderResponseDto createOrder(@RequestBody OrderDto orderDto) {
        System.out.println("in order creation endpoint");
        OrderResponseDto order = orderService.createOrder(orderDto);
        System.out.println("got response");
        return order;
    }

    // get order by id
    @GetMapping("get/{id}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long id) {
        OrderResponseDto order = orderService.getOrderById(id);
        if(order == null) throw new OrderNotFoundException("Order not found for this order id and it got handled");
        return new ResponseEntity<>(order, HttpStatus.OK);
    }


    // update order status
    @PutMapping("updateStatus/{id}")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        String response = orderService.updateOrderStatus(id, status);
        return response;
    }


    // delete order
    @DeleteMapping("delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        String response = orderService.deleteOrder(id);
        return response;
    }

    // get all orders and their products
    @GetMapping("orders")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(){
        List<OrderResponseDto> orderResponseDtos = orderService.getAllOrders();
        return  new ResponseEntity<>(orderResponseDtos,HttpStatus.OK);
    }

    // get all products available in product service
    @GetMapping("getAllProducts")
    @CircuitBreaker(name= "orderService",fallbackMethod = "getAllDemoProducts")
    @Retry(name= "orderService",fallbackMethod = "getAllDemoProducts")
    // getting products from product service
    public List<Product> getAllProducts(){
        System.out.println("retry method called: " + attempt++ + " times " + " at " + new Date());
        return orderService.getAllProducts();
    }

    // fallback method for retry and circuit breaker
      public List<Product> getAllDemoProducts(Exception exception) {
        List<Product> demoProducts = new ArrayList<>();

        demoProducts.add(new Product(1L, "Pen", 10));
        demoProducts.add(new Product(2L, "Pencil", 5));
        demoProducts.add(new Product(3L, "Notebook", 50));
        demoProducts.add(new Product(4L, "Book", 100));
        demoProducts.add(new Product(5L, "Bag", 200));

        return demoProducts;
}

}
