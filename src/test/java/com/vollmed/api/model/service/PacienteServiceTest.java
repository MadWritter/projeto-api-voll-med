package com.vollmed.api.model.service;

import static com.vollmed.api.builders.DadosAtualizacaoPacienteBuilder.dadosDeAtualizacao;
import static com.vollmed.api.builders.DadosCadastroPacienteBuilder.dadosDeCadastro;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;
import com.vollmed.api.model.dto.DadosPacienteCadastrado;
import com.vollmed.api.model.entity.Paciente;
import com.vollmed.api.model.repository.PacienteRepository;

import jakarta.persistence.PersistenceException;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTest {

    @InjectMocks
    private PacienteService pacienteService;

    @Mock
    private PacienteRepository pacienteRepository;

    @Test
    @DisplayName("Deve efetuar o cadastro de um paciente")
    public void deveCadastrarUmPaciente() {
        var dadosDeCadastro = dadosDeCadastro().validos().agora();
        var pacienteCadastrado = new Paciente(dadosDeCadastro);
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteCadastrado);

        DadosPacienteCadastrado dadosCadastrados = pacienteService.cadastrarPaciente(dadosDeCadastro);
        assertNotNull(dadosCadastrados);
    }

    @Test
    @DisplayName("Deve lançar exceção caso o banco esteja fora e não consiga cadastrar")
    public void deveLancarExcecaoCasoOBancoEstejaFora() {
        var dadosDeCadastro = dadosDeCadastro().validos().agora();
        when(pacienteRepository.save(any(Paciente.class))).thenThrow(PersistenceException.class);

        assertThrows(ResponseStatusException.class, () -> pacienteService.cadastrarPaciente(dadosDeCadastro));
    }

    @Test
    @DisplayName("Deve lançar exceção caso algum dado esteja repetido")
    public void deveLancarExcecaoCasoDadoRepetido() {
        var dadosDeCadastro = dadosDeCadastro().validos().agora();
        when(pacienteRepository.save(any(Paciente.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> pacienteService.cadastrarPaciente(dadosDeCadastro));
    }

    @Test
    @DisplayName("Deve retornar um DTO com os dados do paciente a partir do ID")
    public void deveRetornarUmPacientePeloID() {
        var dadosDeCadastro = dadosDeCadastro().validos().agora();
        var pacienteCadastrado = new Paciente(dadosDeCadastro);
        when(pacienteRepository.findByIdAndAtivoTrue(anyLong())).thenReturn(Optional.of(pacienteCadastrado));

        DadosPacienteCadastrado dadosRetornados = pacienteService.buscarPaciente(1L);

        assertNotNull(dadosRetornados);
    }

    @Test
    @DisplayName("Deve lancar exceção caso não tenha um recurso correspondente ao ID informado")
    public void deveLancarExcecao_casoIDInformadoNaoTenhaCorrespondente() {
        when(pacienteRepository.findByIdAndAtivoTrue(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> pacienteService.buscarPaciente(1L));
    }

    @Test
    @DisplayName("Deve retornar uma lista com os pacientes cadastrados")
    public void deveRetornarUmaListaDePacientes() {
        var dadosPaciente = dadosDeCadastro().validos().agora();
        var pageImpl = 
            new PageImpl<>(
                List.of(new Paciente(dadosPaciente), new Paciente(dadosPaciente), new Paciente(dadosPaciente)));
        when(pacienteRepository.findAllByAtivoTrue(any(Pageable.class))).thenReturn(pageImpl);

        Page<DadosPacienteCadastrado> dadosRecebidos = pacienteService.findAll(Pageable.unpaged());
        
        assertNotNull(dadosRecebidos);
    }

    @Test
    @DisplayName("Deve atualizar os dados de um paciente")
    public void deveAtualizarUmPaciente() {
        var dadosPacienteCadastrado = dadosDeCadastro().validos().agora();
        var pacienteCadastrado = new Paciente(dadosPacienteCadastrado);
        var dadosDeAtualizacao = dadosDeAtualizacao().validos().agora();

        when(pacienteRepository.findByIdAndAtivoTrue(anyLong())).thenReturn(Optional.of(pacienteCadastrado));

        DadosPacienteCadastrado dtoRetornado = pacienteService.atualizarPaciente(1L, dadosDeAtualizacao);

        assertNotNull(dtoRetornado);
        assertEquals("Teste2", dtoRetornado.nome());
    }

    @Test
    @DisplayName("Deve excluir um paciente") 
    public void deveExcluirUmPaciente() {
        var dadosPacienteCadastrado = dadosDeCadastro().validos().agora();
        var pacienteCadastrado = new Paciente(dadosPacienteCadastrado);

        when(pacienteRepository.findByIdAndAtivoTrue(anyLong())).thenReturn(Optional.of(pacienteCadastrado));
        
        DadosPacienteCadastrado dtoRetornado = pacienteService.excluirPaciente(1L);

        assertNotNull(dtoRetornado);
        assertFalse(pacienteCadastrado.getAtivo());
    }
}
