package com.durandsuppicich.danmspedidos.controller;

import java.util.List;
import java.util.Optional;

import com.durandsuppicich.danmspedidos.domain.OrderItem;
import com.durandsuppicich.danmspedidos.domain.Order;
import com.durandsuppicich.danmspedidos.exception.BadRequestException;
import com.durandsuppicich.danmspedidos.exception.NotFoundException;
import com.durandsuppicich.danmspedidos.service.IOrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/order")
@Api(value = "OrderController")
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ApiOperation(value = "Creates a new order")
    public ResponseEntity<Order> post(@RequestBody Order order) {

        List<OrderItem> items = order.getItems();

        if ((items != null) && (items.size() > 0)) {

            if (order.getConstruction() != null) {

                boolean validItems = items
                        .stream()
                        .allMatch(oi -> oi.getProduct() != null && oi.getQuantity() != null && oi.getQuantity() > 0);

                if (validItems) {

                    Order body = orderService.post(order);
                    return ResponseEntity.ok(body);

                } else {
                    throw new BadRequestException("Detalles: " + items); // TODO exception (change this)
                }
            } else {
                throw new BadRequestException("Obra: " + order.getConstruction()); // TODO exception (change this)
            }
        } else {
            throw new BadRequestException("Detalles: " + items); // TODO exception (change this)
        }
    }

//    @GetMapping
//    @ApiOperation(value = "Retrieves all orders")
//    public ResponseEntity<List<Order>> getAll() {
//
//        List<Order> body = orderService.getAll();
//
//        return ResponseEntity.ok(body);
//    }

    @GetMapping
    @ApiOperation(value = "Retrieves all orders")
    public String getAll() {
        return "Order Controller";
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Retrieves a order based on the given id")
    public ResponseEntity<Order> getById(@PathVariable Integer id) {

        Optional<Order> body = orderService.getById(id);

        if (body.isPresent()) {
            return ResponseEntity.ok(body.get());
        } else {
            throw new NotFoundException("Pedido no encontrado. Id: " + id); // TODO exception (change this)
        }
    }

    @GetMapping(params = "constructionId")
    @ApiOperation(value = "Retrieves a order based on the given construction site id")
    public ResponseEntity<Order> getByConstructionId(
            @RequestParam(name = "constructionId") Integer constructionId) {

        Optional<Order> body = orderService.getByConstructionId(constructionId);

        if (body.isPresent()) {
            return ResponseEntity.ok(body.get());
        } else {
            throw new NotFoundException("Pedido no encontrado. Id Obra: " + constructionId); // TODO exception (change this)
        }
    }

    @GetMapping(params = "state")
    @ApiOperation(value = "Retrieves an order list based on the given order state")
    public ResponseEntity<List<Order>> getByState(@RequestParam(name = "state") String state) {

        List<Order> body = orderService.getByState(state);

        return ResponseEntity.ok(body);
    }
    
    @GetMapping(params = "cuit")
    @ApiOperation(value = "Retrieves an order list based on the given customer cuit")
    public ResponseEntity<List<Order>> getByCuit(@RequestParam(name = "cuit") String cuit) {

        List<Order> body = orderService.getByCuit(cuit);

        return ResponseEntity.ok(body);
    }
    /*
        Falta buscar pedido por id de cliente o cuit
    */

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Updates an order based on the given id")
    public ResponseEntity<Order> put(@RequestBody Order order, @PathVariable Integer id) {

        orderService.put(order, id);

        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{id}")
    @ApiOperation(value = "Updates the order state based on the given id")
    public ResponseEntity<Order> patch(@RequestBody Order partialOrder, @PathVariable Integer id) {
        
        orderService.patch(partialOrder, id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Deletes an order based on the given id")
    public ResponseEntity<Order> delete(@PathVariable Integer id) {

        orderService.delete(id);

        return ResponseEntity.ok().build();
    }


}