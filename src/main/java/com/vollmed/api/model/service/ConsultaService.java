package com.vollmed.api.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.vollmed.api.model.dto.DadosAgendamentoConsulta;
import com.vollmed.api.model.dto.DadosConsultaAgendada;
import com.vollmed.api.model.entity.Consulta;
import com.vollmed.api.model.entity.Medico;
import com.vollmed.api.model.entity.Paciente;
import com.vollmed.api.model.repository.ConsultaRepository;
import com.vollmed.api.model.repository.MedicoRepository;
import com.vollmed.api.model.repository.PacienteRepository;

/**
 * Serviço que abstrai as transações pertinences as consultas médicas
 * @since branch consultas
 * @author Jean Maciel
 */
@Service
public class ConsultaService {
    
    private ConsultaRepository consultaRepository;
    private PacienteRepository pacienteRepository;
    private MedicoRepository medicoRepository;

    public ConsultaService(ConsultaRepository consultaRepository, PacienteRepository pacienteRepository, MedicoRepository medicoRepository) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public DadosConsultaAgendada cadastrarConsulta(DadosAgendamentoConsulta dados) {
        Optional<Paciente> pacienteConsultado = pacienteRepository.findByIdAndAtivoTrue(dados.pacienteId());
        if (!pacienteConsultado.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum paciente cadastrado com o ID informado");
        }
        Optional<Medico> medicoConsultado;

        if(dados.especialidade() != null) {
            List<Medico> medicosComEspecialidadeInformada = medicoRepository.findAllByEspecialidadeAndAtivoTrue(dados.especialidade());
            if (!medicosComEspecialidadeInformada.isEmpty()) {
                medicoConsultado = Optional.of(medicosComEspecialidadeInformada.get(0));
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum médico com a especialidade informada disponível");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Precisa Especialidade do médico para registrar a consulta");
        }
        Consulta consultaCadastrada = consultaRepository.save(new Consulta(pacienteConsultado.get(), medicoConsultado.get(), dados.dataDaConsulta()));
        return new DadosConsultaAgendada(consultaCadastrada);
    }
}

