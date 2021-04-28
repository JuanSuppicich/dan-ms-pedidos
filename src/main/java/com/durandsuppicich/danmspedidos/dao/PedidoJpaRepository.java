package com.durandsuppicich.danmspedidos.dao;

import com.durandsuppicich.danmspedidos.domain.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoJpaRepository extends JpaRepository<Pedido, Integer> {
}
