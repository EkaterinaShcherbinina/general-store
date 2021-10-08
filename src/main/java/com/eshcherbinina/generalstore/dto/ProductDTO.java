package com.eshcherbinina.generalstore.dto;

import com.eshcherbinina.generalstore.dao.entity.Product;
import com.eshcherbinina.generalstore.restController.ProductController;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
public class ProductDTO extends RepresentationModel {

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
        addLinks();
    }

    public Product toEntity() {
        return Product.builder()
                .id(this.id)
                .title(this.title)
                .quantity(this.quantity)
                .price(this.price).build();
    }

    public void addLinks() {
        add(linkTo(methodOn(ProductController.class).getProduct(id)).withSelfRel());
        add(linkTo(ProductController.class).withRel("product"));
    }
}
