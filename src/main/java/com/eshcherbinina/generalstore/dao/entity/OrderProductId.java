package com.eshcherbinina.generalstore.dao.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Data
@Embeddable
public class OrderProductId implements java.io.Serializable{
    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;
}
