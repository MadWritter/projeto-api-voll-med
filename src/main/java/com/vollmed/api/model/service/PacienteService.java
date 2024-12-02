package com.vollmed.api.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            "Erro ao processar a solicitação do cadastro, tente novamente em instantes");
        }
    }

    /**
     * Faz a consulta de um paciente a partir de um ID
     * @param ID que veio na URI
     * @return um DTO com os dados do paciente
     */
    public DadosPacienteCadastrado buscarPaciente(Long ID) {
        try {
            return pacienteRepository.findById(ID).map(DadosPacienteCadastrado::new)
                .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "O ID informado não tem recurso correspondente"));
        } catch(PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
            "Erro ao processar a solicitação, tente novamente em instantes");
        }
    }

    /**
     * Solicita os dados de todos os pacientes cadastrados
     * @param pageable que veio na requisição
     * @return um Page com os dados dos pacientes cadastrados
     * @throws ResponseStatusException caso não consiga solicitar ao banco ou 
     * nenhum recurso esteja persistido no banco.
     */
    public Page<DadosPacienteCadastrado> findAll(Pageable pageable) {
        try {
            Page<Paciente> pacientes = pacienteRepository.findAll(pageable);
            if (!pacientes.isEmpty()) {
                return pacientes.map(DadosPacienteCadastrado::new);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum recurso encontrado");
            }
        } catch(PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
            "Erro ao processar a solicitação, tente novamente em instantes");
        } 
    }

    //TODO implementar o serviço
}
