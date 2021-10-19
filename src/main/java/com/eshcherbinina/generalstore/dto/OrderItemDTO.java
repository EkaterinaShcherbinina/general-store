package com.eshcherbinina.generalstore.dto;

import com.eshcherbinina.generalstore.dao.entity.OrderProduct;
import com.eshcherbinina.generalstore.restController.ProductController;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
public class OrderItemDTO extends RepresentationModel {

    private String title;

    private int quantity;

    private double price;

    private long productId;

    public OrderItemDTO(OrderProduct orderProduct) {
        this.title = orderProduct.getProduct().getTitle();
        this.quantity = orderProduct.getQuantity();
        this.price = orderProduct.getPrice();
        this.productId = orderProduct.getProduct().getId();

        addLinks();
    }

    public void addLinks() {
        add(linkTo(methodOn(ProductController.class).getProduct(productId)).withRel("product"));
    }
}
