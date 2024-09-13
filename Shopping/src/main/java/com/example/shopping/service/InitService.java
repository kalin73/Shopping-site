package com.example.shopping.service;

import com.example.shopping.model.entity.CategoryEntity;
import com.example.shopping.model.entity.ProductEntity;
import com.example.shopping.model.entity.SpecificationsEntity;
import com.example.shopping.model.enums.Category;
import com.example.shopping.repository.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class InitService {
    private final ProductRepository productRepository;
    private final SpecificationsRepository specificationsRepository;
    private final Gson gson;
    private final ShoppingItemRepository shoppingItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final CategoryRepository categoryRepository;

    @Value("${env.dev}")
    private boolean env;

    public InitService(ProductRepository productRepository, Gson gson,
                       SpecificationsRepository specificationsRepository, ShoppingItemRepository shoppingItemRepository,
                       OrderItemRepository orderItemRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.specificationsRepository = specificationsRepository;
        this.gson = gson;
        this.shoppingItemRepository = shoppingItemRepository;
        this.orderItemRepository = orderItemRepository;
        this.categoryRepository = categoryRepository;
    }

    public void initDb() {
        if (env) {
            if (categoryRepository.count() == 0) {
                for (Category c : Category.values()) {
                    this.categoryRepository.save(new CategoryEntity(c));
                }
            }

            try (InputStream inputStream = new ClassPathResource("products.json").getInputStream()) {
                List<ProductEntity> products = Arrays.stream(gson.fromJson(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8),
                        ProductEntity[].class)).toList();

                List<SpecificationsEntity> specs = new ArrayList<>();

                for (ProductEntity product : products) {
                    specs.addAll(product.getSpecs());
                }

                inputStream.close();

                if (productRepository.count() != products.size() || specificationsRepository.count() != specs.size()) {
                    this.orderItemRepository.deleteAll();
                    this.shoppingItemRepository.deleteAll();
                    this.specificationsRepository.deleteAll();
                    this.productRepository.deleteAll();

                    products = this.productRepository.saveAll(products);

                    for (ProductEntity product : products) {
                        product.getSpecs().forEach(sp -> {
                            sp.setProduct(product);
                            this.specificationsRepository.save(sp);
                        });
                    }
                }
            } catch (IOException e) {
                System.out.println("File doesn't exist!");
            }
        }
    }
}
