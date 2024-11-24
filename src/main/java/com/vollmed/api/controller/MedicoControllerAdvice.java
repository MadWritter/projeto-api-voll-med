package com.vollmed.api.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Responsável por tratar as exceções no MedicoController
 * @since branch medicos
 * @author Jean Maciel
 * @see MedicoController
 */
@RestControllerAdvice(assignableTypes = MedicoController.class)
public class MedicoControllerAdvice {

    /**
     * Trata as exceções pertinentes a duplicidade de dados no cadastro
     * @param e exceção que informa a duplicidade
     * @return um Bad Request com uma mensagem sobre a duplicidade ocorrida
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityException(DataIntegrityViolationException e) {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", 400);

        // Considerando que vai barrar na primeira duplicidade...

        if (e.getMessage().toLowerCase().contains("medico.email")) {
            response.put("message", "O email informado já foi cadastrado");
        }
        if (e.getMessage().toLowerCase().contains("medico.celular")) {
            response.put("message", "O celular informado já foi cadastrado");
        }
        if (e.getMessage().toLowerCase().contains("medico.crm")) {
            response.put("message", "O CRM informado já foi cadastrado");
        }

        response.put("path","/medico");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Elabora uma resposta para caso o banco esteja fora na requisição
     * @param e exceção que vem quando o DB está off
     * @return uma resposta HTTP 500 informando sobre o problema interno.
     */
    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<Object> handleConnectionException(ConnectException e) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", 500);
        response.put("message", "Erro ao processar a solicitação, tente novamente em instantes");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
