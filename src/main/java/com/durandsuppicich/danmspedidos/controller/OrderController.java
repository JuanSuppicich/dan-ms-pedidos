package com.durandsuppicich.danmspedidos.controller;

import java.net.URI;
import java.util.List;

import com.durandsuppicich.danmspedidos.domain.Order;
import com.durandsuppicich.danmspedidos.dto.order.OrderDto;
import com.durandsuppicich.danmspedidos.dto.order.OrderPatchDto;
import com.durandsuppicich.danmspedidos.dto.order.OrderPostDto;
import com.durandsuppicich.danmspedidos.dto.order.OrderPutDto;
import com.durandsuppicich.danmspedidos.exception.validation.IdsNotMatchException;
import com.durandsuppicich.danmspedidos.mapper.IOrderMapper;
import com.durandsuppicich.danmspedidos.service.IOrderService;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@RestController
@Validated
@RequestMapping("/api/orders")
@Api(value = "OrderController")
@CrossOrigin(origins = "*", methods=
        {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE})
public class OrderController {

    private final IOrderService orderService;
    private final IOrderMapper orderMapper;

    public OrderController(
            IOrderService orderService,
            IOrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    @ApiOperation(value = "Creates a new order")
    public ResponseEntity<OrderDto> post(@RequestBody @Valid OrderPostDto orderDto) {

        Order order = orderService.post(orderMapper.map(orderDto));
        OrderDto body = orderMapper.mapToDto(order);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(body.getId())
                .toUri();

        return ResponseEntity.created(location).body(body);
    }

    @GetMapping
    @ApiOperation(value = "Retrieves all orders")
    public ResponseEntity<List<OrderDto>> getAll() {

        List<Order> orders = orderService.getAll();
        List<OrderDto> body = orderMapper.mapToDto(orders);

        return ResponseEntity.ok(body);
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Retrieves an order based on the given id")
    public ResponseEntity<OrderDto> getById(@PathVariable @Positive Integer id) {

        Order order = orderService.getById(id);
        OrderDto body = orderMapper.mapToDto(order);

        return ResponseEntity.ok(body);
    }

    @GetMapping(params = "constructionId")
    @ApiOperation(value = "Retrieves an order based on the given construction site id")
    public ResponseEntity<List<OrderDto>> getByConstructionId(
            @RequestParam(name = "constructionId") @Positive Integer constructionId) {

        List<Order> orders = orderService.getByConstructionId(constructionId);
        List<OrderDto> body = orderMapper.mapToDto(orders);

        return ResponseEntity.ok(body);
    }

    @GetMapping(params = "state")
    @ApiOperation(value = "Retrieves an order list based on the given order state")
    public ResponseEntity<List<OrderDto>> getByState(
            @RequestParam(name = "state") @NotBlank @Length(max = 32) String state) {

        List<Order> orders = orderService.getByState(state);
        List<OrderDto> body = orderMapper.mapToDto(orders);

        return ResponseEntity.ok(body);
    }
    
    @GetMapping(params = "cuit")
    @ApiOperation(value = "Retrieves an order list based on the given customer cuit")
    public ResponseEntity<List<OrderDto>> getByCuit(
            @RequestParam(name = "cuit") @NotBlank @Length(min = 11, max = 11) String cuit) {

        List<Order> orders = orderService.getByCuit(cuit);
        List<OrderDto> body = orderMapper.mapToDto(orders);

        return ResponseEntity.ok(body);
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Updates an order based on the given id")
    public ResponseEntity<?> put(
            @RequestBody @Valid OrderPutDto orderDto,
            @PathVariable @Positive Integer id) {

        if (!orderDto.getId().equals(id)) { throw new IdsNotMatchException(); }

        Order order = orderMapper.map(orderDto);

        orderService.put(order, id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{id}")
    @ApiOperation(value = "Updates the order state based on the given id")
    public ResponseEntity<?> patch(
            @RequestBody @Valid OrderPatchDto orderDto,
            @PathVariable @Positive Integer id) {

        Order order = orderMapper.map(orderDto);

        orderService.patch(order, id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Deletes an order based on the given id")
    public ResponseEntity<?> delete(@PathVariable @Positive Integer id) {

        orderService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
