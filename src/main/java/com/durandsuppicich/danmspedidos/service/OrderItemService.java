package com.durandsuppicich.danmspedidos.service;

import com.durandsuppicich.danmspedidos.domain.Order;
import com.durandsuppicich.danmspedidos.domain.OrderItem;
import com.durandsuppicich.danmspedidos.exception.item.OrderItemIdNotFoundException;
import com.durandsuppicich.danmspedidos.exception.item.OrderItemNotFoundException;
import com.durandsuppicich.danmspedidos.exception.order.OrderIdNotFoundException;
import com.durandsuppicich.danmspedidos.repository.IOrderItemJpaRepository;
import com.durandsuppicich.danmspedidos.repository.IOrderJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class OrderItemService implements IOrderItemService {

    private final IOrderJpaRepository orderRepository;
    private final IOrderItemJpaRepository orderItemRepository;

    public OrderItemService(
            IOrderJpaRepository orderRepository,
            IOrderItemJpaRepository orderItemRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Order post(OrderItem orderItem, Integer orderId) {

        return orderRepository
                .findById(orderId)
                .map(o -> {
                    o.addOrderItem(orderItem);
                    return orderRepository.save(o);
                })
                .orElseThrow(() -> new OrderIdNotFoundException(orderId));
    }

    @Override
    public OrderItem getById(Integer orderId, Integer id) {

        return orderItemRepository
                .findByIdAndOrderId(id, orderId)
                .orElseThrow(OrderItemNotFoundException::new);
    }

    @Override
    public List<OrderItem> getByOrderId(Integer orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    public void put(OrderItem orderItem, Integer orderId, Integer id) {

        orderRepository
                .findById(orderId)
                .ifPresentOrElse(o -> {

                    List<OrderItem> items = o.getItems();

                    IntStream.range(0, items.size())
                            .filter(i -> items.get(i).getId().equals(id))
                            .findFirst()
                            .ifPresentOrElse(i -> {

                                items.get(i).setQuantity(orderItem.getQuantity());
                                orderRepository.save(o);

                            }, () -> {throw new OrderItemIdNotFoundException(id);});
                }, () -> {throw new OrderIdNotFoundException(id);});
    }

    @Override
    public void delete(Integer orderId, Integer id) {

        orderItemRepository
                .findByIdAndOrderId(id, orderId)
                .ifPresentOrElse(
                        (oi) -> orderItemRepository.deleteById(id),
                        () -> {throw new OrderItemNotFoundException();});
    }
}
