package com.durandsuppicich.danmspedidos.dao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.durandsuppicich.danmspedidos.DanMsPedidosApplicationTests;
import com.durandsuppicich.danmspedidos.domain.DetallePedido;
import com.durandsuppicich.danmspedidos.domain.EstadoPedido;
import com.durandsuppicich.danmspedidos.domain.Obra;
import com.durandsuppicich.danmspedidos.domain.Pedido;
import com.durandsuppicich.danmspedidos.domain.Producto;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(classes = DanMsPedidosApplicationTests.class)
public class PedidoJpaRepositoryTest {
    
    @Autowired
    public PedidoJpaRepository pedidoRepository;

    @Test
    public void elRepositorioExiste() {
        assertNotNull(pedidoRepository);
    }

    @Test
    @Sql("/datos_test.sql")
    public void findAll_PedidosAlmacenados_PedidosRecuperados() {

        List<Pedido> pedidos = pedidoRepository.findAll();

        assertFalse(pedidos.isEmpty());
        assertThat(pedidos.size(), is(equalTo(3)));
    }

    @Test
    public void findById_PedidoAlmacenado_PedidoRecuperado() {

        Optional<Pedido> pedido = pedidoRepository.findById(1);
        
        assertTrue(pedido.isPresent());
        assertThat(pedido.get().getId(), is(equalTo(1)));
        assertNotNull(pedido.get().getEstado());
        assertNotNull(pedido.get().getDetalles());
        //assertThat(pedido.get().getDetalles().size(), is(equalTo(2)));
        assertNotNull(pedido.get().getObra());
        //assertThat(pedido.get().getDetalles(), everyItem(hasProperty("producto")));
    }

    @Test
    public void findByObraId_PedidoAlmacenado_PedidoRecuperado() {

        Optional<Pedido> pedido = pedidoRepository.findByObra_Id(2); 

        assertTrue(pedido.isPresent());
        assertThat(pedido.get().getId(), is(equalTo(2))); 
    } 

    @Test
    public void save_PedidoOk_PedidoAlmacenado() {

        EstadoPedido estadoPedido = new EstadoPedido(1, "Aceptado");
        Obra obra = new Obra();
        obra.setId(1);
        Producto producto1 = new Producto();
        Producto producto2 = new Producto();
        producto1.setId(1);
        producto2.setId(2);
        DetallePedido detallePedido1 = new DetallePedido(producto1, 1, 10.0); 
        DetallePedido detallePedido2 = new DetallePedido(producto2, 2, 20.0);
        Pedido pedido = new Pedido();
        pedido.setFecha(Instant.now());
        pedido.setEstado(estadoPedido);
        pedido.setObra(obra);
        pedido.setDetalles(new ArrayList<DetallePedido>());
        pedido.addDetalle(detallePedido1);
        pedido.addDetalle(detallePedido2);

        pedidoRepository.save(pedido);

        assertThat(pedidoRepository.count(), is(equalTo(4L)));
    }
}
