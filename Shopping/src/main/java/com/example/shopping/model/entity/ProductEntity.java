package com.example.shopping.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity {
    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private String description;

    @Column(nullable = false)
    private int quantity;

    @Column
    private String image;

    @Column(name = "video_url")
    private String videoUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany(targetEntity = SpecificationsEntity.class, mappedBy = "product")
    private List<SpecificationsEntity> specs;

    public ProductEntity() {
        specs = new ArrayList<>();
    }

    public ProductEntity(String productName, BigDecimal price, String description, int quantity, String image,
                         String videoUrl, CategoryEntity category, List<SpecificationsEntity> specs) {
        this();
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.image = image;
        this.videoUrl = videoUrl;
        this.category = category;
        this.specs = specs;
    }

    public String getProductName() {
        return productName;
    }

    public ProductEntity setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public ProductEntity setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImage() {
        return image;
    }

    public ProductEntity setImage(String image) {
        this.image = image;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public ProductEntity setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public ProductEntity setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }

    public List<SpecificationsEntity> getSpecs() {
        return specs;
    }

    public ProductEntity setSpecs(List<SpecificationsEntity> specs) {
        this.specs = specs;
        return this;
    }

}
