package com.eshcherbinina.generalstore.dto;

import com.eshcherbinina.generalstore.dao.entity.OrderProduct;
import com.eshcherbinina.generalstore.dao.entity.Product;
import lombok.Data;

@Data
public class OrderItemDTO {

    private String title;

    private int quantity;

    private double price;

    private long productId;

    public OrderItemDTO(OrderProduct orderProduct) {
        this.title = orderProduct.getProduct().getTitle();
        this.quantity = orderProduct.getQuantity();
        this.price = orderProduct.getPrice();
        this.productId = orderProduct.getProduct().getId();
    }
}
