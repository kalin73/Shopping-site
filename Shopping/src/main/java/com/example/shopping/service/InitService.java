package com.example.shopping.service;

import com.example.shopping.model.entity.CategoryEntity;
import com.example.shopping.model.entity.ProductEntity;
import com.example.shopping.model.entity.SpecificationsEntity;
import com.example.shopping.model.enums.Category;
import com.example.shopping.repository.*;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class InitService {
    private static final String[] PRODUCTS_JSON_PATH = {"src", "main", "resources", "static", "js", "products.json"};
    private final ProductRepository productRepository;
    private final SpecificationsRepository specificationsRepository;
    private final Gson gson;
    private final ShoppingItemRepository shoppingItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final CategoryRepository categoryRepository;

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
        if (categoryRepository.count() == 0) {
            for (Category c : Category.values()) {
                this.categoryRepository.save(new CategoryEntity(c));
            }
        }

        try (FileReader reader = new FileReader(
                Path.of("Shopping", PRODUCTS_JSON_PATH).toFile())) {
            List<ProductEntity> products = Arrays.stream(gson.fromJson(reader, ProductEntity[].class)).toList();
            List<SpecificationsEntity> specs = new ArrayList<>();

            for (ProductEntity product : products) {
                specs.addAll(product.getSpecs());
            }

            reader.close();

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
