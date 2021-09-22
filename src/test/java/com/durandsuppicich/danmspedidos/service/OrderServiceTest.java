package com.durandsuppicich.danmspedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;

import com.durandsuppicich.danmspedidos.DanMsPedidosApplicationTests;
import com.durandsuppicich.danmspedidos.repository.IOrderJpaRepository;
import com.durandsuppicich.danmspedidos.domain.OrderItem;
import com.durandsuppicich.danmspedidos.domain.Construction;
import com.durandsuppicich.danmspedidos.domain.Order;
import com.durandsuppicich.danmspedidos.domain.Product;
import com.durandsuppicich.danmspedidos.exception.http.BadRequestException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = DanMsPedidosApplicationTests.class)
public class OrderServiceTest {

    @Autowired
    IOrderService orderService;


    @MockBean
    IProductService productService;

    @MockBean
    IOrderJpaRepository orderRepository;

    Order order;

    @BeforeEach
    public void setUp() {

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
    public void post_DataOk_AcceptedOrder() {

        when(productService.getAvailableStock(any(Product.class))).thenReturn(5);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.post(order);

        assertEquals(1, (int) result.getState().getId());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void post_CustomerWithoutBalanceButIsLowRisk_AcceptedOrder() {

        when(productService.getAvailableStock(any(Product.class))).thenReturn(5);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.post(order);

        assertEquals(1, (int) result.getState().getId());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void post_ProductWithoutStock_PendingOrder() {

        OrderItem oi3 = new OrderItem(6, 100.0, new Product());
        order.getItems().add(2, oi3);

        when(productService.getAvailableStock(any(Product.class))).thenReturn(5);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.post(order);

        assertEquals(2, (int) result.getState().getId());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void post_CustomerWithoutBalanceAndIsNotLowRisk_BadRequestException() {

        when(productService.getAvailableStock(any(Product.class))).thenReturn(5);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        assertThrows(BadRequestException.class, () -> orderService.post(order));
    }
}
