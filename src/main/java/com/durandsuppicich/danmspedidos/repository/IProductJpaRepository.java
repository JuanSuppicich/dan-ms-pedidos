package com.durandsuppicich.danmspedidos.repository;

import com.durandsuppicich.danmspedidos.domain.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductJpaRepository extends JpaRepository<Product, Integer> { }
