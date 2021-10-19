package com.eshcherbinina.generalstore.service;

import com.eshcherbinina.generalstore.dao.entity.Product;
import com.eshcherbinina.generalstore.dao.repository.ProductRepository;
import com.eshcherbinina.generalstore.dto.Cart;
import com.eshcherbinina.generalstore.dto.CartItem;
import com.eshcherbinina.generalstore.exception.ErrorType;
import com.eshcherbinina.generalstore.exception.ExceptionCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService implements ICartService{
    private ProductRepository productRepository;

    @Autowired
    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addItemToCart(long productId, Cart cart) {
        Product product = productRepository.findById(productId);
        if(product == null) if(product == null) ExceptionCreator.throwException(ErrorType.ENTITY_NOT_FOUND,
                "api.error.cart.new.item", "api.error.product.not.found.message", null);
        CartItem item = cart.getOrDefault(productId, new CartItem(product));
        item.addItem();
        cart.put(productId, item);
    }

    @Override
    public void clearCart(Cart cart) {
        cart.clear();
    }


}
