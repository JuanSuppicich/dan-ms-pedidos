package com.durandsuppicich.danmspedidos.config;

import com.durandsuppicich.danmspedidos.mapper.IOrderMapper;
import com.durandsuppicich.danmspedidos.mapper.IOrderItemMapper;
import com.durandsuppicich.danmspedidos.mapper.OrderItemMapper;
import com.durandsuppicich.danmspedidos.mapper.OrderMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public IOrderMapper orderMapper() {
        return new OrderMapper(orderItemMapper());
    }

    @Bean
    public IOrderItemMapper orderItemMapper() {
        return new OrderItemMapper();
    }
}
