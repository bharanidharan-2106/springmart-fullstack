package com.springmart.product.entity;

import com.springmart.product.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document(collection = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String id;
    
    @Indexed
    private String merchantUuid;
    
    private String productName;
    
    @Indexed(unique = true)
    private String sku;
    
    @Indexed
    private String slug;
    
    private String shortDescription;
    
    private String description;
    
    @Indexed
    private String categoryId;
    
    @Indexed
    private String brandId;
    
    private BigDecimal price;
    
    private BigDecimal discountedPrice;
    
    private Integer quantity;
    
    private List<String> productImages;
    
    private Map<String, String> specifications;
    
    private Double averageRating;
    
    private Integer reviewCount;
    
    @Indexed
    private ProductStatus status;
    
    private boolean active;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
