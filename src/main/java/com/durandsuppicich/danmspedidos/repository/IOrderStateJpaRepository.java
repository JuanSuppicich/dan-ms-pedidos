package com.durandsuppicich.danmspedidos.repository;

import com.durandsuppicich.danmspedidos.domain.OrderState;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderStateJpaRepository extends JpaRepository<OrderState, Integer> { }
