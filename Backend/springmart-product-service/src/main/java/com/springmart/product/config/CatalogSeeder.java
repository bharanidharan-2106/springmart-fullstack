package com.springmart.product.config;

import com.springmart.product.entity.Brand;
import com.springmart.product.entity.Category;
import com.springmart.product.repository.BrandRepository;
import com.springmart.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CatalogSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            categoryRepository.save(Category.builder().name("Electronics").description("Electronic devices and accessories").active(true).build());
            categoryRepository.save(Category.builder().name("Clothing").description("Apparel and fashion").active(true).build());
            categoryRepository.save(Category.builder().name("Home & Garden").description("Home improvement and garden supplies").active(true).build());
            log.info("Seeded default product categories");
        }

        if (brandRepository.count() == 0) {
            brandRepository.save(Brand.builder().name("Generic").description("Generic brand").active(true).build());
            brandRepository.save(Brand.builder().name("SpringMart Select").description("SpringMart house brand").active(true).build());
            log.info("Seeded default product brands");
        }
    }
}
