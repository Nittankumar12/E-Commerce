package com.nittan.e_commerce.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nittan.e_commerce.entity.Product;
import com.nittan.e_commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    Environment environment;

    // get all products
    @GetMapping("products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // get product by id
    @GetMapping("products/{id}")
    public Product getProductById(@PathVariable Long id){
        Product product = productService.getProductById(id);
        return product;
    }

    // add new product
    @PostMapping("add")
    public String addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }


    // update product
    @PutMapping("update")
    public Product updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }


    // delete product
    @DeleteMapping("delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "deleted";
    }

    // get products for the order
    @PostMapping("getProductsForOrder")
    public ResponseEntity<List<Product>> getProductsForOrder(@RequestBody List<Long> productIds){
    System.out.println(environment.getProperty("local.server.port"));
    List<Product> products = productService.getProductsForOrder(productIds);
    if(products == null) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    System.out.println("returning products");
    return new ResponseEntity<>(products,HttpStatus.OK);
    }


}
