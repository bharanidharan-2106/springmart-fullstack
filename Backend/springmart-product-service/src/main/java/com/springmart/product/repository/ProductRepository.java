package com.springmart.product.repository;

import com.springmart.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findBySku(String sku);

    boolean existsBySku(String sku);

    Page<Product> findByCategoryId(String categoryId, Pageable pageable);

    Page<Product> findByBrandId(String brandId, Pageable pageable);

    Page<Product> findByMerchantUuid(String merchantUuid, Pageable pageable);

    List<Product> findByMerchantUuid(String merchantUuid);

    @Query("{ '$or': [ { 'productName': { $regex: ?0, $options: 'i' } }, { 'description': { $regex: ?0, $options: 'i' } } ] }")
    Page<Product> searchByKeyword(String keyword, Pageable pageable);

    @Query("{ 'price': { $gte: ?0, $lte: ?1 } }")
    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
}
