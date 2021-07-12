package com.durandsuppicich.danmspedidos.repository;

import java.util.List;
import java.util.Optional;

import com.durandsuppicich.danmspedidos.domain.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderJpaRepository extends JpaRepository<Order, Integer> {

    Optional<Order> findByConstruction_Id(Integer constructionId);

    List<Order> findByState_state(String state);

    // TODO check
    @Query(value = "SELECT * " +
            "FROM ms_orders.order o " +
            "JOIN ms_users.construction c ON o.construction_id = c.construction_id " +
            "JOIN ms_users.customer cmr ON c.customer_id = cmr.customer_id " +
            "WHERE cmr.cuit = :cuit",
            nativeQuery = true
    )
    List<Order> findByCuit(String cuit);
}
