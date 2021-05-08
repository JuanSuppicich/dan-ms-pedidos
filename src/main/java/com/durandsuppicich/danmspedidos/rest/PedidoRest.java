package com.durandsuppicich.danmspedidos.rest;

import java.util.List;
import java.util.Optional;

import com.durandsuppicich.danmspedidos.domain.DetallePedido;
import com.durandsuppicich.danmspedidos.domain.Pedido;
import com.durandsuppicich.danmspedidos.exception.BadRequestException;
import com.durandsuppicich.danmspedidos.exception.NotFoundException;
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

    private final IServicioPedido servicioPedido;

    public PedidoRest(IServicioPedido servicioPedido) {
        this.servicioPedido = servicioPedido;
    }

    @PostMapping
    @ApiOperation(value = "Crea un nuevo pedido")
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido) {

        if (pedido.getDetalles() != null && pedido.getDetalles().size() > 0) {

            if (pedido.getObra() != null) {

                Boolean detallesOk = pedido.getDetalles()
                    .stream()
                    .allMatch(dp -> dp.getProducto() != null && dp.getCantidad() != null &&
                                    dp.getCantidad() > 0);

                if (detallesOk) {

                    Pedido body = servicioPedido.crear(pedido);
                    return ResponseEntity.ok(body);

                } else {
                    throw new BadRequestException("Detalles: " + pedido.getDetalles());
                }
            } else {
                throw new BadRequestException("Obra: " + pedido.getObra());
            }
        } else {
            throw new BadRequestException("Detalles: " + pedido.getDetalles());
        }
    }

    @PostMapping(path = "/{idPedido}/detalle")
    @ApiOperation(value = "Agrega un detalle al pedido")
    public ResponseEntity<Pedido> agregarDetalle(@PathVariable Integer idPedido, @RequestBody DetallePedido detalle) {

        Pedido body = servicioPedido.agregarDetalle(idPedido, detalle);
        return ResponseEntity.ok(body);
    }

    @GetMapping
    @ApiOperation(value = "Lista todos los pedidos")
    public ResponseEntity<List<Pedido>> todos() {

        List<Pedido> body = servicioPedido.todos();
        return ResponseEntity.ok(body);
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca un pedido por id")
    public ResponseEntity<Pedido> pedidoPorId(@PathVariable Integer id) {

        Optional<Pedido> body = servicioPedido.pedidoPorId(id);

        if (body.isPresent()) {
            return ResponseEntity.ok(body.get());
        } else {
            throw new NotFoundException("Pedido no encontrado. Id: " + id);
        }
    }

    @GetMapping(params = "idObra")
    @ApiOperation(value = "Busca un pedido por id de obra")
    public ResponseEntity<Pedido> pedidoPorIdObra(@RequestParam(name = "idObra") Integer idObra) {

        Optional<Pedido> body = servicioPedido.pedidoPorIdObra(idObra);

        if (body.isPresent()) {
            return ResponseEntity.ok(body.get());
        } else {
            throw new NotFoundException("Pedido no encontrado. Id Obra: " + idObra);
        }
    }

    /*
     * Falta buscar pedido por id de cliente o cuit
    */

    @GetMapping(path = "/{idPedido}/detalle/{id}")
    @ApiOperation(value = "Busca un detalle del pedido en base al id")
    public ResponseEntity<DetallePedido> buscarDetalle(@PathVariable Integer idPedido, @PathVariable Integer id) {

        Optional<DetallePedido> body = servicioPedido.buscarDetalle(idPedido, id);

        if (body.isPresent()) {
            return ResponseEntity.ok(body.get());
        } else {
            throw new NotFoundException("Detalle no encontrado. Id Pedido: " +
                                         idPedido + ", Id Detalle: " + id);
        }
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Actualiza un pedido en base al id")
    public ResponseEntity<Pedido> actualizar(@RequestBody Pedido pedido, @PathVariable Integer id) {

        servicioPedido.actualizar(id, pedido);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Elimina un pedido en base al id")
    public ResponseEntity<Pedido> eliminar(@PathVariable Integer id) {

        servicioPedido.eliminar(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{idPedido}/detalle/{id}")
    @ApiOperation(value = "Elimina un detalle del pedido en base al id")
    public ResponseEntity<Pedido> eliminarDetalle(@PathVariable Integer idPedido, @PathVariable Integer id) {

        servicioPedido.eliminarDetalle(idPedido, id);
        return ResponseEntity.ok().build();
    }
}