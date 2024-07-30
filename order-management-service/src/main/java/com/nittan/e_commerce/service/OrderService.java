package com.nittan.e_commerce.service;

import com.nittan.e_commerce.client.ProductClient;
import com.nittan.e_commerce.client.UserClient;
import com.nittan.e_commerce.dao.OrderDao;
import com.nittan.e_commerce.dto.OrderDto;
import com.nittan.e_commerce.dto.OrderResponseDto;
import com.nittan.e_commerce.entity.Order;
import com.nittan.e_commerce.entity.Product;
import com.nittan.e_commerce.entity.User;
import com.nittan.e_commerce.exception.OrderNotFoundException;
import com.nittan.e_commerce.exception.ProductServiceException;
import com.nittan.e_commerce.exception.UserServiceException;
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

/**
 * Service layer for handling order-related operations.
 */
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    Logger logger = LoggerFactory.getLogger(OrderService.class);

    /**
     * Creates an order with the provided order details and fetches products from the product service.
     *
     * @param orderDto The order data transfer object containing user ID and list of product IDs.
     * @return An OrderResponseDto containing the created order details along with fetched products.
     * @throws ProductServiceException If products are not found or an error occurs while fetching products.
     */
    public OrderResponseDto createOrder(OrderDto orderDto) {

        ResponseEntity<User> user = null;
        try{
            user = userClient.getUserById(orderDto.getUserId());
        }
        catch(Exception e){
            logger.error("user not found");
            throw new UserServiceException("User not found ,,,");
        }

        ResponseEntity<List<Product>> products = null;
        try {
            products = productClient.getProductsForOrder(orderDto.getProductIds());
        } catch (Exception errorException) {
            logger.error("ProductServiceClient::Getting products caught the HttpServer server error {}", errorException.toString());
            throw new ProductServiceException("Products not found");
        }
        if(products.getBody().size() != orderDto.getProductIds().size()){
            logger.warn("not all products found, some are missing");
            throw new ProductServiceException("Not all products found");
        }

        if (products.getStatusCode() == HttpStatus.OK) {
            Order order = new Order();
            order.setUserId(orderDto.getUserId());
            order.setProductIds(orderDto.getProductIds());
            order.setStatus("CREATED");
            order = orderDao.save(order);

            OrderResponseDto orderResponseDto = new OrderResponseDto();
            orderResponseDto.setOrderId(order.getId());
            orderResponseDto.setUserEmail(user.getBody().getEmail());
            orderResponseDto.setUserId(user.getBody().getId());
            orderResponseDto.setStatus(order.getStatus());
            orderResponseDto.setCreatedAt(order.getCreatedAt());
            orderResponseDto.setLastModified(order.getLastModified());
            orderResponseDto.setProducts(products.getBody());

//            if (orderResponseDto.getProducts().size() != orderDto.getProductIds().size()) {
//                log.error("ProductServiceClient::Getting products caught the HttpServer server error {}");
//                throw new ProductServiceException("Not all products found");
//            }
            logger.info("order saved to the repo");
            return orderResponseDto;
        }
        logger.error("error while getting products from product client");
        throw new ProductServiceException("Error while getting products from product service");
    }

    /**
     * Retrieves an order by its ID and fetches products associated with the order.
     *
     * @param id The ID of the order to retrieve.
     * @return An OrderResponseDto containing the retrieved order details along with fetched products.
     * @throws OrderNotFoundException If no order is found with the specified ID.
     */
    public OrderResponseDto getOrderById(Long id) {

        Optional<Order> orderOptional = orderDao.findById(id);
        if (orderOptional.isEmpty()) {
            logger.error("Order not found ");
            throw new OrderNotFoundException("Order not found for id: " + id);
        }
        Order order = orderOptional.get();
        ResponseEntity<List<Product>> products = productClient.getProductsForOrder(order.getProductIds());

        ResponseEntity<User> user = null;
        try{
            user = userClient.getUserById(order.getUserId());
        }
        catch(Exception e){
            throw new UserServiceException("User not found");
        }

        if (products.getStatusCode() == HttpStatus.OK) {
            OrderResponseDto orderResponseDto = new OrderResponseDto();
            orderResponseDto.setOrderId(order.getId());
            orderResponseDto.setUserEmail(user.getBody().getEmail());
            orderResponseDto.setUserId(user.getBody().getId());
            orderResponseDto.setStatus(order.getStatus());
            orderResponseDto.setCreatedAt(order.getCreatedAt());
            orderResponseDto.setLastModified(order.getLastModified());
            orderResponseDto.setProducts(products.getBody());
            logger.info("Got "+ products.getBody().size() + " products");
            return orderResponseDto;
        } else {
            logger.error("order not found with this id");
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
    }

    /**
     * Updates the status of an order identified by its ID.
     *
     * @param orderId The ID of the order to update.
     * @param status  The new status to set for the order.
     * @return A message indicating the success of the status update.
     * @throws OrderNotFoundException If no order is found with the specified ID.
     */
    public String updateOrderStatus(Long orderId, String status) {
        Optional<Order> orderOptional = orderDao.findById(orderId);
        if (orderOptional.isEmpty()) {
            logger.error("order not found with this id");
            throw new OrderNotFoundException("Order not found with id: " + orderId);
        }
        Order order = orderOptional.get();
        order.setStatus(status);
        orderDao.save(order);
        logger.info("order status updated");
        return "Order status updated";
    }

    /**
     * Deletes an order identified by its ID.
     *
     * @param orderId The ID of the order to delete.
     * @return A message indicating the success of the deletion.
     * @throws OrderNotFoundException If no order is found with the specified ID.
     */
    public String deleteOrder(Long orderId) {
        if (!orderDao.existsById(orderId)) {
            logger.error("order doesn't exists");
            throw new OrderNotFoundException("Order not found with id: " + orderId);
        }
        orderDao.deleteById(orderId);
        logger.info("order deleted");
        return "Order deleted successfully";
    }

    /**
     * Retrieves all orders along with their associated products.
     *
     * @return A list of OrderResponseDto objects containing details of all orders and their associated products.
     * @throws OrderNotFoundException If no orders are found in the database.
     */
    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderDao.findAll();
        List<OrderResponseDto> orderResponseList = new ArrayList<>();
        for (Order order : orders) {
            ResponseEntity<List<Product>> products = productClient.getProductsForOrder(order.getProductIds());
            ResponseEntity<User> user = null;
            try{
                user = userClient.getUserById(order.getUserId());
            }
            catch(Exception e){
                logger.error("user not found");
                throw new UserServiceException("User not found");
            }
            if (products.getStatusCode() == HttpStatus.OK) {
                OrderResponseDto orderResponseDto = new OrderResponseDto();
                orderResponseDto.setOrderId(order.getId());
                orderResponseDto.setUserEmail(user.getBody().getEmail());
                orderResponseDto.setUserId(user.getBody().getId());
                orderResponseDto.setStatus(order.getStatus());
                orderResponseDto.setCreatedAt(order.getCreatedAt());
                orderResponseDto.setLastModified(order.getLastModified());
                orderResponseDto.setProducts(products.getBody());
                logger.info("Got "+ products.getBody().size()+" products");
                orderResponseList.add(orderResponseDto);
            }
        }
        if (orderResponseList.isEmpty()) {
            logger.error("order not found");
            throw new OrderNotFoundException("No orders found");
        }
        logger.info("returning orderlist");
        return orderResponseList;
    }

    /**
     * Retrieves all products available from the product service.
     *
     * @return A list of Product objects containing details of all available products.
     * @throws ProductServiceException If an error occurs while retrieving products from the product service.
     */
    public List<Product> getAllProducts() {
        try {
            List<Product> products = productClient.getAllProducts();
            logger.info("Got " + products.size() + " products");
            return products;
        } catch (Exception e) {
            logger.error("Error while fetching products from product service", e.getMessage());
            throw new ProductServiceException("products not found");
        }
    }
}
