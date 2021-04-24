package com.durandsuppicich.danmspedidos.service;

import com.durandsuppicich.danmspedidos.domain.Obra;

import org.springframework.stereotype.Service;

@Service
public class ServicioCliente implements IServicioCliente {

    @Override
    public Double saldoCliente(Obra obra) {
        return 0.0;
    }

    @Override
    public Double maximoSaldoNegativo(Obra obra) {
        return 10000.0;
    }

}
