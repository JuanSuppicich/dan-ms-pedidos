package com.durandsuppicich.danmspedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class DanMsPedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(DanMsPedidosApplication.class, args);
	}

}
