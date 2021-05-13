package com.durandsuppicich.danmspedidos.dao;

import java.util.List;
import java.util.Optional;

import com.durandsuppicich.danmspedidos.domain.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoJpaRepository extends JpaRepository<Pedido, Integer> {

    Optional<Pedido> findByObra_Id(Integer id);

    List<Pedido> findByEstado_estado(String estado);

    @Query(value = "SELECT * FROM ms_pedidos.pedido p JOIN ms_usuarios.obra o ON p.id_obra = o.id_obra JOIN" +
                    " ms_usuarios.cliente c ON o.id_cliente = c.id_cliente WHERE c.cuit = :cuit", nativeQuery = true)
    List<Pedido> findByCuit(String cuit);

}
