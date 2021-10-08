package com.eshcherbinina.generalstore.restController;

import com.eshcherbinina.generalstore.dto.Cart;
import com.eshcherbinina.generalstore.dto.CartItem;
import com.eshcherbinina.generalstore.dto.ProductDTO;
import com.eshcherbinina.generalstore.exception.CustomException;
import com.eshcherbinina.generalstore.exception.ErrorDetails;
import com.eshcherbinina.generalstore.exception.OrderCreationFailed;
import com.eshcherbinina.generalstore.service.ICartService;
import com.eshcherbinina.generalstore.service.IOrderService;
import com.eshcherbinina.generalstore.utils.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping("/cart")
@Scope("session")
public class CartController {

    private ICartService cartService;
    private IOrderService orderService;
    private Cart cart;
    private MessageSource messageSource;

    @Autowired
    public CartController(ICartService cartService, Cart cart,
                          IOrderService orderService, MessageSource messageSource) {
        this.cartService = cartService;
        this.cart = cart;
        this.orderService = orderService;
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "/content", method = RequestMethod.GET)
    public Cart getCartContent() {
        return cart;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> addToCart(@RequestParam long productId) {
        cartService.addItemToCart(productId, cart);
        return new ResponseEntity<>(messageSource.getMessage("api.response.cart.item.creation.successful",
                null, Locale.ENGLISH), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<String> removeItem(@RequestParam long productId) {
        CartItem item = cart.get(productId);
        if(item != null) cart.remove(item);
        return new ResponseEntity<>(messageSource.getMessage("api.response.cart.item.removal.successful",
                null, Locale.ENGLISH), HttpStatus.OK);
    }

    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    public ResponseEntity<String> clearCart() {
        cartService.clearCart(cart);
        return new ResponseEntity<>(messageSource.getMessage("api.response.cart.cleaning.successful",
                null, Locale.ENGLISH), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<String> modifyCartItem(@Valid @RequestBody CartItem cartItem) {
        CartItem item = cart.get(cartItem);
        if(item != null) item.setQuantity(cartItem.getQuantity());
        return new ResponseEntity<>(messageSource.getMessage("api.response.cart.item.change.successful",
                null, Locale.ENGLISH), HttpStatus.OK);
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public ResponseEntity<String> checkout() {
        orderService.createOrder(cart);
        cartService.clearCart(cart);
        return new ResponseEntity<>(messageSource.getMessage("api.response.cart.checkout.successful",
                null, Locale.ENGLISH), HttpStatus.OK);
    }

    @ExceptionHandler(OrderCreationFailed.class)
    public ResponseEntity<ErrorDetails> exceptionHandler(CustomException ex) {
        ErrorDetails details = new ErrorDetails();
        details.setStatus(HttpStatus.CONFLICT.value());
        details.setTitle(ex.getMessage());
        details.setDetail(ex.getDetails());
        details.setType(ex.getClass().toString());
        return new ResponseEntity<>(details, HttpStatus.CONFLICT);
    }
}
