package com.vollmed.api.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vollmed.api.model.entity.Consulta;

/**
 * DTO que representa os dados de uma Consulta agendada
 * @since branch consultas
 * @author Jean Maciel
 */
public record DadosConsultaAgendada(
    Long id,
    String nomeDoPaciente,
    String nomeDoMedico,
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime dataDaConsulta
) {
    public DadosConsultaAgendada(Consulta consulta) {
        this(consulta.getId(),
             consulta.getPaciente().getNome(),
             consulta.getMedico().getNome(),
             consulta.getDataDaConsulta());
    }
}
