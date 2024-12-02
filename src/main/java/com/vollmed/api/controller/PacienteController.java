package com.vollmed.api.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.vollmed.api.model.dto.DadosCadastroPaciente;
import com.vollmed.api.model.dto.DadosPacienteCadastrado;
import com.vollmed.api.model.entity.Paciente;
import com.vollmed.api.model.service.PacienteService;

import jakarta.validation.Valid;


/**
 * Endpoint para tratar os recursos do paciente na aplicação
 * @since branch paciente
 * @author Jean Maciel
 * @see Paciente
 */
@RestController
@RequestMapping("/paciente")
public class PacienteController {
    
    private PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    /**
     * Solicita o cadastro de um paciente no sistema.
     * @param dados que vem no JSON no corpo da requisição
     * @param uriBuilder para construir a URI do recurso
     * @return um DTO com os dados do recurso criado.
     */
    @PostMapping
    public ResponseEntity<DadosPacienteCadastrado> cadastrarPaciente(
        @RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
            DadosPacienteCadastrado pacienteCadastrado = pacienteService.cadastrarPaciente(dados);
            URI uri = uriBuilder.path("/paciente/{id}").buildAndExpand(pacienteCadastrado.id()).toUri();
            return ResponseEntity.created(uri).body(pacienteCadastrado);
    }

    /**
     * Solicita os dados de um paciente a partir de um ID
     * @param id que vem na URL
     * @return um DTO com os dados do recurso solicitado
     */
    @GetMapping("/{id}")
    public ResponseEntity<DadosPacienteCadastrado> obterPaciente(@PathVariable Long id) {
        DadosPacienteCadastrado dadosPacienteCadastrado = pacienteService.buscarPaciente(id);
        return ResponseEntity.ok().body(dadosPacienteCadastrado);
    }

    /**
     * Solicita todos os pacientes cadastrados
     * @param pageable para ordenação e paginação do resultado
     * @return um JSON com os dados paginados e ordenados
     */
    @GetMapping
    public ResponseEntity<Page<DadosPacienteCadastrado>> getAll(
        @PageableDefault(sort = "nome", direction = Direction.ASC, size = 10) Pageable pageable) {
            Page<DadosPacienteCadastrado> pacientesCadastrados = pacienteService.findAll(pageable);
            return ResponseEntity.ok(pacientesCadastrados);
    }
}
