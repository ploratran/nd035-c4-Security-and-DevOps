package com.example.demo.controllers;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.UserOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ItemControllerTest.class);

    private OrderController orderController;

    private OrderRepository orderRepository = mock(OrderRepository.class);

    private UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    public void setup() {
        log.info("setup called");

        orderController = new OrderController(orderRepository, userRepository);

        // mock repositories:
        when(userRepository.findByUsername("Plora")).thenReturn(getUser());
        when(orderRepository.findByUser(getUser())).thenReturn((getUserOrders()));
    }

    @Test
    @Order(1)
    public void testSubmit() {
        ResponseEntity<UserOrder> response = orderController.submit("Plora");
        UserOrder actualOrder = response.getBody();

        assertNotNull(response);
        assertNotNull(actualOrder);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // test order information:
        assertEquals("Plora", actualOrder.getUser().getUsername());
        assertEquals("testPassword", actualOrder.getUser().getPassword());
        assertEquals(1, actualOrder.getItems().size());
        assertEquals(new BigDecimal(19.99), actualOrder.getTotal());
    }

    @Test
    @Order(2)
    public void testInvalidSubmit() {
        // test edge case with no username given:
        ResponseEntity<UserOrder> response = orderController.submit("");

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(3)
    public void testGetOrdersForUser() {
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("Plora");
        List<UserOrder> orders = response.getBody();

        assertNotNull(response);
        assertNotNull(orders);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(orders.size(), 1);
    }

    @Test
    @Order(4)
    public void testGetOrdersWithInvalidUser() {
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("");
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // helper functions:
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

    private static List<UserOrder> getUserOrders() {
        UserOrder userOrder = UserOrder.createFromCart(getUser().getCart());
        return Lists.list(userOrder);
    }
}
