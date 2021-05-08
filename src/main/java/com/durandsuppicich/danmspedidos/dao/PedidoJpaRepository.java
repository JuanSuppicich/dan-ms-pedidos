package com.durandsuppicich.danmspedidos.dao;

import java.util.Optional;

import com.durandsuppicich.danmspedidos.domain.Pedido;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoJpaRepository extends JpaRepository<Pedido, Integer> {

    @Query("SELECT p FROM Pedido p WHERE p.obra.id = :idObra")
    Optional<Pedido> findByIdObra(@Param("idObra") Integer id);

}
