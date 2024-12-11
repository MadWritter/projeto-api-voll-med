package com.vollmed.api.controller;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseStatusException handleConnectionException(ConnectException e) {
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar a solicitação, tente novamente em instantes");
    }

    /**
     * Trata as exceções de leitura da requisição http
     * @param e exceção a ser tratada
     * @return uma resposta http de acordo com a exceção
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpNotReadableException(HttpMessageNotReadableException e) {

        // trata as exceções de deserialização do UF no cadastro ou atualização.
        if (e.getMessage().contains("com.vollmed.api.model.entity.UF")) {
            Map<String, Object> reponse = new LinkedHashMap<>();
            reponse.put("timestamp", LocalDateTime.now().toString());
            reponse.put("status", 400);
            reponse.put("message", "Deve conter um dos UF's do Brasil em caixa alta (ex: SP, AM...)");
            reponse.put("path", "/medicos");
            return new ResponseEntity<>(reponse, HttpStatus.BAD_REQUEST);
        }
        
        return ResponseEntity.internalServerError().build();
    }
}
