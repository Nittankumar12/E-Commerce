package com.nittan.e_commerce.dao;

import com.nittan.e_commerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Product entities.
 */
@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

    /**
     * Checks if a product with the given name already exists.
     *
     * @param name The name of the product to check.
     * @return True if a product with the given name exists, false otherwise.
     */
    boolean existsByProductName(String name);

    /**
     * Retrieves products by their IDs.
     *
     * @param ids The list of product IDs to retrieve.
     * @return List of Product entities matching the given IDs.
     */
    List<Product> findByIdIn(List<Long> ids);
}
