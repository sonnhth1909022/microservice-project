package com.orderservice.orderservice.service;

import com.orderservice.orderservice.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product saveProduct(Product product);
    List<Product> getAllProducts();
    Optional<Product> findProductById(long id);
}
