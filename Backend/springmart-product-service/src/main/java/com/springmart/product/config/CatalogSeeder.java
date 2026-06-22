package com.springmart.product.config;

import com.springmart.product.entity.Category;
import com.springmart.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CatalogSeeder implements CommandLineRunner {

    private static final List<String> REQUIRED_CATEGORIES = List.of(
            "Electronics",
            "Fashion",
            "Home Appliances",
            "Books",
            "Sports",
            "Groceries"
    );

    private final CategoryRepository categoryRepository;

    @Override
    @SuppressWarnings("null")
    public void run(String... args) {
        for (String name : REQUIRED_CATEGORIES) {
            categoryRepository.findByName(name).orElseGet(() -> {
                Category saved = categoryRepository.save(
                        Category.builder()
                                .name(name)
                                .description(name + " products")
                                .active(true)
                                .build()
                );
                log.info("Seeded category: {}", name);
                return saved;
            });
        }

        categoryRepository.findAll().forEach(category -> {
            if (!REQUIRED_CATEGORIES.contains(category.getName()) && category.isActive()) {
                category.setActive(false);
                categoryRepository.save(category);
                log.info("Deactivated legacy category: {}", category.getName());
            }
        });
    }
}
