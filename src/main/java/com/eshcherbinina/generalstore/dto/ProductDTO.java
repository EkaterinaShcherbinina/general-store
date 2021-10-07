package com.eshcherbinina.generalstore.dto;

import com.eshcherbinina.generalstore.dao.entity.Product;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
public class ProductDTO {

    private long id;

    @NotEmpty
    private String title;

    @PositiveOrZero
    private int quantity;

    @Positive
    private double price;

    public ProductDTO() {

    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.quantity = product.getQuantity();
        this.price = product.getPrice();
    }

    public Product toEntity() {
        return Product.builder()
                .id(this.id)
                .title(this.title)
                .quantity(this.quantity)
                .price(this.price).build();
    }
}
