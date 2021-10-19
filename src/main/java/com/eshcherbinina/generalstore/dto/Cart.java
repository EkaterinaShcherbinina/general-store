package com.eshcherbinina.generalstore.dto;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Scope("session")
public class Cart extends HashMap<Long, CartItem> {
}
