package com.ecommerce.admin.repository;

import com.ecommerce.admin.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Add custom query methods if needed
}
