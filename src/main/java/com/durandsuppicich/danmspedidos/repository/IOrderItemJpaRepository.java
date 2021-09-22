package com.durandsuppicich.danmspedidos.repository;

import com.durandsuppicich.danmspedidos.domain.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderItemJpaRepository extends JpaRepository<OrderItem, Integer> {

    @Query(value = "SELECT * " +
            "FROM ms_orders.order_item " +
            "WHERE order_id = :orderId",
            nativeQuery = true
    )
    List<OrderItem> findByOrderId(Integer orderId);

    @Query(value = "SELECT * " +
            "FROM ms_orders.order o " +
            "JOIN ms_orders.order_item oi ON o.order_id = oi.order_id " +
            "WHERE o.order_id = :orderId AND oi.order_item_id = :id",
            nativeQuery = true
    )
    Optional<OrderItem> findByIdAndOrderId(Integer id, Integer orderId);
}
