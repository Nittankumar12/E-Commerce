package com.nittan.e_commerce.service;

import com.nittan.e_commerce.dao.ProductDao;
import com.nittan.e_commerce.entity.Product;
import com.nittan.e_commerce.exception.GenericeException;
import com.nittan.e_commerce.exception.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling product-related operations.
 */
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    /**
     * Retrieves all products from the database.
     *
     * @return List of Product entities
     * @throws ProductNotFoundException if no products are found
     */
    public List<Product> getAllProducts() {
        List<Product> products = productDao.findAll();
        if (products.isEmpty()) {
            logger.warn("No products found");
            throw new ProductNotFoundException("No products found");
        }
        logger.info("Retrieved {} products " + products.size());
        return products;
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id ID of the product to retrieve
     * @return Product entity
     * @throws ProductNotFoundException if no product is found with the given ID
     */
    public Product getProductById(Long id) {
        Optional<Product> productOptional = productDao.findById(id);
        Product product = productOptional.orElseThrow(() -> new ProductNotFoundException("Product not found with this id"));
        return product;
    }

    /**
     * Adds a new product to the database.
     *
     * @param product Product entity to add
     * @return Success message upon successful addition
     * @throws GenericeException if a product with the same name already exists
     */
    public String addProduct(Product product) {
        if (productDao.existsByProductName(product.getProductName())) {
            throw new GenericeException("Product already present with same name");
        }
        productDao.save(product);
        return "Product added successfully";
    }

    /**
     * Updates an existing product in the database.
     *
     * @param product Product entity with updated details
     * @return Updated Product entity
     * @throws ProductNotFoundException if no product is found with the given ID
     */
    public Product updateProduct(Product product) {
        Optional<Product> optionalProduct = productDao.findById(product.getId());
        Product updatedProduct = optionalProduct.orElseThrow(() -> new ProductNotFoundException("No product found with this id"));
        updatedProduct.setProductName(product.getProductName());
        updatedProduct.setProductPrice(product.getProductPrice());
        productDao.save(updatedProduct);
        return updatedProduct;
    }

    /**
     * Deletes a product from the database by its ID.
     *
     * @param id ID of the product to delete
     * @return Success message upon successful deletion
     * @throws ProductNotFoundException if no product is found with the given ID
     */
    public String deleteProduct(Long id) {
        if (!productDao.existsById(id)) {
            throw new ProductNotFoundException("Product not found with this id");
        }
        productDao.deleteById(id);
        return "Product deleted successfully";
    }

    /**
     * Retrieves products based on a list of product IDs.
     *
     * @param productIds List of product IDs to retrieve
     * @return List of Product entities matching the given IDs
     * @throws ProductNotFoundException if no products are found from the given list of IDs
     */
    public List<Product> getProductsForOrder(List<Long> productIds) {
        List<Product> products;
        try {
            products = productDao.findByIdIn(productIds);
        } catch (Exception ex) {
            throw new ProductNotFoundException("Products not found from the given list or wrong product IDs");
        }
        if (products.isEmpty()) {
            logger.warn("No products found");
            throw new ProductNotFoundException("No products found");
        }
        if (products.size() < productIds.size()) {
            throw new ProductNotFoundException("Not all products found");
        }
        logger.info("Retrieved {} products " + products.size());
        return products;
    }
}
