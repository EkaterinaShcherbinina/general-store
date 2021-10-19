package com.eshcherbinina.generalstore.dto;

import com.eshcherbinina.generalstore.dao.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
@Builder
@AllArgsConstructor
public class CartItem {
    @Positive
    private long id;

    private String title;

    @Positive
    private int quantity;

    public CartItem(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
    }

    public CartItem() {
    }

    public void addItem() {
        this.quantity++;
    }

    public void removeItem() {
        if(this.quantity > 0)
            this.quantity--;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
