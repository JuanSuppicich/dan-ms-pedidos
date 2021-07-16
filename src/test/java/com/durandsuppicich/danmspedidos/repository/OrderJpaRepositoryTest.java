package com.durandsuppicich.danmspedidos.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.durandsuppicich.danmspedidos.DanMsPedidosApplicationTests;
import com.durandsuppicich.danmspedidos.domain.*;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(classes = DanMsPedidosApplicationTests.class)
public class OrderJpaRepositoryTest {
    
    @Autowired
    public IOrderJpaRepository orderRepository;

    @Test
    public void repositoryExists() {
        assertNotNull(orderRepository);
    }

    @Test
    @Sql("/datos_test.sql")
    public void findAll_StoredOrders_RetrievedOrders() {

        List<Order> orders = orderRepository.findAll();

        assertFalse(orders.isEmpty());
        assertThat(orders.size(), is(equalTo(3)));
    }

    @Test
    public void findById_StoredOrder_RetrievedOrder() {

        Optional<Order> optOrder = orderRepository.findById(1);
        
        assertTrue(optOrder.isPresent());
        assertThat(optOrder.get().getId(), is(equalTo(1)));
        assertNotNull(optOrder.get().getState());
        assertNotNull(optOrder.get().getItems());
        // assertThat(optOrder.get().getItems().size(), is(equalTo(2)));
        assertNotNull(optOrder.get().getConstruction());
        // assertThat(optOrder.get().getItems(), everyItem(hasProperty("product")));
    }

    @Test
    public void findByConstructionId_StoredOrder_RetrievedOrder() {

        //Optional<Order> optOrder = orderRepository.findByConstruction_Id(2);

        //assertTrue(optOrder.isPresent());
        //assertThat(optOrder.get().getId(), is(equalTo(2)));
    } 

    @Test
    public void post_OrderOk_StoredOrder() {

        OrderState orderState = new OrderState(1, "Aceptado");

        Construction construction = new Construction();

        construction.setId(1);

        Product p1 = new Product();
        Product p2 = new Product();

        p1.setId(1);
        p2.setId(2);

        OrderItem oi1 = new OrderItem(1, 10.0, p1);
        OrderItem oi2 = new OrderItem(2, 20.0, p2);

        Order order = new Order();
        order.setShippingDate(Instant.now());
        order.setState(orderState);
        order.setConstruction(construction);
        order.setItems(new ArrayList<>());
        order.addOrderItem(oi1);
        order.addOrderItem(oi2);

        orderRepository.save(order);

        assertThat(orderRepository.count(), is(equalTo(4L)));
    }
}
