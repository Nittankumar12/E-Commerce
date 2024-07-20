package com.nittan.e_commerce.dao;

import com.nittan.e_commerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ProductDao extends JpaRepository<Product, Long> {
    // check product if already exists by name
    boolean existsByProductName(String name);
    List<Product> findByIdIn(List<Long> ids);
}
