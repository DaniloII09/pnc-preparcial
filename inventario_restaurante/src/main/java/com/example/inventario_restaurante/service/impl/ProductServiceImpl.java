package com.example.inventario_restaurante.service.impl;

import com.example.inventario_restaurante.dto.request.ProductAmountRequest;
import com.example.inventario_restaurante.dto.request.ProductRequest;
import com.example.inventario_restaurante.dto.response.ProductResponse;
import com.example.inventario_restaurante.exception.BusinessRuleException;
import com.example.inventario_restaurante.exception.DuplicateEntityException;
import com.example.inventario_restaurante.exception.EntityNotFoundException;
import com.example.inventario_restaurante.model.entity.Product;
import com.example.inventario_restaurante.model.enums.Category;
import com.example.inventario_restaurante.repository.ProductRepository;
import com.example.inventario_restaurante.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        if(productRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateEntityException("Product", "name", request.getName());
        }

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .available(request.getQuantity() != 0 && request.getAvailable())
                .category(request.getCategory())
                .build();

        return toResponse(productRepository.save(product));
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", id));
        return toResponse(product);
    }

    @Override
    public List<ProductResponse> getProductsByCategoryAndAvailability(Category category, Boolean available) {
        return productRepository
                .findByCategoryAndAvailable(category, available)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", id));

        if(!product.getName().equalsIgnoreCase(request.getName())
                && productRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateEntityException("Product", "name", request.getName());
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setAvailable(request.getQuantity() != 0 && request.getAvailable());
        product.setCategory(request.getCategory());
        return toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse updateAmount(Long id, ProductAmountRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", id));

        int newQuantity = product.getQuantity() + request.getAmount();

        if(newQuantity < 0) {
            throw new BusinessRuleException("Insufficient amount, resulting quantity cannot be negative");
        }

        product.setQuantity(newQuantity);
        product.setAvailable(newQuantity != 0 && product.getAvailable());
        return toResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", id));

        if(product.getCategory() == Category.INGREDIENT && product.getAvailable()) {
            throw new BusinessRuleException("Cannot delete an ingredient that is currently available");
        }

        productRepository.delete(product);
    }

    private ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .available(product.getAvailable())
                .category(product.getCategory())
                .build();
    }
}
