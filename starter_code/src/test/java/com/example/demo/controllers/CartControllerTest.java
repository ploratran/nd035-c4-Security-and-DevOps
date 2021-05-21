package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ItemControllerTest.class);

    private CartController cartController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @BeforeEach
    public void setup() {
        log.info("Setup called");

        cartController = new CartController(userRepository, cartRepository, itemRepository);

        when(userRepository.findByUsername("Plora")).thenReturn(getUser());
        when(itemRepository.findById(1L)).thenReturn(getItem());
    }

    @Test
    @Order(1)
    public void testAddToCart() {
        // add new ModifyCartRequest item to cart
        // Item(id, name, price, description)
        // Cart(id, List<Item> items, User user)
        // ModifyCartRequest(itemId, username, quantity):
        ResponseEntity<Cart> response = cartController.addToCart(new ModifyCartRequest(1L, "Plora", 1));
        Cart actualCart = response.getBody();

        assertNotNull(actualCart);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // test properties of Cart:
        assertEquals("Plora", actualCart.getUser().getUsername());
        assertEquals(2, actualCart.getItems().size()); // cart should have 2 items after adding new item to cart
        assertEquals(2, actualCart.getItems().stream().count()); // should have 2 items in cart after adding
        assertEquals(1, actualCart.getUser().getId()); // cart's user id should be 1 due to same user's cart
        assertEquals(1, actualCart.getItems().get(0).getId()); // first item in List<Item> has id of 1
    }

    @Test
    @Order(2)
    public void testAddToCartWithInvalidInput() {
        ResponseEntity<Cart> response = cartController.addToCart(new ModifyCartRequest(1L, "", 1));

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(3)
    public void testRemoveFromCart() {
        ResponseEntity<Cart> response = cartController.removeFromCart(new ModifyCartRequest(1L, "Plora", 1));
        Cart actualCart = response.getBody();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // test for cart's properties:
        assertEquals(0, actualCart.getItems().size()); // cart should be 0 after remove 1 item
        assertEquals(0, actualCart.getItems().stream().count()); // same with using size()
        assertEquals(0, actualCart.getTotal().intValue()); // cart's total should be 0 when there's no item
        assertEquals("Plora", actualCart.getUser().getUsername());
    }

    @Test
    @Order(4)
    public void testRemoveCartWithInvalidInput() {
        ResponseEntity<Cart> response = cartController.removeFromCart(new ModifyCartRequest(1L, "", 1));

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /** helper functions: */
    private static User getUser() {
        User user = new User(1L, "Plora", "testPassword");
        user.setCart(getCart(user));

        return user;
    }

    private static Cart getCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.addItem(getItem().orElse(null));
        return cart;
    }

    private static Optional<Item> getItem() {
        Item item = new Item(1L, "Charmin Ultra Soft Toilet Paper", new BigDecimal(19.99), "18 Mega Rolls");

        return Optional.of(item);
    }
}
