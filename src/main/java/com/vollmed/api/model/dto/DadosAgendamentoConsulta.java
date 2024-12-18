package com.vollmed.api.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vollmed.api.model.entity.Especialidade;

import jakarta.validation.constraints.NotNull;

/**
 * DTO que representa os dados do agendamento da consulta
 * @since branch consultas
 * @author Jean Maciel
 */
public record DadosAgendamentoConsulta(
    @NotNull(message = "O ID do paciente não pode ser nulo")
    Long pacienteId,
    @NotNull(message = "Precisa informar a especialidade do médico que deseja se consultar")
    Especialidade especialidade,
    @NotNull(message = "A data da consulta não pode ser nula")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime dataDaConsulta
) {
    
}
