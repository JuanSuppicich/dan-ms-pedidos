package com.durandsuppicich.danmspedidos.dao;

import com.durandsuppicich.danmspedidos.domain.Obra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObraJpaRepository extends JpaRepository<Obra, Integer> {
}
