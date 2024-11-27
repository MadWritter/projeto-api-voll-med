package com.vollmed.api.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.vollmed.api.model.dto.DadosAtualizacaoMedico;
import com.vollmed.api.model.dto.DadosCadastroMedico;
import com.vollmed.api.model.dto.DadosMedicoCadastrado;
import com.vollmed.api.model.service.MedicoService;

import jakarta.validation.Valid;

/**
 * Endpoint para resolver as requisições do médico no sistema
 * 
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
     * 
     * @see DadosCadastroMedico
     * @param dados      que vem da requisição
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

    /**
     * Retorna um Médico a partir de um ID
     * 
     * @param id que vem da URL
     * @return HTTP 200 e o DTO no corpo da requisição ou 404 caso não
     *         exista o recurso solicitado
     */
    @GetMapping("/{id}")
    public ResponseEntity<DadosMedicoCadastrado> buscarMedico(@PathVariable Long id) {
        DadosMedicoCadastrado dadosConsultados = medicoService.findMedicoByID(id);
        if (dadosConsultados == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dadosConsultados);
    }

    /**
     * Busca todos os Médicos do sistema
     * 
     * @return uma Lista de DTOs com todos os médicos cadastrados,
     *         além de detalhes sobre os dados requisitados, quantidade, ordenação e
     *         afins
     */
    @GetMapping
    public ResponseEntity<Page<DadosMedicoCadastrado>> buscarTodos(
            @PageableDefault(sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<DadosMedicoCadastrado> dadosConsultados = medicoService.findAll(pageable);
        if (dadosConsultados.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dadosConsultados);
    }

    /**
     * Solicita a atualização dos dados enviados na requisição
     * @param id que vem na url a respeito do Médico a ser atualizado
     * @param dadosDeAtualizacao que vem no corpo da requisição
     * @return um DTO do tipo DadosMedicoCadastrado com os dados que são autorizados
     * exibir e que estejam atualizados.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DadosMedicoCadastrado> atualizarMedico(
            @PathVariable Long id, @RequestBody DadosAtualizacaoMedico dadosDeAtualizacao) {
        DadosMedicoCadastrado dadosAtualizados = medicoService.atualizarMedico(id, dadosDeAtualizacao);
        return ResponseEntity.ok(dadosAtualizados);
    }

    /**
     * Solicita a exclusão de um médico
     * @param id que vem na url
     * @return um DTO com os dados do médico excluído
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<DadosMedicoCadastrado> excluirMedico(@PathVariable Long id) {
        DadosMedicoCadastrado dadosMedicoExcluido = medicoService.excluirMedico(id);
        return ResponseEntity.ok(dadosMedicoExcluido);
    }
}
