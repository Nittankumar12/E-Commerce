package com.nittan.e_commerce.controller;

import com.nittan.e_commerce.entity.Product;
import com.nittan.e_commerce.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling product-related operations.
 */
@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    Environment environment;

    Logger logger  = LoggerFactory.getLogger(ProductService.class);
    /**
     * Endpoint to retrieve all products.
     *
     * @return List of Product entities.
     */
    @GetMapping("products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * Endpoint to retrieve a product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return The Product entity.
     */
    @GetMapping("products/{id}")
    public Product getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    /**
     * Endpoint to add a new product.
     *
     * @param product The Product object to add.
     * @return Confirmation message.
     */
    @PostMapping("add")
    public String addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    /**
     * Endpoint to update an existing product.
     *
     * @param product The Product object with updated information.
     * @return The updated Product entity.
     */
    @PutMapping("update")
    public Product updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }

    /**
     * Endpoint to delete a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @return Confirmation message.
     */
    @DeleteMapping("delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "deleted";
    }

    /**
     * Endpoint to retrieve products for a given list of product IDs.
     *
     * @param productIds The list of product IDs to retrieve.
     * @return ResponseEntity containing the list of Product entities or NOT_FOUND if no products are found.
     */
    @PostMapping("getProductsForOrder")
    public ResponseEntity<List<Product>> getProductsForOrder(@RequestBody List<Long> productIds){
        logger.info(environment.getProperty("local.server.port"));
        List<Product> products = productService.getProductsForOrder(productIds);
        if(products == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
