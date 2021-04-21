package com.durandsuppicich.danmspedidos.service;

import com.durandsuppicich.danmspedidos.domain.Producto;

public interface IServicioMaterial {

    Integer stockDisponible(Producto producto);
    
}
