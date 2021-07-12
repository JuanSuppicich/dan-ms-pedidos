package com.durandsuppicich.danmspedidos.service;

import java.util.List;
import java.util.Optional;

import com.durandsuppicich.danmspedidos.domain.Order;

public interface IOrderService {

    Order post(Order order);

    List<Order> getAll();

    Optional<Order> getById(Integer id);

    Optional<Order> getByConstructionId(Integer constructionId);

    List<Order> getByState(String state);

    List<Order> getByCuit(String cuit);

    void put(Order order, Integer id);

    void patch(Order partialOrder, Integer id);

    void delete(Integer id);
}
