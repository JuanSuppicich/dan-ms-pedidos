package com.durandsuppicich.danmspedidos.service;

import java.util.List;
import java.util.Optional;

import com.durandsuppicich.danmspedidos.dao.DetallePedidoJpaRepository;
import com.durandsuppicich.danmspedidos.dao.PedidoJpaRepository;
import com.durandsuppicich.danmspedidos.domain.DetallePedido;
import com.durandsuppicich.danmspedidos.domain.EstadoPedido;
import com.durandsuppicich.danmspedidos.domain.Obra;
import com.durandsuppicich.danmspedidos.domain.Pedido;
import com.durandsuppicich.danmspedidos.domain.Producto;
import com.durandsuppicich.danmspedidos.exception.BadRequestException;
import com.durandsuppicich.danmspedidos.exception.NotFoundException;

import org.springframework.stereotype.Service;

@Service
public class ServicioPedido implements IServicioPedido {

    private final IServicioCliente servicioCliente;
    private final IServicioMaterial servicioMaterial;
    private final PedidoJpaRepository pedidoRepository;
    private final DetallePedidoJpaRepository detallePedidoRepository;

    public ServicioPedido(IServicioCliente servicioCliente, IServicioMaterial servicioMaterial,
            PedidoJpaRepository pedidoRepository, DetallePedidoJpaRepository detallePedidoRepository) {
        this.servicioCliente = servicioCliente;
        this.servicioMaterial = servicioMaterial;
        this.pedidoRepository = pedidoRepository;
        this.detallePedidoRepository = detallePedidoRepository;
    }

    @Override
    public Pedido crear(Pedido pedido) {

        Boolean existeStock = pedido.getDetalles()
                                    .stream()
                                    .allMatch(dp -> 
                                    verificarStock(dp.getProducto(), dp.getCantidad()));

        Double totalOrden = pedido.getDetalles()
                                .stream()
                                .mapToDouble(dp -> dp.getCantidad() * dp.getPrecio()).sum();
    
        Double saldoCliente = servicioCliente.saldoCliente(pedido.getObra());
        Double nuevoSaldo = saldoCliente - totalOrden;

        if (existeStock) {
            if (nuevoSaldo >= 0 || this.esDeBajoRiesgo(pedido.getObra(), nuevoSaldo)) {

                pedido.setEstado(new EstadoPedido(1, "ACEPTADO"));
            } else {
                throw new BadRequestException("No tiene aprobacion crediticia");
            }
        } else {
            pedido.setEstado(new EstadoPedido(2, "PENDIENTE"));
        }
        return this.pedidoRepository.save(pedido);
    }

    @Override
    public Pedido agregarDetalle(Integer idPedido, DetallePedido detallePedido) {
        
        if (pedidoRepository.existsById(idPedido)) {

            Pedido pedido = pedidoRepository.findById(idPedido).get();
            pedido.addDetalle(detallePedido);
            return pedidoRepository.save(pedido);

        } else {
            throw new NotFoundException("Pedido inexistente. Id: " + idPedido);
        }
    }

    @Override
    public List<Pedido> todos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Optional<Pedido> pedidoPorId(Integer id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public Optional<Pedido> pedidoPorIdObra(Integer idObra) {
        return pedidoRepository.findByIdObra(idObra);
    }

    @Override
    public Optional<DetallePedido> buscarDetalle(Integer idPedido, Integer idDetalle) {

        Optional<Pedido> pedido = pedidoRepository.findById(idPedido);

        if (pedido.isPresent()) {

            return pedido.get()
                .getDetalles()
                .stream()
                .filter(dp -> dp.getId().equals(idDetalle))
                .findFirst();

        } else {
            throw new NotFoundException("Pedido inexistente. Id: " + idPedido);
        }
    }

    @Override
    public void actualizar(Integer id, Pedido pedido) {

        if (pedidoRepository.existsById(id)) {
            pedidoRepository.save(pedido);
        } else {
            throw new NotFoundException("Pedido inexistente. Id: " + id);
        }
    }

    @Override
    public void eliminar(Integer id) {

        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
        } else {
            throw new NotFoundException("Pedido inexistente. Id: " + id);
        }
    }

    @Override
    public void eliminarDetalle(Integer idPedido, Integer idDetalle) {
        
        Optional<Pedido> pedido = pedidoRepository.findById(idPedido); 

        if (pedido.isPresent()) {

            if (detallePedidoRepository.existsById(idDetalle)) {

                detallePedidoRepository.deleteById(idDetalle); 

            } else {
                throw new NotFoundException("Detalle inexistente. Id: " + idPedido);
            }
        } else {
            throw new NotFoundException("Pedido inexistente. Id: " + idPedido);
        }
        
    }

    private Boolean verificarStock(Producto producto, Integer cantidad) {
        return servicioMaterial.stockDisponible(producto) >= cantidad;
    }

    private Boolean esDeBajoRiesgo(Obra obra, Double nuevoSaldo) {
        Double maximoSaldoNegativo = servicioCliente.maximoSaldoNegativo(obra);
        return Math.abs(nuevoSaldo) < maximoSaldoNegativo;
    }
}
