package com.vollmed.api.model.exceptions;

import lombok.Getter;

/**
 * Representa as exceções de cadastro do médico
 * @since branch medicos
 * @author Jean Maciel
 */
@Getter
public class MedicoNaoCadastradoException extends RuntimeException {

    String mensagem;

    public MedicoNaoCadastradoException(String mensagem) {
        this.mensagem = mensagem;
    }
}
