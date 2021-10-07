package com.eshcherbinina.generalstore.dto;

import com.eshcherbinina.generalstore.dao.entity.Order;
import com.eshcherbinina.generalstore.dao.entity.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class OrderDTO {
    private long id;

    private Date orderDate;

    private OrderStatus status;

    private Set<OrderItemDTO> orderItems;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
        this.orderItems = order.getOrderProducts().stream().map(item ->
                new OrderItemDTO(item)).collect(Collectors.toSet());
    }

    public Order toEntity() {
        return Order.builder()
                .id(this.id)
                .orderDate(this.orderDate)
                .status(this.status).build();
    }
}
