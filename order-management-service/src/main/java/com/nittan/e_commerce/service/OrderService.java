package com.nittan.e_commerce.service;

import com.nittan.e_commerce.client.ProductClient;
import com.nittan.e_commerce.dao.OrderDao;
import com.nittan.e_commerce.dto.OrderDto;
import com.nittan.e_commerce.dto.OrderResponseDto;
import com.nittan.e_commerce.entity.Order;
import com.nittan.e_commerce.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductClient productClient;

    public OrderResponseDto createOrder(OrderDto orderDto) {
        System.out.println("going to get products");
        List<Product> products = productClient.getProductsForOrder(orderDto.getProductIds());
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
        orderResponseDto.setProducts(products);
        System.out.println("returning responsedto of order");
        return orderResponseDto;
    }

    public OrderResponseDto getOrderById(Long id) {
        Optional<Order> orderOptional = orderDao.findById(id);
        if (orderOptional.isEmpty()) {
            return null;
        }
        Order order = orderOptional.get();

        List<Product> products = productClient.getProductsForOrder(order.getProductIds());

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderId(order.getId());
        orderResponseDto.setUserId(order.getUserId());
        orderResponseDto.setStatus(order.getStatus());
        orderResponseDto.setCreatedAt(order.getCreatedAt());
        orderResponseDto.setLastModified(order.getLastModified());
        orderResponseDto.setProducts(products);
        return orderResponseDto;
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
}
