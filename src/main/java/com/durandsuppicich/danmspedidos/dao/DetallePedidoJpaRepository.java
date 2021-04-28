package com.durandsuppicich.danmspedidos.dao;

import com.durandsuppicich.danmspedidos.domain.DetallePedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePedidoJpaRepository extends JpaRepository<DetallePedido, Integer> {
}
