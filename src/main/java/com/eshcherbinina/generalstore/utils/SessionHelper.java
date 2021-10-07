package com.eshcherbinina.generalstore.utils;

import com.eshcherbinina.generalstore.dto.Cart;
import com.eshcherbinina.generalstore.dto.CartItem;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionHelper {
    public static Cart cart;
    static {
        cart = new Cart();
        CartItem i = new CartItem();
        i.setId(1);
        i.setQuantity(1);
        i.setTitle("Cat Toy");
        cart.put(1l, i);
    }

    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            //Set<Role> auth = authentication.getAuthorities();
            return currentUserName;
        }
        return "";
    }
}
