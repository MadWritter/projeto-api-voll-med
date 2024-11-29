package com.vollmed.api.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = PacienteController.class)
public class PacienteControllerAdvice {
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", 400);
        if (e.getMessage().toLowerCase().contains("paciente.email")) {
            response.put("message", "O email informado já foi cadastrado");
        }
        if (e.getMessage().toLowerCase().contains("paciente.celular")) {
            response.put("message", "O celular informado já foi cadastrado");
        }
        if (e.getMessage().toLowerCase().contains("paciente.cpf")) {
            response.put("message", "O CPF informado já foi cadastrado");
        }
        response.put("path", "/paciente");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
