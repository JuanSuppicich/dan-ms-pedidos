package com.durandsuppicich.danmspedidos.service;

import com.durandsuppicich.danmspedidos.domain.Order;
import com.durandsuppicich.danmspedidos.domain.OrderItem;
import com.durandsuppicich.danmspedidos.exception.NotFoundException;
import com.durandsuppicich.danmspedidos.repository.IOrderItemJpaRepository;
import com.durandsuppicich.danmspedidos.repository.IOrderJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@Service
public class OrderItemService implements IOrderItemService {

    private final IOrderJpaRepository orderRepository;
    private final IOrderItemJpaRepository orderItemRepository;

    public OrderItemService(IOrderJpaRepository orderRepository, IOrderItemJpaRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Order post(OrderItem orderItem, Integer orderId) {

        Optional<Order> optOrder = orderRepository.findById(orderId);

        if (optOrder.isPresent()) {

            Order order = optOrder.get();
            order.addOrderItem(orderItem);

            return orderRepository.save(order);

        } else {
            throw new NotFoundException("Pedido inexistente. Id: " + orderId); // TODO exception (change this)
        }
    }

    @Override
    public Optional<OrderItem> getById(Integer orderId, Integer id) {

        Optional<Order> optOrder = orderRepository.findById(orderId);

        if (optOrder.isPresent()) {

            return optOrder
                    .get()
                    .getItems()
                    .stream()
                    .filter(oi -> oi.getId().equals(id))
                    .findFirst();

        } else {
            throw new NotFoundException("Pedido inexistente. Id: " + orderId); // TODO exception (change this)
        }
    }

    @Override
    public void put(OrderItem orderItem, Integer orderId, Integer id) {

        Optional<Order> orderOpt = orderRepository.findById(orderId);

        if (orderOpt.isPresent()) {

            Order order = orderOpt.get();
            List<OrderItem> items = order.getItems();

            OptionalInt optIndex = IntStream.range(0, items.size())
                    .filter(i -> items.get(i).getId().equals(id))
                    .findFirst();

            if (optIndex.isPresent()) {

                items.set(optIndex.getAsInt(), orderItem);

                orderRepository.save(order);

            } else {
                throw new NotFoundException("Detalle inexistente. Id: " + id); // TODO exception (change this)
            }
        } else {
            throw new NotFoundException("Pedido inexistente. Id: " + orderId); // TODO exception (change this)
        }
    }

    @Override
    public void delete(Integer orderId, Integer id) {

        Optional<Order> optOrder = orderRepository.findById(orderId);

        if (optOrder.isPresent()) {

            if (orderItemRepository.existsById(id)) {

                orderItemRepository.deleteById(id);

            } else {
                throw new NotFoundException("Detalle inexistente. Id: " + orderId); // TODO exception (change this)
            }
        } else {
            throw new NotFoundException("Pedido inexistente. Id: " + orderId); // TODO exception (change this)
        }
    }
}
