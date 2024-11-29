package com.vollmed.api.model.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.vollmed.api.model.dto.DadosCadastroPaciente;
import com.vollmed.api.model.dto.DadosPacienteCadastrado;
import com.vollmed.api.model.entity.Paciente;
import com.vollmed.api.model.repository.PacienteRepository;

import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

/**
 * Serviço para as transações referentes ao Paciente
 * @since branch paciente
 * @author Jean Maciel
 * @see Paciente
 */
@Service
public class PacienteService {
    
    private PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    /**
     * Faz o cadastro de um paciente no sistema
     * @param dadosDeCadastro que vieram na requisiçãi
     * @return um DTO com os dados cadastrados.
     * @throws ResponseStatusException caso haja um problema na transação
     */
    @Transactional(rollbackOn = PersistenceException.class)
    public DadosPacienteCadastrado cadastrarPaciente(DadosCadastroPaciente dadosDeCadastro) {
        try {
            Paciente pacienteCadastrado = pacienteRepository.save(new Paciente(dadosDeCadastro));
            return new DadosPacienteCadastrado(pacienteCadastrado);
        } catch(PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
            "Erro ao processar a solicitação doc cadastro, tente novamente em instantes");
        }
    }

    //TODO implementar o serviço
}
