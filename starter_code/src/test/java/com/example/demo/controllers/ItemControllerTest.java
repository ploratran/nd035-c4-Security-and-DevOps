package com.example.demo.controllers;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ItemControllerTest.class);

    private ItemController itemController;

    private ItemRepository itemRepository = mock(ItemRepository.class);; // mock Item Repository

    @BeforeEach
    public void setup() {
        log.info("setup called");

        itemController = new ItemController(itemRepository);

        // set mock data of items:
        Item item1 = createItem(1L, "Charmin Ultra Soft Toilet Paper", new BigDecimal(19.99), "18 Mega Rolls");
        Item item2 = createItem(2L, "Charmin Ultra Soft Toilet Paper ", new BigDecimal(29.99), "30 Mega Rolls");
        Item item3 = createItem(3L, "Bounty Full Sheet Paper Towels", new BigDecimal(9.99), "6 Double Rolls");

        // mock Item Repository to get all items:
        when(itemRepository.findAll()).thenReturn(Lists.list(item1, item2, item3));
    }

    // helper function to create mock data of each item:
    private Item createItem(Long id, String name, BigDecimal price, String description) {
        Item item = new Item();

        item.setId(id);
        item.setName(name);
        item.setPrice(price);
        item.setDescription(description);

        return item;
    }

    @Test
    public void testFindAllItems() {
        ResponseEntity<List<Item>> response = itemController.getItems();

        List<Item> actualItems = response.getBody();

        Item item1 = createItem(1L, "Charmin Ultra Soft Toilet Paper", new BigDecimal(19.99), "18 Mega Rolls");

        assertNotNull(actualItems);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, actualItems.size());
    }

}
