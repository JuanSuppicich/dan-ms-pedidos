package com.durandsuppicich.danmspedidos.controller;

import com.durandsuppicich.danmspedidos.domain.Order;
import com.durandsuppicich.danmspedidos.domain.OrderItem;
import com.durandsuppicich.danmspedidos.exception.NotFoundException;
import com.durandsuppicich.danmspedidos.service.IOrderItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/order/{orderId}/item")
@Api(value = "OrderItemController")
public class OrderItemController {

    private final IOrderItemService orderItemService;

    public OrderItemController(IOrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping
    @ApiOperation(value = "Adds an item to an existing order")
    public ResponseEntity<Order> post(@PathVariable Integer orderId, @RequestBody OrderItem orderItem) {

        Order body = orderItemService.post(orderItem, orderId);

        return ResponseEntity.ok(body);
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Retrieves an order item based on the given id")
    public ResponseEntity<OrderItem> getById(@PathVariable Integer orderId, @PathVariable Integer id) {

        Optional<OrderItem> body = orderItemService.getById(orderId, id);

        if (body.isPresent()) {
            return ResponseEntity.ok(body.get());
        } else {
            throw new NotFoundException("Detalle no encontrado. Id Pedido: " +
                    orderId + ", Id Detalle: " + id); // TODO exception (change this)
        }
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Updates an order item based on the given id")
    public ResponseEntity<Order> put(
            @RequestBody OrderItem orderItem,
            @PathVariable Integer orderId,
            @PathVariable Integer id) {

        orderItemService.put(orderItem, orderId, id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Deletes an order item based on the given id")
    public ResponseEntity<Order> delete(@PathVariable Integer orderId, @PathVariable Integer id) {

        orderItemService.delete(orderId, id);

        return ResponseEntity.ok().build();
    }
}
