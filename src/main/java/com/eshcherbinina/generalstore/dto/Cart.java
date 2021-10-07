package com.eshcherbinina.generalstore.dto;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;

@Component
@Scope("session")
public class Cart extends HashMap<Long, CartItem> {
}
