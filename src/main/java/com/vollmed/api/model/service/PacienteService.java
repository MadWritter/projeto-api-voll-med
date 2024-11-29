package com.vollmed.api.model.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.vollmed.api.model.dto.DadosCadastroPaciente;
import com.vollmed.api.model.dto.DadosPacienteCadastrado;
import com.vollmed.api.model.entity.Paciente;
import com.vollmed.api.model.repository.PacienteRepository;

import jakarta.persistence.PersistenceException;

@Service
public class PacienteService {
    
    private PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public DadosPacienteCadastrado cadastrarPaciente(DadosCadastroPaciente dadosDeCadastro) {
        try {
            Paciente pacienteCadastrado = pacienteRepository.save(new Paciente(dadosDeCadastro));
            return new DadosPacienteCadastrado(pacienteCadastrado);
        } catch(PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar a solicitação doc cadastro, tente novamente em instantes");
        }
    }

    //TODO implementar o serviço
}
