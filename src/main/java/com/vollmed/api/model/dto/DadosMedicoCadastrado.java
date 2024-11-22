package com.vollmed.api.model.dto;

import com.vollmed.api.model.entity.Especialidade;
import com.vollmed.api.model.entity.Medico;

/**
 * DTO para retornar os dados do médico cadastrado
 * @since branch medicos
 * @author Jean Maciel
 */
public record DadosMedicoCadastrado(
        Long id,
        String nome,
        String email,
        String CRM,
        Especialidade especialidade
) {

    public DadosMedicoCadastrado(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCRM(), medico.getEspecialidade());
    }
}
