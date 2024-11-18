package com.vollmed.api.controller;

import com.vollmed.api.model.exceptions.MedicoNaoCadastradoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Responsável por tratar as exceções no MedicoController
 * @since branch medicos
 * @author Jean Maciel
 * @see MedicoController
 */
@RestControllerAdvice(assignableTypes = MedicoController.class)
public class MedicoControllerAdvice {

    @ExceptionHandler(value = MedicoNaoCadastradoException.class)
    public ResponseEntity<Object> handleMedicoNaoCadastradoException(MedicoNaoCadastradoException ex) {
        if (ex.getMensagem().contains("Erro de integridade de dados: ")) {
            if (ex.getMensagem().contains("medico.email")) {
                return ResponseEntity.badRequest().body("Email já cadastrado");
            }
            if (ex.getMensagem().contains("medico.celular")) {
                return ResponseEntity.badRequest().body("Celular já cadastrado");
            }
            if (ex.getMensagem().contains("medico.CRM")) {
                return ResponseEntity.badRequest().body("CRM já cadastrado");
            } else {
                return ResponseEntity.badRequest().body(ex.getMensagem());
            }
        } else {
            return ResponseEntity.internalServerError().body("Erro ao processar a requisição no servidor, tente novamente em instantes");
        }
    }
}
