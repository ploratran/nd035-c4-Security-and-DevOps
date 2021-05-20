package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;

import java.math.BigDecimal;
import java.util.Optional;

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

    private static User getUser() {
        User user = new User(1L, "Plora", "testPassword");

        return user;
    }

    private static Optional<Item> getItem() {
        Item item = new Item(1L, "Charmin Ultra Soft Toilet Paper", new BigDecimal(19.99), "18 Mega Rolls");
        return Optional.of(item);
    }
}
