package com.durandsuppicich.danmspedidos.service;

import com.durandsuppicich.danmspedidos.domain.Order;
import com.durandsuppicich.danmspedidos.domain.OrderItem;

import java.util.Optional;

public interface IOrderItemService {

    Order post(OrderItem orderItem, Integer orderId);

    Optional<OrderItem> getById(Integer orderId, Integer id);

    void put(OrderItem orderItem, Integer orderId, Integer id);

    void delete(Integer orderId, Integer id);
}
