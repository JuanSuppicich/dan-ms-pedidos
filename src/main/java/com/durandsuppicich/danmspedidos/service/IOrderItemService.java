package com.durandsuppicich.danmspedidos.service;

import com.durandsuppicich.danmspedidos.domain.Order;
import com.durandsuppicich.danmspedidos.domain.OrderItem;

import java.util.List;

public interface IOrderItemService {

    Order post(OrderItem orderItem, Integer orderId);

    OrderItem getById(Integer orderId, Integer id);

    List<OrderItem> getByOrderId(Integer orderId);

    void put(OrderItem orderItem, Integer orderId, Integer id);

    void delete(Integer orderId, Integer id);
}
