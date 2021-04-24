package com.durandsuppicich.danmspedidos;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import com.durandsuppicich.danmspedidos.domain.DetallePedido;
import com.durandsuppicich.danmspedidos.domain.Obra;
import com.durandsuppicich.danmspedidos.domain.Pedido;
import com.durandsuppicich.danmspedidos.domain.Producto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = PedidosTest.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class PedidoRestTest {

    @Autowired
    private TestRestTemplate testRestTemplate; 
    @LocalServerPort
    String puerto;


    @Test
    public void crear_PedidoOk_Ok() {
        String url = "http://localhost:" + puerto + "/api/pedido";
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
        
        HttpEntity<Pedido> request = new HttpEntity<Pedido>(pedido);
        ResponseEntity<Pedido> response = testRestTemplate
            .exchange(url, HttpMethod.POST, request, Pedido.class);
        assertTrue(response.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    public void crear_PedidoSinObra_BadRequest() {
        String url = "http://localhost:" + puerto + "/api/pedido";
        Pedido pedido = new Pedido();
        DetallePedido d1 = new DetallePedido(new Producto(), 1, 100.0);
        DetallePedido d2 = new DetallePedido(new Producto(), 2, 50.0);
        DetallePedido d3 = new DetallePedido(new Producto(), 3, 100.0);
        pedido.setDetalles(new ArrayList<DetallePedido>());
        pedido.getDetalles().add(d1);
        pedido.getDetalles().add(d2);
        pedido.getDetalles().add(d3);
        
        HttpEntity<Pedido> request = new HttpEntity<Pedido>(pedido);
        ResponseEntity<Pedido> response = testRestTemplate
            .exchange(url, HttpMethod.POST, request, Pedido.class);
        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }
    
    @Test
    public void crear_PedidoSinDetalles_BadRequest() {
        String url = "http://localhost:" + puerto + "/api/pedido";
        Pedido pedido = new Pedido();
        Obra obra = new Obra();
        pedido.setDetalles(new ArrayList<DetallePedido>());
        pedido.setObra(obra);
        
        HttpEntity<Pedido> request = new HttpEntity<Pedido>(pedido);
        ResponseEntity<Pedido> response = testRestTemplate
            .exchange(url, HttpMethod.POST, request, Pedido.class);
        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void crear_PedidoDetallesSinInfo_BadRequest() {
        String url = "http://localhost:" + puerto + "/api/pedido";
        Pedido pedido = new Pedido();
        Obra obra = new Obra();
        DetallePedido d1 = new DetallePedido(new Producto(), 1, 100.0);
        DetallePedido d2 = new DetallePedido(new Producto(), 2, 50.0);
        DetallePedido d3 = new DetallePedido();
        pedido.setDetalles(new ArrayList<DetallePedido>());
        pedido.getDetalles().add(d1);
        pedido.getDetalles().add(d2);
        pedido.getDetalles().add(d3);
        pedido.setObra(obra);
        
        HttpEntity<Pedido> request = new HttpEntity<Pedido>(pedido);
        ResponseEntity<Pedido> response = testRestTemplate
            .exchange(url, HttpMethod.POST, request, Pedido.class);
        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void crear_PedidoDetallesConCantidadNegativa_BadRequest() {
        String url = "http://localhost:" + puerto + "/api/pedido";
        Pedido pedido = new Pedido();
        Obra obra = new Obra();
        DetallePedido d1 = new DetallePedido(new Producto(), 1, 100.0);
        DetallePedido d2 = new DetallePedido(new Producto(), -2, 50.0);
        DetallePedido d3 = new DetallePedido(new Producto(), -3, 100.0);
        pedido.setDetalles(new ArrayList<DetallePedido>());
        pedido.getDetalles().add(d1);
        pedido.getDetalles().add(d2);
        pedido.getDetalles().add(d3);
        pedido.setObra(obra);
        
        HttpEntity<Pedido> request = new HttpEntity<Pedido>(pedido);
        ResponseEntity<Pedido> response = testRestTemplate
            .exchange(url, HttpMethod.POST, request, Pedido.class);
        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    
}
