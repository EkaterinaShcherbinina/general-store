package com.eshcherbinina.generalstore.dto;

import com.eshcherbinina.generalstore.dao.entity.Product;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
public class CartItem {

    private long id;

    @NotEmpty
    private String title;

    @Positive
    private int quantity;

    public CartItem(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.quantity++;
    }

    public CartItem() {
    }
}
