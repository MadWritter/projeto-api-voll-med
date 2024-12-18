package com.vollmed.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vollmed.api.model.entity.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
    
}
