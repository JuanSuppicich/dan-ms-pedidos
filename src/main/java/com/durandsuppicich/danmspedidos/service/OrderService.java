package com.durandsuppicich.danmspedidos.service;

import java.util.List;

import com.durandsuppicich.danmspedidos.domain.Construction;
import com.durandsuppicich.danmspedidos.domain.OrderState;
import com.durandsuppicich.danmspedidos.domain.Product;
import com.durandsuppicich.danmspedidos.exception.order.OrderIdNotFoundException;
import com.durandsuppicich.danmspedidos.exception.order.OrderStateNotFoundException;
import com.durandsuppicich.danmspedidos.exception.order.OrderStateUpdateException;
import com.durandsuppicich.danmspedidos.repository.IOrderJpaRepository;
import com.durandsuppicich.danmspedidos.domain.Order;

import com.durandsuppicich.danmspedidos.repository.IOrderStateJpaRepository;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {

    private final IOrderJpaRepository orderRepository;
    private final IOrderStateJpaRepository orderStateRepository;
    private final ICustomerService customerService;
    private final IProductService productService;
    private final JmsTemplate jmsTemplate;

    public OrderService(
            IOrderJpaRepository orderRepository,
            IOrderStateJpaRepository orderStateRepository,
            ICustomerService customerService,
            IProductService productService,
            JmsTemplate jmsTemplate) {
        this.orderRepository = orderRepository;
        this.orderStateRepository = orderStateRepository;
        this.customerService = customerService;
        this.productService = productService;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public Order post(Order order) {

        order.setState(new OrderState(1, "Nuevo"));

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order getById(Integer id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new OrderIdNotFoundException(id));
    }

    @Override
    public List<Order> getByConstructionId(Integer constructionId) {
        return orderRepository.findByConstruction_Id(constructionId);
    }

    @Override
    public List<Order> getByState(String state) {
        return orderRepository.findByState_description(state);
    }

    @Override
    public List<Order> getByCuit(String cuit) {
        return orderRepository.findByCuit(cuit);
    }

    @Override
    public void put(Order order, Integer id) {

        orderRepository
                .findById(id)
                .map(o -> {
                    o.setShippingDate(order.getShippingDate());
                    o.setConstruction(order.getConstruction());
                    return orderRepository.save(o);
                })
                .orElseThrow(() -> new OrderIdNotFoundException(id));
    }

    @Override
    public void patch(Order partialOrder, Integer id) {

        orderRepository
                .findById(id)
                .ifPresentOrElse(
                        o -> {
                            orderStateRepository
                                    .findById(partialOrder.getState().getId())
                                    .ifPresentOrElse(
                                            orderState -> updateOrderState(o, orderState),
                                            () -> {throw new OrderStateNotFoundException();});

                        },
                        () -> {throw new OrderIdNotFoundException(id);});
    }

    @Override
    public void delete(Integer id) {

        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new OrderIdNotFoundException(id);
        }
    }

    private void updateOrderState(Order order, OrderState orderState) {

        String currentState = order.getState().getDescription();
        String newState = orderState.getDescription();

        switch (newState) {
            case "Confirmado":
                switch (currentState) {
                    case "Nuevo":
                        orderConfirmation(order);
                        orderRepository.save(order);
                        break;
                    default:
                        throw new OrderStateUpdateException(currentState, newState);
                }
            case "Cancelado":
                switch (currentState) {
                    case "Nuevo":
                    case "Confirmado":
                    case "Pendiente":
                        order.setState(orderState);
                        orderRepository.save(order);
                        break;
                    default:
                        throw new OrderStateUpdateException(currentState, newState);
                }
            case "Aceptado":
                switch (currentState) {
                    case "Pendiente":
                        order.setState(orderState);
                        orderRepository.save(order);
                        break;
                    default:
                        throw new OrderStateUpdateException(currentState, newState);
                }
            case "En preparacion":
                switch (currentState) {
                    case "Aceptado":
                    case "Pendiente":
                        order.setState(orderState);
                        orderRepository.save(order);
                    default:
                        throw new OrderStateUpdateException(currentState, newState);
                }
            case "Entregado":
                switch (currentState) {
                    case "En preparacion":
                        order.setState(orderState);
                        orderRepository.save(order);
                        break;
                    default:
                        throw new OrderStateUpdateException(currentState, newState);
                }
            default:
                throw new OrderStateUpdateException(currentState, newState);
        }
    }

    private void orderConfirmation(Order order) {

        boolean availableStock = order
                .getItems()
                .stream()
                .allMatch(oi -> verifyStock(oi.getProduct(), oi.getQuantity()));

        double totalPrice = order
                .getItems()
                .stream()
                .mapToDouble(oi -> oi.getQuantity() * oi.getPrice())
                .sum();

        Double customerBalance = customerService.getBalance(order.getConstruction());

        double newBalance = customerBalance - totalPrice;

        if (availableStock) {

            if (newBalance >= 0 || this.isLowRisk(order.getConstruction(), newBalance)) {

                order.setState(new OrderState(5, "Aceptado"));

                jmsTemplate.convertAndSend("COLA_PEDIDOS", order.getId());

            } else {
                order.setState(new OrderState(6, "Rechazado"));
            }
        } else {
            order.setState(new OrderState(3, "Pendiente"));
        }
    }

    private Boolean verifyStock(Product product, Integer quantity) {
        return productService.getAvailableStock(product) >= quantity;
    }

    private Boolean isLowRisk(Construction construction, Double newBalance) {
        Double maximumNegativeBalance = customerService.getMaximumNegativeBalance(construction);
        return Math.abs(newBalance) < maximumNegativeBalance;
    }
}
