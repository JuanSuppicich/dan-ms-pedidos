package com.durandsuppicich.danmspedidos.service;

import com.durandsuppicich.danmspedidos.domain.Obra;

public interface IServicioCliente {

    Double saldoCliente(Obra obra);

    Double maximoSaldoNegativo(Obra obra);

}
