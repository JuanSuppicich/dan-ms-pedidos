package com.durandsuppicich.danmspedidos.dao;

import java.util.Optional;

import com.durandsuppicich.danmspedidos.domain.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoJpaRepository extends JpaRepository<Pedido, Integer> {

    Optional<Pedido> findByObra_Id(Integer id);

}
