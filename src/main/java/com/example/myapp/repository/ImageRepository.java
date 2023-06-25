package com.example.myapp.repository;

import com.example.myapp.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image getImageById(int id);
}
