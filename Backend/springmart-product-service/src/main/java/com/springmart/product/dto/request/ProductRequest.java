package com.springmart.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private String merchantUuid;

    @NotBlank(message = "Product name is required")
    private String productName;

    private String sku;

    private String shortDescription;

    private String description;

    @NotBlank(message = "Category ID is required")
    private String categoryId;

    private String brandId;

    @NotBlank(message = "Brand name is required")
    private String brandName;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @PositiveOrZero(message = "Discounted price cannot be negative")
    private BigDecimal discountedPrice;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity cannot be negative")
    private Integer quantity;

    @NotEmpty(message = "At least one product image is required")
    private List<String> productImages;

    private Map<String, String> specifications;

    private boolean active = true;
}
