package com.ecommerce.admin.service;

import com.ecommerce.admin.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    void saveProduct(Product product);

    Product getProductById(Long id);

    List<Product> getAllProducts();

    void deleteProduct(Long id);

    List<Product> getFeaturedProducts();

    void saveOrUpdate(Product product);

    Optional<Product> findById(Long id);
}
