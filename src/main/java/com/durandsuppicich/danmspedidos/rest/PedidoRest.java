package com.durandsuppicich.danmspedidos.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import com.durandsuppicich.danmspedidos.domain.DetallePedido;
import com.durandsuppicich.danmspedidos.domain.Pedido;
import com.durandsuppicich.danmspedidos.service.IServicioPedido;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/pedido")
@Api(value = "PedidoRest", description = "Permite gestionar los pedidos")
public class PedidoRest {

    private List<Pedido> pedidos = new ArrayList<Pedido>();
    private final IServicioPedido servicioPedido;


    public PedidoRest(IServicioPedido servicioPedido) {
        this.servicioPedido = servicioPedido;
    }

    @PostMapping
    @ApiOperation(value = "Crea un nuevo pedido")
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido) {
        if (pedido.getObra() != null && pedido.getDetalles() != null 
            && pedido.getDetalles().size() > 0) {
                
                Boolean detallesOk = pedido.getDetalles()
                    .stream()
                    .allMatch(dp -> dp.getProducto() != null 
                        && dp.getCantidad() != null 
                        && dp.getCantidad() > 0);

                if (detallesOk) {          
                    Pedido body = servicioPedido.crear(pedido);
                    return ResponseEntity.ok(body);
                }
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "/{idPedido}/detalle")
    @ApiOperation(value = "Agrega un detalle al pedido")
    public ResponseEntity<Pedido> agregarDetalle(@PathVariable Integer idPedido, @RequestBody DetallePedido detalle) {

        Optional<Pedido> pedidoOpt = pedidos
            .stream()
            .filter(p -> p.getId().equals(idPedido))
            .findFirst();

        if (pedidoOpt.isPresent()) {
            pedidoOpt.get().getDetalles().add(detalle);
            return ResponseEntity.ok(pedidoOpt.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping
    @ApiOperation(value = "Lista todos los pedidos")
    public ResponseEntity<List<Pedido>> todos() {
        return ResponseEntity.ok(pedidos);
    }
    
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca un pedido por id")
    public ResponseEntity<Pedido> pedidoPorId(@PathVariable Integer id) {
        Optional<Pedido> pedidoOpt = pedidos
            .stream()
            .filter(p -> p.getId().equals(id))
            .findFirst();
        return ResponseEntity.of(pedidoOpt);
    }

    @GetMapping(params = "idObra")
    @ApiOperation(value = "Busca un pedido por id de obra")
    public ResponseEntity<Pedido> pedidoPorIdObra(@RequestParam(name = "idObra") Integer idObra) {
        Optional<Pedido> pedidoOpt = pedidos
            .stream()
            .filter(p -> p.getObra().getId().equals(idObra))
            .findFirst();
        return ResponseEntity.of(pedidoOpt);
    }

    /*
    Completar buscar pedido por id de cliente o cuit
    */

    @GetMapping(path = "/{idPedido}/detalle/{id}")
    @ApiOperation(value = "Busca un detalle del pedido en base al id")
    public ResponseEntity<DetallePedido> buscarDetalle(@PathVariable Integer idPedido, @PathVariable Integer id) {
        Optional<DetallePedido> detalleOpt = pedidos
            .stream()
            .filter(p -> p.getId().equals(idPedido))
            .findFirst()
            .get()
            .getDetalles()
            .stream()
            .filter(d -> d.getId().equals(id))
            .findFirst();
        return ResponseEntity.of(detalleOpt);
    }
    
    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Actualiza un pedido en base al id")
    public ResponseEntity<Pedido> actualizar(@RequestBody Pedido pedido, @PathVariable Integer id) {

        OptionalInt indexOpt = IntStream
            .range(0, pedidos.size())
            .filter(i -> pedidos.get(i).getId().equals(id))
            .findFirst();

        if (indexOpt.isPresent()) {
            pedidos.set(indexOpt.getAsInt(), pedido);
            return ResponseEntity.ok(pedido);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Elimina un pedido en base al id")
    public ResponseEntity<Pedido> eliminar(@PathVariable Integer id) {

        OptionalInt indexOpt = IntStream
            .range(0, pedidos.size())
            .filter(i -> pedidos.get(i).getId().equals(id))
            .findFirst();

        if (indexOpt.isPresent()) {
            pedidos.remove(indexOpt.getAsInt());
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{idPedido}/detalle/{id}")
    @ApiOperation(value = "Elimina un detalle del pedido en base al id")
    public ResponseEntity<Pedido> eliminarDetalle(@PathVariable Integer idPedido, @PathVariable Integer id) {

        Optional<Pedido> pedidoOpt = pedidos
            .stream()
            .filter(p -> p.getId().equals(idPedido))
            .findFirst();
            
        if (pedidoOpt.isPresent()) {
            
            List<DetallePedido> detalles = pedidoOpt.get().getDetalles();

            OptionalInt indexOpt = IntStream
                .range(0, detalles.size())
                .filter(i -> detalles.get(i).getId().equals(id))
                .findFirst();

            if (indexOpt.isPresent()) {
                pedidoOpt.get().getDetalles().remove(indexOpt.getAsInt());
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
