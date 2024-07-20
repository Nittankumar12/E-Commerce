package com.nittan.e_commerce.dao;

import com.nittan.e_commerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Order entities.
 */
@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
}
