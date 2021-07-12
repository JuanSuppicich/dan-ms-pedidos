package com.durandsuppicich.danmspedidos.repository;

import com.durandsuppicich.danmspedidos.domain.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderItemJpaRepository extends JpaRepository<OrderItem, Integer> { }
