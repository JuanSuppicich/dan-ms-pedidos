package com.durandsuppicich.danmspedidos.service;

import java.util.List;
import java.util.Optional;

import com.durandsuppicich.danmspedidos.domain.DetallePedido;
import com.durandsuppicich.danmspedidos.domain.EstadoPedido;
import com.durandsuppicich.danmspedidos.domain.Obra;
import com.durandsuppicich.danmspedidos.domain.Pedido;
import com.durandsuppicich.danmspedidos.domain.Producto;
import com.durandsuppicich.danmspedidos.repository.PedidoRepository;

import org.springframework.stereotype.Service;

@Service
public class ServicioPedido implements IServicioPedido {

    private final IServicioCliente servicioCliente; 
    private final IServicioMaterial servicioMaterial;
    private final PedidoRepository pedidoRepository;


    public ServicioPedido(IServicioCliente servicioCliente, 
                        IServicioMaterial servicioMaterial,
                        PedidoRepository pedidoRepository) {
        this.servicioCliente = servicioCliente;
        this.servicioMaterial = servicioMaterial;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public Pedido crear(Pedido pedido) {
        Boolean existeStock = pedido.getDetalles()
            .stream()
            .allMatch(dp -> verificarStock(dp.getProducto(), dp.getCantidad()));
        Double totalOrden = pedido.getDetalles()
            .stream()
            .mapToDouble(dp -> dp.getCantidad() * dp.getPrecio())
            .sum();
        Double saldoCliente = servicioCliente.saldoCliente(pedido.getObra()); 
        Double nuevoSaldo = saldoCliente - totalOrden;

        if (existeStock) {
            if (!(nuevoSaldo < 0) || (nuevoSaldo < 0 &&  
                this.esDeBajoRiesgo(pedido.getObra(), nuevoSaldo))) {
                
                pedido.setEstado(new EstadoPedido(1, "ACEPTADO"));
            } 
            else {
                throw new RuntimeException("No tiene aprobacion crediticia");
            }
        }
        else {
            pedido.setEstado(new EstadoPedido(2, "PENDIENTE"));
        }
        return this.pedidoRepository.save(pedido);
    }

    @Override
    public Pedido agregarDetalle(Integer id, DetallePedido detallePedido) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Pedido> todos() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Pedido> pedidoPorId(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Pedido> pedidoPorIdObra(Integer idObra) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<DetallePedido> buscarDetalle(Integer idPedido, Integer idDetalle) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void actualizar(Integer id, Pedido pedido) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void eliminar(Integer id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void eliminarDetalle(Integer idPedido, Integer idDetalle) {
        // TODO Auto-generated method stub
        
    }
    
    private Boolean verificarStock(Producto producto, Integer cantidad) {
        return servicioMaterial.stockDisponible(producto) >= cantidad;
    }

    private Boolean esDeBajoRiesgo(Obra obra, Double nuevoSaldo) {
        Double maximoSaldoNegativo = servicioCliente.maximoSaldoNegativo(obra);
        return Math.abs(nuevoSaldo) < maximoSaldoNegativo;
    }
}
