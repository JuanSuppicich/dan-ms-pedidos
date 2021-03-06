package com.durandsuppicich.danmspedidos.mapper;

import com.durandsuppicich.danmspedidos.domain.OrderItem;
import com.durandsuppicich.danmspedidos.dto.item.OrderItemDto;
import com.durandsuppicich.danmspedidos.dto.item.OrderItemPostDto;
import com.durandsuppicich.danmspedidos.dto.item.OrderItemPutDto;

import java.util.List;

public interface IOrderItemMapper {

    OrderItem map(OrderItemPostDto orderItemDto);

    OrderItem map(OrderItemPutDto orderItemDto);

    OrderItemDto mapToDto(OrderItem orderItem);

    List<OrderItemDto> mapToDto(List<OrderItem> items);
}
