package com.vollmed.api.model.dto;

import com.vollmed.api.model.entity.Paciente;

public record DadosPacienteCadastrado(
    Long id,
    String nome,
    String email,
    String CPF
) {
    public DadosPacienteCadastrado(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCPF());
    }
}
