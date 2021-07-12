package com.durandsuppicich.danmspedidos.repository;

import com.durandsuppicich.danmspedidos.domain.Construction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConstructionJpaRepository extends JpaRepository<Construction, Integer> { }
