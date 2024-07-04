package com.ecommerce.admin.repository;

import com.ecommerce.admin.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findTop3ByOrderByCreatedAtDesc();
}
