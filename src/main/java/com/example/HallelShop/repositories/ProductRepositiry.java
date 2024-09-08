package com.example.HallelShop.repositories;

import com.example.HallelShop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepositiry extends JpaRepository<Product,Long> {
    List<Product> findByTitle(String title);}