package com.vollmed.api.builders;

import java.time.LocalDateTime;

import com.vollmed.api.model.dto.DadosAgendamentoConsulta;
import com.vollmed.api.model.entity.Especialidade;

public class DadosAgendamentoConsultaBuilder {
    
    private DadosAgendamentoConsulta dados;

    private DadosAgendamentoConsultaBuilder() {}

    public static DadosAgendamentoConsultaBuilder dadosDeAgendamento() {
        return new DadosAgendamentoConsultaBuilder();
    }

    public DadosAgendamentoConsultaBuilder validos() {
        this.dados = new DadosAgendamentoConsulta(1L, Especialidade.CARDIOLOGIA, LocalDateTime.now());
        return this;
    }

    public DadosAgendamentoConsulta agora() {
        return this.dados;
    }
}
