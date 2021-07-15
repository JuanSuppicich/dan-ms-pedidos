package com.durandsuppicich.danmspedidos.mapper;

import com.durandsuppicich.danmspedidos.domain.Order;
import com.durandsuppicich.danmspedidos.dto.order.OrderDto;
import com.durandsuppicich.danmspedidos.dto.order.OrderPatchDto;
import com.durandsuppicich.danmspedidos.dto.order.OrderPostDto;
import com.durandsuppicich.danmspedidos.dto.order.OrderPutDto;

import java.util.List;

public interface IOrderMapper {

    Order map(OrderPostDto orderDto);
    Order map(OrderPutDto orderDto);
    Order map(OrderPatchDto orderDto);
    OrderDto mapToDto(Order order);
    List<OrderDto> mapToDto(List<Order> orders);
}
