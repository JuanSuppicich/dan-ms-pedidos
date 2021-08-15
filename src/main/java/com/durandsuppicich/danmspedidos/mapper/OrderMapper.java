package com.durandsuppicich.danmspedidos.mapper;

import com.durandsuppicich.danmspedidos.domain.Construction;
import com.durandsuppicich.danmspedidos.domain.Order;
import com.durandsuppicich.danmspedidos.domain.OrderItem;
import com.durandsuppicich.danmspedidos.domain.OrderState;
import com.durandsuppicich.danmspedidos.dto.item.OrderItemPostDto;
import com.durandsuppicich.danmspedidos.dto.order.OrderDto;
import com.durandsuppicich.danmspedidos.dto.order.OrderPatchDto;
import com.durandsuppicich.danmspedidos.dto.order.OrderPostDto;
import com.durandsuppicich.danmspedidos.dto.order.OrderPutDto;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper implements IOrderMapper {

    private final IOrderItemMapper orderItemMapper;

    public OrderMapper(IOrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public Order map(OrderPostDto orderDto) {
        Order order = new Order();

        Construction construction = new Construction();
        construction.setId(orderDto.getConstructionId());

        List<OrderItem> items = new ArrayList<>();

        for (OrderItemPostDto orderItemDto : orderDto.getItems()) {
            items.add(orderItemMapper.map(orderItemDto));
        }

        order.setShippingDate(orderDto.getShippingDate());
        order.setConstruction(construction);
        order.setItems(items);

        return order;
    }

    @Override
    public Order map(OrderPutDto orderDto) {
        Order order = new Order();

        Construction construction = new Construction();
        construction.setId(orderDto.getConstructionId());

        order.setId(orderDto.getId());
        order.setShippingDate(orderDto.getShippingDate());
        order.setConstruction(construction);

        return order;
    }

    @Override
    public Order map(OrderPatchDto orderDto) {
        Order order = new Order();

        OrderState orderState = new OrderState();
        orderState.setId(orderDto.getOrderStateId());

        order.setId(orderDto.getId());
        order.setState(orderState);

        return order;
    }

    @Override
    public OrderDto mapToDto(Order order) {
        OrderDto orderDto = new OrderDto();

        Double totalPrice = order
                .getItems()
                .stream()
                .mapToDouble(oi -> oi.getQuantity() * oi.getPrice())
                .sum();

        orderDto.setId(order.getId());
        orderDto.setShippingDate(order.getShippingDate());
        orderDto.setStateDescription(order.getState().getDescription());
        orderDto.setStateId(order.getState().getId());
        orderDto.setConstructionDescription(order.getConstruction().getDescription());
        orderDto.setItemCount(order.getItems().size());
        orderDto.setTotalPrice(totalPrice);

        return orderDto;
    }

    @Override
    public List<OrderDto> mapToDto(List<Order> orders) {
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            orderDtos.add(mapToDto(order));
        }

        return orderDtos;
    }
}
