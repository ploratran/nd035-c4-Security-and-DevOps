package com.example.demo.controllers;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ItemControllerTest.class);

    private ItemController itemController;

    private ItemRepository itemRepository = mock(ItemRepository.class);; // mock Item Repository

    @Before
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
    public void whenFindAllItems_thenAllItemsAreReturned() {
        ResponseEntity<List<Item>> response = itemController.getItems();

        List<Item> items = response.getBody();

        assertNotNull(items);
    }

}
