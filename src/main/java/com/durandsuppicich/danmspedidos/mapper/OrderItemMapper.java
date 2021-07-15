package com.durandsuppicich.danmspedidos.mapper;

import com.durandsuppicich.danmspedidos.domain.OrderItem;
import com.durandsuppicich.danmspedidos.domain.Product;
import com.durandsuppicich.danmspedidos.dto.item.OrderItemDto;
import com.durandsuppicich.danmspedidos.dto.item.OrderItemPostDto;
import com.durandsuppicich.danmspedidos.dto.item.OrderItemPutDto;

public class OrderItemMapper implements IOrderItemMapper {

    @Override
    public OrderItem map(OrderItemPostDto orderItemDto) {
        OrderItem orderItem = new OrderItem();

        Product product = new Product();
        product.setId(orderItemDto.getProductId());

        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setPrice(orderItemDto.getPrice());
        orderItem.setProduct(product);

        return orderItem;
    }

    @Override
    public OrderItem map(OrderItemPutDto orderItemDto) {
        OrderItem orderItem = new OrderItem();

        orderItem.setId(orderItemDto.getId());
        orderItem.setQuantity(orderItemDto.getQuantity());

        return orderItem;
    }

    @Override
    public OrderItemDto mapToDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();

        orderItemDto.setId(orderItem.getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setProductDescription(orderItem.getProduct().getDescription());

        return orderItemDto;
    }
}
