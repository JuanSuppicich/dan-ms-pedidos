package com.durandsuppicich.danmspedidos.dao;

import com.durandsuppicich.danmspedidos.domain.EstadoPedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoPedidoJpaReposity extends JpaRepository<EstadoPedido, Integer> {
}
