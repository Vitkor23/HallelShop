package com.example.HallelShop.repositories;


import com.example.HallelShop.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image , Long>  {
}