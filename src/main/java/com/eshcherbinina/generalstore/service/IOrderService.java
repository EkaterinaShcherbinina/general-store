package com.eshcherbinina.generalstore.service;

import com.eshcherbinina.generalstore.dto.Cart;
import com.eshcherbinina.generalstore.dto.OrderDTO;

import java.util.List;

public interface IOrderService {
    List<OrderDTO> getOrderHistory();
    OrderDTO getOrder(long id);
    void cancelOrder(long id);
    void createOrder(Cart cart);
}
