package com.durandsuppicich.danmspedidos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.ArrayList;

import com.durandsuppicich.danmspedidos.DanMsPedidosApplicationTests;
import com.durandsuppicich.danmspedidos.domain.OrderItem;
import com.durandsuppicich.danmspedidos.domain.Construction;
import com.durandsuppicich.danmspedidos.domain.Order;
import com.durandsuppicich.danmspedidos.domain.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = DanMsPedidosApplicationTests.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    String puerto;

    Order order;

    String url;

    @BeforeEach
    public void setUp() {

        url = "http://localhost:" + puerto + "/api/order";

        Construction construction = new Construction();

        construction.setId(1);

        Product p1 = new Product();
        Product p2 = new Product();
        Product p3 = new Product();

        p1.setId(1);
        p2.setId(2);
        p3.setId(3);

        OrderItem oi1 = new OrderItem(1, 100.0, p1);
        OrderItem oi2 = new OrderItem(2, 50.0, p2);
        OrderItem oi3 = new OrderItem(3, 100.0, p3);

        order = new Order();
        order.setShippingDate(Instant.now());
        order.setConstruction(construction);
        order.setItems(new ArrayList<>());
        order.addOrderItem(oi1);
        order.addOrderItem(oi2);
        order.addOrderItem(oi3);
    }

    @Test
    public void post_OrderOk_Ok() {

        HttpEntity<Order> request = new HttpEntity<>(order);
        ResponseEntity<Order> response = testRestTemplate.exchange(url, HttpMethod.POST, request, Order.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void post_OrderWithoutConstruction_BadRequest() {

        order.setConstruction(null);

        HttpEntity<Order> request = new HttpEntity<>(order);
        ResponseEntity<Order> response = testRestTemplate.exchange(url, HttpMethod.POST, request, Order.class);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void post_OrderWithoutItems_BadRequest() {

        order.setItems(new ArrayList<>());

        HttpEntity<Order> request = new HttpEntity<>(order);
        ResponseEntity<Order> response = testRestTemplate.exchange(url, HttpMethod.POST, request, Order.class);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void post_OrderWithItemsWithoutInfo_BadRequest() {

        order.getItems().add(2, new OrderItem());

        HttpEntity<Order> request = new HttpEntity<>(order);
        ResponseEntity<Order> response = testRestTemplate.exchange(url, HttpMethod.POST, request, Order.class);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void post_OrderWithItemsWithNegativeQuantity_BadRequest() {

        OrderItem oi2 = new OrderItem(-2, 50.0, new Product());
        OrderItem oi3 = new OrderItem(-3, 100.0, new Product());

        order.getItems().add(1, oi2);
        order.getItems().add(2, oi3);

        HttpEntity<Order> request = new HttpEntity<>(order);
        ResponseEntity<Order> response = testRestTemplate.exchange(url, HttpMethod.POST, request, Order.class);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
