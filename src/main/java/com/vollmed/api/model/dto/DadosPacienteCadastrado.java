package com.vollmed.api.model.dto;

import com.vollmed.api.model.entity.Paciente;

/**
 * DTO com os dados do paciente criado
 * @since branch paciente
 * @author Jean Maciel
 */
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
