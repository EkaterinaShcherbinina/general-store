package com.eshcherbinina.generalstore.unit;

import com.eshcherbinina.generalstore.dao.entity.*;
import com.eshcherbinina.generalstore.dao.repository.OrderRepository;
import com.eshcherbinina.generalstore.dao.repository.ProductRepository;
import com.eshcherbinina.generalstore.dao.repository.UserRepository;
import com.eshcherbinina.generalstore.dto.Cart;
import com.eshcherbinina.generalstore.dto.CartItem;
import com.eshcherbinina.generalstore.dto.OrderDTO;
import com.eshcherbinina.generalstore.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    OrderService orderService;

    private User user = null;
    Order orderOne = null;
    Product prodOne = null;
    Product prodTwo = null;

    @Before
    public void setUp() {
        user = User.builder()
                .id(1)
                .email("test@gmail.com")
                .password("password12345").build();
        orderOne = Order.builder()
                .id(1)
                .orderDate(new Date())
                .status(OrderStatus.in_progress).build();

        prodOne = Product.builder()
                .id(3)
                .title("Nail gun")
                .price(23.45)
                .quantity(7)
                .build();
        prodTwo = Product.builder()
                .id(6)
                .title("Earbuds with active noise cancellation")
                .price(23.45)
                .quantity(7)
                .build();
        OrderProduct orderProductOne = new OrderProduct();
        orderProductOne.setOrder(orderOne);
        orderProductOne.setProduct(prodOne);
        OrderProduct orderProductTwo = new OrderProduct();
        orderProductTwo.setOrder(orderOne);
        orderProductTwo.setProduct(prodTwo);
        HashSet<OrderProduct> orderProducts = new HashSet<>();
        orderProducts.add(orderProductOne);
        orderProducts.add(orderProductTwo);

        orderOne.setOrderProducts(orderProducts);
        HashSet<Order> orders = new HashSet<>();
        orders.add(orderOne);
        user.setOrders(orders);
    }

    @Test
    public void getOrderHistoryHappy() {
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(user);
        List<OrderDTO> list = orderService.getOrderHistory();
        assertEquals(1, list.size());
        assertEquals(OrderStatus.in_progress, list.get(0).getStatus());
        assertEquals(1, list.get(0).getId());
        verify(userRepository, times(1)).findByEmail(Mockito.anyString());
    }

    @Test
    public void getOrderByIdTestHappy() {
        int id = 1;
        when(orderRepository.findById(id)).thenReturn(orderOne);

        OrderDTO actualOrder = orderService.getOrder(id);

        assertEquals(1, actualOrder.getId());
        verify(orderRepository, times(id)).findById(id);
    }

    @Test
    public void cancelOrderTestHappy() {
        int id = 1;
        when(orderRepository.findById(id)).thenReturn(orderOne);
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(user);
        orderService.cancelOrder(id);

        verify(userRepository, times(id)).save(user);
    }

    /*@Test
    public void createOrder() {
        Cart cart = new Cart();
        CartItem cartItemOne = CartItem.builder()
                .id(1)
                .quantity(2)
                .title("Nail gun")
                .build();
        CartItem cartItemTwo = CartItem.builder()
                .id(2)
                .quantity(2)
                .title("Earbuds with active noise cancellation")
                .build();

        cart.put(1l, cartItemOne);
        cart.put(2l, cartItemTwo);
        List<Product> products = new ArrayList<>();
        products.add(prodOne);
        products.add(prodTwo);

        orderService.createOrder(cart);

        when(productRepository.findAllById(cart.keySet())).thenReturn(products);
        verify(productRepository, times(products.size())).save(Mockito.any());
        verify(orderRepository, times(1)).save(Mockito.any());
    }*/
}
