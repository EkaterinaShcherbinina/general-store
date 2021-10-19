package com.eshcherbinina.generalstore.integration;

import com.eshcherbinina.generalstore.dto.Cart;
import com.eshcherbinina.generalstore.dto.CartItem;
import com.eshcherbinina.generalstore.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@AutoConfigureTestDatabase
public class AddingToCartAndOrderCheckoutTest {
    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
    private HttpHeaders headers;

    private String url = "";
    private final int defaultAmountOfProductsToAddToCart = 2;
    private final int modifiedCartItemQuantity = 6;

    @Before
    public void setUp() {
        url = Utils.BASE_URL.concat(String.valueOf(port));
        headers = Utils.getHeaders();
    }

    @Test
    public void orderCheckoutHappyTest() {
        UserDTO user = UserDTO.builder()
                .email("test@gmail.com")
                .password("password12345").build();

        signUpUser(user);
        String token = signInUser(user);
        headers.set("Authorization", token);
        List<Long> products = getProducts();
        for (int i = 0; i < defaultAmountOfProductsToAddToCart; i++)
            addToCart(products.get(i));

        Cart cart = checkCartContent();
        modifyCartItem(cart);
        checkModifiedCartContent();
        checkoutOrder();
    }

    @Test
    public void addItemsToCartForUnauthorizedUserHappyTest() {
        List<Long> products = getProducts();
        for (int i = 0; i < defaultAmountOfProductsToAddToCart; i++)
            addToCart(products.get(i));

        Cart cart = checkCartContent();
        modifyCartItem(cart);
        checkModifiedCartContent();
    }

    private void signUpUser(UserDTO user) {
        try {
            String registrationBody = new ObjectMapper().writeValueAsString(user);
            HttpEntity<String> entity = new HttpEntity<>(registrationBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    url.concat(Utils.SIGN_UP),
                    HttpMethod.POST, entity, String.class);
            assertEquals(HttpStatus.OK, response.getStatusCode());
        } catch (JsonProcessingException ex) {
            fail();
            System.out.println("signUpUser: Json Processing is failed");
        }
    }

    private String signInUser(UserDTO user) {
        String token = null;
        try {
            String loginBody = new ObjectMapper().writeValueAsString(user);
            HttpEntity<String> entity = new HttpEntity<>(loginBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url.concat(Utils.LOGIN),
                    HttpMethod.POST, entity, String.class);
            HttpHeaders headers = response.getHeaders();
            token = headers.get("Authorization").get(0);
        } catch (JsonProcessingException ex) {
            fail();
            System.out.println("signInUser: Json Processing is failed");
        }
        assertNotNull(token);
        return token;
    }

    private List<Long> getProducts() {
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url.concat(Utils.PRODUCT_RESOURCE),
                HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Long> productIds = new ArrayList<>();

        try{
            JSONArray products = new JSONArray(response.getBody());
            for (int i = 0; i < products.length(); i++)
            {
                productIds.add(products.getJSONObject(i).getLong("id"));
            }
        } catch(JSONException ex) {
            System.out.println(ex.getMessage());
        }
        assertTrue(productIds.size() >= defaultAmountOfProductsToAddToCart);
        return productIds;
    }

    private void addToCart(long productId) {
        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url.concat(Utils.CART_RESOURCE))
                .queryParam("id", productId);
        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private Cart checkCartContent() {
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Cart> responseEntity =
                restTemplate.exchange(
                        url.concat(Utils.CART_CONTENT),
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<Cart>() {}
                );

        Cart cart = responseEntity.getBody();
        assertEquals(defaultAmountOfProductsToAddToCart, cart.size());
        return cart;
    }

    private void checkModifiedCartContent() {
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Cart> responseEntity =
                restTemplate.exchange(
                        url.concat(Utils.CART_CONTENT),
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<Cart>() {}
                );

        Cart cart = responseEntity.getBody();
        Map.Entry<Long, CartItem> entry = cart.entrySet().iterator().next();
        CartItem value = entry.getValue();
        assertEquals(modifiedCartItemQuantity, value.getQuantity());
    }

    private void checkoutOrder() {
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url.concat(Utils.CART_CHECKOUT),
                HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private void modifyCartItem(Cart cart) {
        Map.Entry<Long, CartItem> entry = cart.entrySet().iterator().next();
        CartItem value = entry.getValue();
        value.setQuantity(modifiedCartItemQuantity);
        try {
            String cartItemBody = new ObjectMapper().writeValueAsString(value);
            HttpEntity<String> entity = new HttpEntity<>(cartItemBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url.concat(Utils.CART_RESOURCE),
                    HttpMethod.PUT, entity, String.class);
            assertEquals(HttpStatus.OK, response.getStatusCode());
        } catch (JsonProcessingException ex) {
            fail();
            System.out.println("modifyCartItem: Json Processing is failed");
        }
    }
}
