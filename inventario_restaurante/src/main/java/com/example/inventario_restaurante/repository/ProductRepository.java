package com.example.inventario_restaurante.repository;

import com.example.inventario_restaurante.model.entity.Product;
import com.example.inventario_restaurante.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByNameIgnoreCase(String name);
    List<Product> findByCategoryAndAvailable(Category category, Boolean available);
}
