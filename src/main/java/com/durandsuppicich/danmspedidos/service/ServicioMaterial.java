package com.durandsuppicich.danmspedidos.service;

import com.durandsuppicich.danmspedidos.domain.Producto;

import org.springframework.stereotype.Service;

@Service
public class ServicioMaterial implements IServicioMaterial {
    
    @Override
    public Integer stockDisponible(Producto producto) {
        return Integer.MAX_VALUE; //TODO completar metodo
    }

}
