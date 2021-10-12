package com.eshcherbinina.generalstore.service;

import com.eshcherbinina.generalstore.dao.entity.*;
import com.eshcherbinina.generalstore.dao.repositiry.OrderRepository;
import com.eshcherbinina.generalstore.dao.repositiry.ProductRepository;
import com.eshcherbinina.generalstore.dao.repositiry.UserRepository;
import com.eshcherbinina.generalstore.dto.Cart;
import com.eshcherbinina.generalstore.dto.CartItem;
import com.eshcherbinina.generalstore.dto.OrderDTO;
import com.eshcherbinina.generalstore.exception.*;
import com.eshcherbinina.generalstore.utils.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService{

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private MessageSource messageSource;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository,
                        ProductRepository productRepository, MessageSource messageSource) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.messageSource = messageSource;
    }

    @Override
    public List<OrderDTO> getOrderHistory() {
        String userName = SessionHelper.getCurrentUsername();
        if(userName == null) ExceptionCreator.throwException(ErrorType.USER_NOT_AUTHORIZED,
                "api.error.user.not.authorized", "api.error.order.creation.failed", null);
        User user = userRepository.findByEmail(userName);
        if(user == null) ExceptionCreator.throwException(ErrorType.ENTITY_NOT_FOUND,
                "api.error.user.not.found", "api.error.order.creation.failed", null);
        return user.getOrders().stream().map(order -> new OrderDTO(order)).collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrder(long id) {
        Order order =  orderRepository.findById(id);
        if(order == null) ExceptionCreator.throwException(ErrorType.ENTITY_NOT_FOUND,
                "api.error.order.not.found", "api.error.order.not.found.detail", new Object[]{id});
       return new OrderDTO(order);
    }

    @Override
    public void cancelOrder(long id) {
        Order order = orderRepository.findById(id);
        if(order == null) throw new ResponseStatusException(
                HttpStatus.NO_CONTENT,
                messageSource.getMessage("api.error.order.cancel.failed", null, Locale.ENGLISH));
        if(order.getStatus() != OrderStatus.in_progress) throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                messageSource.getMessage("api.response.order.cancellation.failed", null, Locale.ENGLISH));

        String userName = SessionHelper.getCurrentUsername();
        User user = userRepository.findByEmail(userName);
        user.getOrders().remove(order);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void createOrder(Cart cart) {
        OrderProduct orderProduct = new OrderProduct();

        String userName = SessionHelper.getCurrentUsername();
        if (userName.isEmpty()) ExceptionCreator.throwException(ErrorType.USER_NOT_AUTHORIZED,
                "api.error.user.not.authorized", "api.error.order.creation.username.failed", null);
        User user = userRepository.findByEmail(userName);
        if(user == null) ExceptionCreator.throwException(ErrorType.ENTITY_NOT_FOUND,
                "api.error.user.not.found", "api.error.order.creation.failed", null);
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        Order order = Order.builder()
                .orderDate(sqlDate)
                .status(OrderStatus.in_progress)
                .build();
        order.assignUser(user);

        List<Product> products = productRepository.findAllById(cart.keySet());

        products.stream().forEach(product -> {
            CartItem cartItem = cart.get(product.getId());
            int quantDiff = product.getQuantity() - cartItem.getQuantity();
            if (quantDiff >= 0) {
                product.setQuantity(quantDiff);
                productRepository.save(product);

                orderProduct.setQuantity(cartItem.getQuantity());
                orderProduct.setPrice(product.getPrice());
                orderProduct.setOrder(order);
                orderProduct.setProduct(product);
                order.getOrderProducts().add(orderProduct);
            } else ExceptionCreator.throwException(ErrorType.ORDER_CREATION_FAILED, "api.error.order.creation.failed",
                    "api.error.order.creation.failed.detail", new Object[] {product.getId(), cartItem.getQuantity()});
        });

        orderRepository.save(order);
    }
}
