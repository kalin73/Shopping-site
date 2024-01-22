package com.example.shopping.service;

import com.example.shopping.model.entity.ProductEntity;
import com.example.shopping.model.entity.SpecificationsEntity;
import com.example.shopping.repository.OrderItemRepository;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.repository.ShoppingItemRepository;
import com.example.shopping.repository.SpecificationsRepository;
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
    private final ProductRepository productRepository;
    private final SpecificationsRepository specificationsRepository;
    private final Gson gson;
    private final ShoppingItemRepository shoppingItemRepository;
    private final OrderItemRepository orderItemRepository;

    public InitService(ProductRepository productRepository, Gson gson,
                       SpecificationsRepository specificationsRepository, ShoppingItemRepository shoppingItemRepository,
                       OrderItemRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.specificationsRepository = specificationsRepository;
        this.gson = gson;
        this.shoppingItemRepository = shoppingItemRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public void initDb() {
        try (FileReader reader = new FileReader(
                Path.of("src", "main", "resources", "static", "js", "products.json").toFile())) {
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
