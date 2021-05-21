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
        when(itemRepository.findById(1L)).thenReturn(Optional.of(getItem()));
    }

    @Test
    @Order(1)
    public void testAddToCart() {
        ResponseEntity<Cart> response = cartController.addToCart(new ModifyCartRequest(1L, "Plora", 1));
        Cart actualCart = response.getBody();

        assertNotNull(actualCart);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // test properties of Cart:
        assertEquals("Plora", actualCart.getUser().getUsername());
        assertEquals(new BigDecimal(1L), actualCart.getId());
    }

    /** helper functions: */
    private static User getUser() {
        User user = new User(1L, "Plora", "testPassword");
        user.setCart(getCart(user));

        return user;
    }

    private static Cart getCart(User user) {
        Cart cart = new Cart();
        cart.addItem(getItem());
        cart.setUser(user);

        return cart;
    }

    private static Item getItem() {
        Item item = new Item(1L, "Charmin Ultra Soft Toilet Paper", new BigDecimal(19.99), "18 Mega Rolls");
        return item;
    }
}
