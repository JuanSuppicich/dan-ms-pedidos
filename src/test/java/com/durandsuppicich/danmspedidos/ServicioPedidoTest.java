package com.durandsuppicich.danmspedidos;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import com.durandsuppicich.danmspedidos.domain.DetallePedido;
import com.durandsuppicich.danmspedidos.domain.Obra;
import com.durandsuppicich.danmspedidos.domain.Pedido;
import com.durandsuppicich.danmspedidos.domain.Producto;
import com.durandsuppicich.danmspedidos.repository.PedidoInMemoryRepository;
import com.durandsuppicich.danmspedidos.service.IServicioCliente;
import com.durandsuppicich.danmspedidos.service.IServicioMaterial;
import com.durandsuppicich.danmspedidos.service.IServicioPedido;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ServicioPedidoTest {
    
    @Autowired
    IServicioPedido servicioPedido; 
    @MockBean
    IServicioCliente servicioCliente;
    @MockBean
    IServicioMaterial servicioMaterial;
    @MockBean
    PedidoInMemoryRepository pedidoRepository;


    @Test
    public void crear_TodoOk_PedidoAceptado() {
        Pedido pedido = new Pedido();
        Obra obra = new Obra();
        DetallePedido d1 = new DetallePedido(new Producto(), 1, 100.0);
        DetallePedido d2 = new DetallePedido(new Producto(), 2, 50.0);
        DetallePedido d3 = new DetallePedido(new Producto(), 3, 100.0);
        pedido.setDetalles(new ArrayList<DetallePedido>());
        pedido.getDetalles().add(d1);
        pedido.getDetalles().add(d2);
        pedido.getDetalles().add(d3);
        pedido.setObra(obra);
        when(servicioMaterial.stockDisponible(any(Producto.class))).thenReturn(5);
        when(servicioCliente.saldoCliente(any(Obra.class))).thenReturn(1000.0);
        when(servicioCliente.maximoSaldoNegativo(any(Obra.class))).thenReturn(1000.0);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        
        Pedido resultado = servicioPedido.crear(pedido);
        assertTrue(resultado.getEstado().getId().equals(1));
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    public void crear_SinSaldoPeroDeBajoRiesgo_PedidoAceptado() {
        Pedido pedido = new Pedido();
        Obra obra = new Obra();
        DetallePedido d1 = new DetallePedido(new Producto(), 1, 100.0);
        DetallePedido d2 = new DetallePedido(new Producto(), 2, 50.0);
        DetallePedido d3 = new DetallePedido(new Producto(), 3, 100.0);
        pedido.setDetalles(new ArrayList<DetallePedido>());
        pedido.getDetalles().add(d1);
        pedido.getDetalles().add(d2);
        pedido.getDetalles().add(d3);
        pedido.setObra(obra);
        when(servicioMaterial.stockDisponible(any(Producto.class))).thenReturn(5);
        when(servicioCliente.saldoCliente(any(Obra.class))).thenReturn(0.0);
        when(servicioCliente.maximoSaldoNegativo(any(Obra.class))).thenReturn(1000.0);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        
        Pedido resultado = servicioPedido.crear(pedido);
        assertTrue(resultado.getEstado().getId().equals(1));
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    public void crear_ProductoSinStock_PedidoPendiente() {
        Pedido pedido = new Pedido();
        Obra obra = new Obra();
        DetallePedido d1 = new DetallePedido(new Producto(), 1, 100.0);
        DetallePedido d2 = new DetallePedido(new Producto(), 2, 50.0);
        DetallePedido d3 = new DetallePedido(new Producto(), 6, 100.0);
        pedido.setDetalles(new ArrayList<DetallePedido>());
        pedido.getDetalles().add(d1);
        pedido.getDetalles().add(d2);
        pedido.getDetalles().add(d3);
        pedido.setObra(obra);
        when(servicioMaterial.stockDisponible(any(Producto.class))).thenReturn(5);
        when(servicioCliente.saldoCliente(any(Obra.class))).thenReturn(0.0);
        when(servicioCliente.maximoSaldoNegativo(any(Obra.class))).thenReturn(1000.0);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        
        Pedido resultado = servicioPedido.crear(pedido);
        assertTrue(resultado.getEstado().getId().equals(2));
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    public void crear_SinSaldoYNoEsBajoRiesgo_RuntimeException() {
        Pedido pedido = new Pedido();
        Obra obra = new Obra();
        DetallePedido d1 = new DetallePedido(new Producto(), 1, 100.0);
        DetallePedido d2 = new DetallePedido(new Producto(), 2, 50.0);
        DetallePedido d3 = new DetallePedido(new Producto(), 3, 100.0);
        pedido.setDetalles(new ArrayList<DetallePedido>());
        pedido.getDetalles().add(d1);
        pedido.getDetalles().add(d2);
        pedido.getDetalles().add(d3);
        pedido.setObra(obra);
        when(servicioMaterial.stockDisponible(any(Producto.class))).thenReturn(5);
        when(servicioCliente.saldoCliente(any(Obra.class))).thenReturn(0.0);
        when(servicioCliente.maximoSaldoNegativo(any(Obra.class))).thenReturn(400.0);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        
        assertThrows(RuntimeException.class, () -> {servicioPedido.crear(pedido);});
    }

    

}
