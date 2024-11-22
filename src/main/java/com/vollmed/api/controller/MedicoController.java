package com.vollmed.api.controller;

import com.vollmed.api.model.dto.DadosCadastroMedico;
import com.vollmed.api.model.dto.DadosMedicoCadastrado;
import com.vollmed.api.model.service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Endpoint para resolver as requisições do médico no sistema
 * @see MedicoService
 * @since branch medicos
 * @author Jean Maciel
 */
@RestController
@RequestMapping("/medico")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    /**
     * Realiza o cadastro de um médico
     * @see DadosCadastroMedico
     * @param dados que vem da requisição
     * @param uriBuilder para construir a URI do cabeçalho location
     * @return um DTO com os dados do médico cadastrado.
     */
    @PostMapping
    public ResponseEntity<DadosMedicoCadastrado> cadastrarMedico(
            @RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
        DadosMedicoCadastrado dadosCadastrados = medicoService.cadastrarNovoMedico(dados);
        URI uri = uriBuilder.path("/medico/{id}").buildAndExpand(dadosCadastrados.id()).toUri();
        return ResponseEntity.created(uri).body(dadosCadastrados);
    }
}
