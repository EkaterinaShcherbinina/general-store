package com.eshcherbinina.generalstore.dto;

import com.eshcherbinina.generalstore.dao.entity.Order;
import com.eshcherbinina.generalstore.dao.entity.OrderProduct;
import com.eshcherbinina.generalstore.dao.entity.OrderStatus;
import com.eshcherbinina.generalstore.restController.OrderController;
import com.eshcherbinina.generalstore.restController.ProductController;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
public class OrderDTO extends RepresentationModel {
    private long id;

    private Date orderDate;

    private OrderStatus status;

    private Set<OrderItemDTO> orderItems;

    private double total;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
        this.orderItems = order.getOrderProducts().stream().map(item ->
                new OrderItemDTO(item)).collect(Collectors.toSet());
        this.total = order.getOrderProducts().stream().mapToDouble(OrderProduct::getPrice)
                .sum();

        addLinks();
    }

    public Order toEntity() {
        return Order.builder()
                .id(this.id)
                .orderDate(this.orderDate)
                .status(this.status).build();
    }

    public void addLinks() {
        add(linkTo(methodOn(OrderController.class).getOrder(id)).withSelfRel());
        add(linkTo(methodOn(OrderController.class).getOrderHistory()).withRel("history"));
    }
}
