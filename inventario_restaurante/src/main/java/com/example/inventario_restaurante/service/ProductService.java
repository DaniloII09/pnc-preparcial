package com.example.inventario_restaurante.service;

import com.example.inventario_restaurante.dto.request.ProductAmountRequest;
import com.example.inventario_restaurante.dto.request.ProductRequest;
import com.example.inventario_restaurante.dto.response.ProductResponse;
import com.example.inventario_restaurante.model.enums.Category;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    List<ProductResponse> getProductsByCategoryAndAvailability(Category category, Boolean available);
    ProductResponse updateProduct(Long id, ProductRequest request);
    ProductResponse updateAmount(Long id, ProductAmountRequest request);
    void deleteProduct(Long id);
}
