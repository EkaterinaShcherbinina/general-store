package com.eshcherbinina.generalstore.service;

import com.eshcherbinina.generalstore.dto.Cart;

public interface ICartService {
    void addItemToCart(long productId, Cart cart);

    void clearCart(Cart cart);
}
