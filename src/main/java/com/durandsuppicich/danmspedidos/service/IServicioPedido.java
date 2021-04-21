package com.durandsuppicich.danmspedidos.service;

import java.util.List;
import java.util.Optional;

import com.durandsuppicich.danmspedidos.domain.DetallePedido;
import com.durandsuppicich.danmspedidos.domain.Pedido;

public interface IServicioPedido {

    Pedido crear(Pedido pedido);
    Pedido agregarDetalle(Integer id, DetallePedido detallePedido);
    List<Pedido> todos();
    Optional<Pedido> pedidoPorId(Integer id);
    Optional<Pedido> pedidoPorIdObra(Integer idObra);
    Optional<DetallePedido> buscarDetalle(Integer idPedido, Integer idDetalle);
    void actualizar(Integer id, Pedido pedido);
    void eliminar(Integer id);
    void eliminarDetalle(Integer idPedido, Integer idDetalle);

}
