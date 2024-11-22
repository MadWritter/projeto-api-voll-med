package com.vollmed.api.model.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada ao cadastrar um Medico, seja por banco inativo ou afins.
 * Quando lançada, resolve para uma resposta HTTP 500 com uma mensagem de erro
 * @since branch medicos
 * @author Jean Maciel
 */
@Getter
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Ocorreu um erro ao processar o cadastro, tente novamente")
public class MedicoNaoCadastradoException extends RuntimeException {

    String mensagem;

    public MedicoNaoCadastradoException(String mensagem) {
        this.mensagem = mensagem;
    }
}
