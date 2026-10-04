package com.samazon.product.repository;

// Simplifying with JPA repository (extends)
import org.springframework.data.jpa.repository.JpaRepository;

import com.samazon.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}