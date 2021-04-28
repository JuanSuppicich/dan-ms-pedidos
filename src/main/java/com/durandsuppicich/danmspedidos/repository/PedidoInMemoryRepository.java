package com.durandsuppicich.danmspedidos.repository;

import com.durandsuppicich.danmspedidos.domain.Pedido;

import org.springframework.stereotype.Repository;

import frsf.isi.dan.InMemoryRepository;

@Repository
public class PedidoInMemoryRepository extends InMemoryRepository<Pedido> {

    @Override
    public Integer getId(Pedido entity) {
        return entity.getId();
    }

    @Override
    public void setId(Pedido entity, Integer id) {
        entity.setId(id);
    }
    
}
