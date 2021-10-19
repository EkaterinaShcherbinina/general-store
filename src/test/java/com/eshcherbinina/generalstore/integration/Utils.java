package com.eshcherbinina.generalstore.integration;

import org.springframework.http.*;

public class Utils {
    public static final String BASE_URL = "http://localhost:";
    public static final String LOGIN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";
    public static final String PRODUCT_RESOURCE = "/product";
    public static final String CART_RESOURCE = "/cart";
    public static final String CART_CHECKOUT = "/cart/checkout";
    public static final String CART_CONTENT= "/cart/content";

    public static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    public static HttpHeaders getHeadersWithToken(String token) {
        HttpHeaders headers = getHeaders();
        headers.set("Authorization", token);
        return headers;
    }
}
