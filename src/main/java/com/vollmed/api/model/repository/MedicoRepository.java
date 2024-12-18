package com.vollmed.api.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vollmed.api.model.entity.Especialidade;
import com.vollmed.api.model.entity.Medico;

/**
 * Repository para encapsular as transações da entidade Médico
 * @since branch medicos
 * @author Jean Maciel
 */
@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    public Optional<Medico> findByIdAndAtivoTrue(Long id);
    public Page<Medico> findAllByAtivoTrue(Pageable pageable);
    public List<Medico> findAllByEspecialidadeAndAtivoTrue(Especialidade especialidade);
}
