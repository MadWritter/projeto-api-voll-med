package com.vollmed.api.controller;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.vollmed.api.model.dto.DadosAgendamentoConsulta;
import com.vollmed.api.model.dto.DadosConsultaAgendada;
import com.vollmed.api.model.service.ConsultaService;
import jakarta.validation.Valid;

/**
 * Controller para os recursos pertinentes as consultas médicas
 * @since branch consultas
 * @author Jean Maciel
 */
@RestController
@RequestMapping("/consulta")
public class ConsultaController {
    
    private ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    /**
     * Solicita o cadastro de uma consulta médica
     * @param dados que vem na requisição
     * @param uriBuilder para devolver o location
     * @return um DTO com os dados da consulta agendada
     */
    @PostMapping
    public ResponseEntity<DadosConsultaAgendada> cadastrarConsulta(
        @RequestBody @Valid DadosAgendamentoConsulta dados, UriComponentsBuilder uriBuilder) {
        DadosConsultaAgendada dadosAgendados = consultaService.cadastrarConsulta(dados);
        URI uri = uriBuilder.path("/consulta/{id}").buildAndExpand(dadosAgendados.id()).toUri();
        return ResponseEntity.created(uri).body(dadosAgendados);
    }
}
