package com.nittan.e_commerce.service;

import com.nittan.e_commerce.client.ProductClient;
import com.nittan.e_commerce.dao.OrderDao;
import com.nittan.e_commerce.dto.OrderDto;
import com.nittan.e_commerce.dto.OrderResponseDto;
import com.nittan.e_commerce.entity.Order;
import com.nittan.e_commerce.entity.Product;
import com.nittan.e_commerce.exception.GenericeException;
import com.nittan.e_commerce.exception.OrderNotFoundException;
import com.nittan.e_commerce.exception.ProductServiceException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductClient productClient;

    Logger logger = LoggerFactory.getLogger(OrderService.class);


    // create order and also fetching order from the other service
    public OrderResponseDto createOrder(OrderDto orderDto) {

        ResponseEntity<List<Product>> products = null;
        try {
            products = productClient.getProductsForOrder(orderDto.getProductIds());
        } catch (Exception errorException) {
            System.out.println("got error");
             log.error("ProductServiceClient::Getting products caught the HttpServer server error {}", errorException.toString());
            throw new ProductServiceException("Products not found");
        }
            System.out.println("got order");
            if (products.getStatusCode() == HttpStatus.OK) {
                System.out.println("I am order, i got products");
                Order order = new Order();
                order.setUserId(orderDto.getUserId());
                order.setProductIds(orderDto.getProductIds());
                order.setStatus("CREATED");
                System.out.println("saving to repo");
                orderDao.save(order);
                logger.info("Creating order and got products from product-service {}" + products.getBody().size());
                System.out.println("setting responsedto of order");
                OrderResponseDto orderResponseDto = new OrderResponseDto();
                orderResponseDto.setOrderId(order.getId());
                orderResponseDto.setUserId(order.getUserId());
                orderResponseDto.setStatus(order.getStatus());
                orderResponseDto.setCreatedAt(order.getCreatedAt());
                orderResponseDto.setLastModified(order.getLastModified());
                orderResponseDto.setProducts(products.getBody());
                System.out.println("returning responsedto of order");
                if(orderResponseDto.getProducts().size() != orderDto.getProductIds().size()){
                    log.error("ProductServiceClient::Getting products caught the HttpServer server error {}");
                    throw new ProductServiceException("all products not found");
                }
                return orderResponseDto;
            }
            System.out.println("not found");
            throw new ProductServiceException("Error while getting products from product service");
        }


        // get order by id
    public OrderResponseDto getOrderById(Long id) {
        Optional<Order> orderOptional = orderDao.findById(id);
        if (orderOptional.isEmpty()) {
            return null;
        }
        Order order = orderOptional.get();
        System.out.println("going for order products");
        ResponseEntity<List<Product>> products = productClient.getProductsForOrder(order.getProductIds());
        System.out.println("products fetched");
        if(products.getStatusCode()== HttpStatus.OK) {
            OrderResponseDto orderResponseDto = new OrderResponseDto();
            orderResponseDto.setOrderId(order.getId());
            orderResponseDto.setUserId(order.getUserId());
            orderResponseDto.setStatus(order.getStatus());
            orderResponseDto.setCreatedAt(order.getCreatedAt());
            orderResponseDto.setLastModified(order.getLastModified());
            orderResponseDto.setProducts(products.getBody());
            // statement to show number of products got
            logger.info("got products {}" + products.getBody().size());
            return orderResponseDto;
        }else{
            System.out.println("not found");
            throw new OrderNotFoundException("Order not found with this id");
        }
    }

    // updating order status
    public String updateOrderStatus(Long orderId, String status) {
        Optional<Order> orderOptional = orderDao.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new OrderNotFoundException("order not found with this id");
        }
        Order order = orderOptional.get();
        order.setStatus(status);
        orderDao.save(order);
        return "Order status updated";
    }

    public String deleteOrder(Long orderId) {
        if (!orderDao.existsById(orderId)) {
            throw new OrderNotFoundException("order not found with this id");
        }
        orderDao.deleteById(orderId);
        return "Order deleted successfully";
    }

    // getting all orders and returning through the order response
    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderDao.findAll();
        List<OrderResponseDto> orderResponseList = new ArrayList<>();
        for(Order order: orders){

            // fetching product from product service
            ResponseEntity<List<Product>> products = productClient.getProductsForOrder(order.getProductIds());
            if(products.getStatusCode() == HttpStatus.OK){
            OrderResponseDto orderResponseDto = new OrderResponseDto();
            orderResponseDto.setProducts(products.getBody());
            orderResponseDto.setUserId(order.getUserId());
            orderResponseDto.setStatus(order.getStatus());
            orderResponseDto.setCreatedAt(order.getCreatedAt());
            orderResponseDto.setLastModified(order.getLastModified());
            orderResponseDto.setOrderId(order.getId());
            orderResponseList.add(orderResponseDto);
            }
        }
        if(orderResponseList.isEmpty())
             throw new OrderNotFoundException("no orders found");
        // logger to  show orders
        logger.info("Got {} orders" + orderResponseList.size());
        return orderResponseList;
    }

    // get all products available through product client
    public List<Product> getAllProducts() {
        List<Product> products = null;
        try {
            products =  productClient.getAllProducts();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        // logger statement to check how many products got
        logger.info("got {} products" + products.size());
        return products;
    }
}
