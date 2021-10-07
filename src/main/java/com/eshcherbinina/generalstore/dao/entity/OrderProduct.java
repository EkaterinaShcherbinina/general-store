package com.eshcherbinina.generalstore.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="order_product")
@AssociationOverrides({
        @AssociationOverride(name="pk.order",
            joinColumns = @JoinColumn(name="orderId")),
        @AssociationOverride(name="pk.product",
            joinColumns = @JoinColumn(name="productId"))
})
public class OrderProduct {
    @Column
    private int quantity;

    @Column
    private double price;

    @EmbeddedId
    private OrderProductId pk = new OrderProductId();

    public Order getOrder() {
        return pk.getOrder();
    }

    public void setOrder(Order order) {
        pk.setOrder(order);
    }

    public Product getProduct() {
        return pk.getProduct();
    }

    public void setProduct(Product product) {
        pk.setProduct(product);
    }
}
