package com.nittan.e_commerce.service;

import com.nittan.e_commerce.client.ProductClient;
import com.nittan.e_commerce.dao.OrderDao;
import com.nittan.e_commerce.dto.OrderDto;
import com.nittan.e_commerce.dto.OrderResponseDto;
import com.nittan.e_commerce.entity.Order;
import com.nittan.e_commerce.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductClient productClient;

    public OrderResponseDto createOrder(OrderDto orderDto) {
        System.out.println("going for products");
        ResponseEntity<List<Product>> products = productClient.getProductsForOrder(orderDto.getProductIds());
        System.out.println("got order");
        if(products.getStatusCode() == HttpStatus.OK) {
            System.out.println("I am order, i got products");
            Order order = new Order();
            order.setUserId(orderDto.getUserId());
            order.setProductIds(orderDto.getProductIds());
            order.setStatus("CREATED");
            System.out.println("saving to repo");
            orderDao.save(order);
            System.out.println("setting responsedto of order");
            OrderResponseDto orderResponseDto = new OrderResponseDto();
            orderResponseDto.setOrderId(order.getId());
            orderResponseDto.setUserId(order.getUserId());
            orderResponseDto.setStatus(order.getStatus());
            orderResponseDto.setCreatedAt(order.getCreatedAt());
            orderResponseDto.setLastModified(order.getLastModified());
            orderResponseDto.setProducts(products.getBody());
            System.out.println("returning responsedto of order");
            return orderResponseDto;
        }
        else{
            System.out.println("not found");
        }
        return null;
    }

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
            return orderResponseDto;
        }else{
            System.out.println("not found");
        }
        return null;
    }

    public String updateOrderStatus(Long orderId, String status) {
        Optional<Order> orderOptional = orderDao.findById(orderId);
        if (orderOptional.isEmpty()) {
            return "Order not found";
        }
        Order order = orderOptional.get();
        order.setStatus(status);
        orderDao.save(order);
        return "Order status updated";
    }

    public String deleteOrder(Long orderId) {
        if (!orderDao.existsById(orderId)) {
            return "Order not exists";
        }
        orderDao.deleteById(orderId);
        return "Order deleted successfully";
    }

    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderDao.findAll();
        List<OrderResponseDto> orderResponseList = new ArrayList<>();
        for(Order order: orders){
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
        return orderResponseList;
    }
}
