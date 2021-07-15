package com.durandsuppicich.danmspedidos.service;

import java.util.List;
import java.util.Optional;

import com.durandsuppicich.danmspedidos.domain.Construction;
import com.durandsuppicich.danmspedidos.domain.OrderState;
import com.durandsuppicich.danmspedidos.domain.Product;
import com.durandsuppicich.danmspedidos.repository.IOrderJpaRepository;
import com.durandsuppicich.danmspedidos.domain.Order;
import com.durandsuppicich.danmspedidos.exception.BadRequestException;
import com.durandsuppicich.danmspedidos.exception.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {

    private final IOrderJpaRepository orderRepository;
    private final ICustomerService customerService;
    private final IProductService productService;

    @Autowired 
    private JmsTemplate jmsTemplate;

    public OrderService(
            IOrderJpaRepository orderRepository,
            ICustomerService customerService,
            IProductService productService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.productService = productService;
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
    public Optional<Order> getById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public Optional<Order> getByConstructionId(Integer constructionId) {
        return orderRepository.findByConstruction_Id(constructionId);
    }

    @Override
    public List<Order> getByState(String state) {
        return orderRepository.findByState_state(state);
    }

    @Override
    public List<Order> getByCuit(String cuit) {
        return orderRepository.findByCuit(cuit);
    }

    @Override
    public void put(Order order, Integer id) {

        if (orderRepository.existsById(id)) {
            orderRepository.save(order);
        } else {
            throw new NotFoundException("Pedido inexistente. Id: " + id); // TODO exception (change this)
        }
    }

    @Override
    public void patch(Order partialOrder, Integer id) { // TODO check

        Optional<Order> optOrder = orderRepository.findById(id);

        OrderState orderState = partialOrder.getState();

        if (optOrder.isPresent()) {

            Order order = optOrder.get();

            if (orderState.getDescription().equals("Confirmado")) {

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

                        orderRepository.save(order);

                        throw new BadRequestException("No tiene aprobacion crediticia"); // TODO exception (change this)
                    }
                } else {
                    order.setState(new OrderState(3, "Pendiente"));
                }
            } else {
                order.setState(orderState);
            }

            orderRepository.save(order);

        } else {
            throw new NotFoundException("Pedido inexistente. Id: " + id); // TODO exception (change this)
        }
    }

    @Override
    public void delete(Integer id) {

        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new NotFoundException("Pedido inexistente. Id: " + id); // TODO exception (change this)
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
