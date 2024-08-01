package com.nittan.e_commerce.controller;

import com.nittan.e_commerce.dto.OrderDto;
import com.nittan.e_commerce.dto.OrderResponseDto;
import com.nittan.e_commerce.entity.Product;
import com.nittan.e_commerce.exception.OrderNotFoundException;
import com.nittan.e_commerce.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Controller for handling order-related operations.
 */
@RestController
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;

    private int attempt = 1;

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Endpoint to create a new order.
     *
     * @param orderDto The DTO containing order details.
     * @return OrderResponseDto containing details of the created order.
     */
    @PostMapping("create")
    public OrderResponseDto createOrder(@RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    /**
     * Endpoint to retrieve an order by its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return ResponseEntity with OrderResponseDto if found, or OrderNotFoundException if not found.
     */
    @GetMapping("get/{id}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long id) {
        OrderResponseDto order = orderService.getOrderById(id);
        if (order == null) throw new OrderNotFoundException("Order not found for id: " + id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    /**
     * Endpoint to update the status of an order by its ID.
     *
     * @param id     The ID of the order to update.
     * @param status The new status of the order.
     * @return String indicating the updated status.
     */
    @PutMapping("updateStatus/{id}")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        return orderService.updateOrderStatus(id, status);
    }

    /**
     * Endpoint to delete an order by its ID.
     *
     * @param id The ID of the order to delete.
     * @return String indicating the result of the deletion.
     */
    @DeleteMapping("delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }

    /**
     * Endpoint to retrieve all orders with their products.
     *
     * @return ResponseEntity with List of OrderResponseDto containing all orders.
     */
    @GetMapping("orders")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> orderResponseDtos = orderService.getAllOrders();
        return new ResponseEntity<>(orderResponseDtos, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve all products available in the product service.
     * Uses Circuit Breaker and Retry patterns for resilience.
     *
     * @return List of Product containing all available products.
     */
    @GetMapping("getAllProducts")
    @CircuitBreaker(name = "orderService", fallbackMethod = "getAllDemoProducts")
    @Retry(name = "orderService", fallbackMethod = "getAllDemoProducts")
    public List<Product> getAllProducts() {
        String message = String.format("Retry method called: %s times at %s ",attempt++,new Date());
        logger.info(message);
        return orderService.getAllProducts();
    }

    /**
     * Fallback method for Retry and Circuit Breaker.
     * Provides demo products as a fallback in case of failures.
     *
     * @param exception The exception that triggered the fallback.
     * @return List of Product containing demo products.
     */
    public List<Product> getAllDemoProducts(Exception exception) {
        return List.of(
                new Product(1L, "Pen", 10),
                new Product(2L, "Pencil", 5),
                new Product(3L, "Notebook", 50),
                new Product(4L, "Book", 100),
                new Product(5L, "Bag", 200)
        );
    }
}
