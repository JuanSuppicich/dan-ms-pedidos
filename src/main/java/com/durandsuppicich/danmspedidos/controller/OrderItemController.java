package com.durandsuppicich.danmspedidos.controller;

import com.durandsuppicich.danmspedidos.domain.OrderItem;
import com.durandsuppicich.danmspedidos.dto.item.OrderItemDto;
import com.durandsuppicich.danmspedidos.dto.item.OrderItemPostDto;
import com.durandsuppicich.danmspedidos.dto.item.OrderItemPutDto;
import com.durandsuppicich.danmspedidos.exception.validation.IdsNotMatchException;
import com.durandsuppicich.danmspedidos.mapper.IOrderItemMapper;
import com.durandsuppicich.danmspedidos.service.IOrderItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/orders/{orderId}/items")
@Api(value = "OrderItemController")
public class OrderItemController {

    private final IOrderItemService orderItemService;
    private final IOrderItemMapper orderItemMapper;

    public OrderItemController(
            IOrderItemService orderItemService,
            IOrderItemMapper orderItemMapper) {
        this.orderItemService = orderItemService;
        this.orderItemMapper = orderItemMapper;
    }

    @PostMapping
    @ApiOperation(value = "Adds an item to an existing order")
    public ResponseEntity<?> post(
            @RequestBody @Valid OrderItemPostDto orderItemDto,
            @PathVariable @Positive Integer orderId) {

        OrderItem orderItem = orderItemMapper.map(orderItemDto);

        orderItemService.post(orderItem, orderId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ApiOperation(value = "Retrieves a list of order items based on the given order id")
    public ResponseEntity<List<OrderItemDto>> getByOrderId(
            @PathVariable @Positive Integer orderId) {

        List<OrderItem> items = orderItemService.getByOrderId(orderId);

        List<OrderItemDto> body = orderItemMapper.mapToDto(items);

        return ResponseEntity.ok(body);
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Retrieves an order item based on the given id")
    public ResponseEntity<OrderItemDto> getById(
            @PathVariable @Positive Integer orderId,
            @PathVariable @Positive Integer id) {

        OrderItem orderItem = orderItemService.getById(orderId, id);

        OrderItemDto body = orderItemMapper.mapToDto(orderItem);

        return ResponseEntity.ok(body);
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Updates an order item based on the given id")
    public ResponseEntity<?> put(
            @RequestBody @Valid OrderItemPutDto orderItemDto,
            @PathVariable @Positive Integer orderId,
            @PathVariable @Positive Integer id) {

        if (!orderItemDto.getId().equals(id)) { throw new IdsNotMatchException(); }

        OrderItem orderItem = orderItemMapper.map(orderItemDto);

        orderItemService.put(orderItem, orderId, id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Deletes an order item based on the given id")
    public ResponseEntity<?> delete(
            @PathVariable @Positive Integer orderId,
            @PathVariable @Positive Integer id) {

        orderItemService.delete(orderId, id);

        return ResponseEntity.noContent().build();
    }
}
