package com.example.inventario_restaurante.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAmountRequest {
    @NotNull(message = "Amount is required")
    private Integer amount;
}
