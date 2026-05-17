package com.example.inventario_restaurante.controller;

import com.example.inventario_restaurante.dto.request.ProductAmountRequest;
import com.example.inventario_restaurante.dto.request.ProductRequest;
import com.example.inventario_restaurante.dto.response.ProductResponse;
import com.example.inventario_restaurante.model.enums.Category;
import com.example.inventario_restaurante.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> getProductsByCategoryAndAvailable(
            @RequestParam Category category, @RequestParam Boolean available) {
        return ResponseEntity.ok(productService.getProductsByCategoryAndAvailability(category, available));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> updateAmount(
            @PathVariable Long id, @Valid @RequestBody ProductAmountRequest request) {
        return ResponseEntity.ok(productService.updateAmount(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
