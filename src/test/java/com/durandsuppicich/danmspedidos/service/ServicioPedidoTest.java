package com.durandsuppicich.danmspedidos.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;

import com.durandsuppicich.danmspedidos.DanMsPedidosApplicationTests;
import com.durandsuppicich.danmspedidos.dao.PedidoJpaRepository;
import com.durandsuppicich.danmspedidos.domain.DetallePedido;
import com.durandsuppicich.danmspedidos.domain.Obra;
import com.durandsuppicich.danmspedidos.domain.Pedido;
import com.durandsuppicich.danmspedidos.domain.Producto;
import com.durandsuppicich.danmspedidos.exception.BadRequestException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = DanMsPedidosApplicationTests.class)
public class ServicioPedidoTest {

    @Autowired
    IServicioPedido servicioPedido;

    @MockBean
    IServicioCliente servicioCliente;

    @MockBean
    IServicioMaterial servicioMaterial;

    @MockBean
    PedidoJpaRepository pedidoRepository;

    Pedido pedido;

    @BeforeEach
    public void setUp() {

        Obra obra = new Obra();
        obra.setId(1);

        Producto p1 = new Producto();
        Producto p2 = new Producto();
        Producto p3 = new Producto();
        p1.setId(1);
        p2.setId(2);
        p3.setId(3);

        DetallePedido d1 = new DetallePedido(p1, 1, 100.0);
        DetallePedido d2 = new DetallePedido(p2, 2, 50.0);
        DetallePedido d3 = new DetallePedido(p3, 3, 100.0);

        pedido = new Pedido();
        pedido.setFecha(Instant.now());
        pedido.setObra(obra);
        pedido.setDetalles(new ArrayList<DetallePedido>());
        pedido.addDetalle(d1);
        pedido.addDetalle(d2);
        pedido.addDetalle(d3);
    }

    @Test
    public void crear_TodoOk_PedidoAceptado() {

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

        DetallePedido d3 = new DetallePedido(new Producto(), 6, 100.0);
        pedido.getDetalles().add(2, d3);

        when(servicioMaterial.stockDisponible(any(Producto.class))).thenReturn(5);
        when(servicioCliente.saldoCliente(any(Obra.class))).thenReturn(0.0);
        when(servicioCliente.maximoSaldoNegativo(any(Obra.class))).thenReturn(1000.0);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = servicioPedido.crear(pedido);

        assertTrue(resultado.getEstado().getId().equals(2));
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    public void crear_SinSaldoYNoEsBajoRiesgo_BadRequestException() {

        when(servicioMaterial.stockDisponible(any(Producto.class))).thenReturn(5);
        when(servicioCliente.saldoCliente(any(Obra.class))).thenReturn(0.0);
        when(servicioCliente.maximoSaldoNegativo(any(Obra.class))).thenReturn(400.0);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        assertThrows(BadRequestException.class, () -> {
            servicioPedido.crear(pedido);
        });
    }
}
