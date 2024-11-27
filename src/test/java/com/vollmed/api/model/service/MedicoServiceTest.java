package com.vollmed.api.model.service;

import static com.vollmed.api.builders.DadosAtualizacaoMedicoBuilder.dadosDeAtualização;
import static com.vollmed.api.builders.DadosCadastroMedicoBuilder.dadosDeCadastroMedico;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import com.vollmed.api.model.dto.DadosMedicoCadastrado;
import com.vollmed.api.model.entity.Medico;
import com.vollmed.api.model.repository.MedicoRepository;

import jakarta.persistence.PersistenceException;

@ExtendWith(MockitoExtension.class)
public class MedicoServiceTest {

    @InjectMocks
    private MedicoService medicoService;
    @Mock
    private MedicoRepository medicoRepository;

    @Test
    @DisplayName("Deve cadastrar um médico corretamente")
    public void deveCadastrarUmMedico() {
        var dadosCadastro = dadosDeCadastroMedico().validos().agora();
        var medicoCadastrado = new Medico(dadosCadastro);
        when(medicoRepository.save(any(Medico.class))).thenReturn(medicoCadastrado);

        DadosMedicoCadastrado dadosRetornados = medicoService.cadastrarNovoMedico(dadosCadastro);
        assertNotNull(dadosRetornados);
    }

    @Test
    @DisplayName("Deve lançar exceção caso o banco esteja fora e não consiga cadastrar")
    public void deveLancarExcecao_casoBancoForaNoCadastro() {
        var dadosDeCadastro = dadosDeCadastroMedico().validos().agora();
        when(medicoRepository.save(any(Medico.class))).thenThrow(PersistenceException.class);
        assertThrows(ResponseStatusException.class, () -> medicoService.cadastrarNovoMedico(dadosDeCadastro));
    }

    @Test
    @DisplayName("Deve lançar exceção caso algum dado esteja repetido")
    public void deveLancarExcecao_casoAlgumDadoEstejaRepetido() {
        var dadosDeCadastro = dadosDeCadastroMedico().validos().agora();
        when(medicoRepository.save(any(Medico.class))).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DataIntegrityViolationException.class, () -> medicoService.cadastrarNovoMedico(dadosDeCadastro));
    }

    @Test
    @DisplayName("Deve buscar um médico a partir de um ID")
    public void deveBuscarUmMedico() {
        var dadosCadastroMedico = dadosDeCadastroMedico().validos().agora();
        var medicoCadastrado = new Medico(dadosCadastroMedico);
        when(medicoRepository.findByIdAndAtivoTrue(anyLong())).thenReturn(Optional.of(medicoCadastrado));

        DadosMedicoCadastrado dadosCadastrado = medicoService.findMedicoByID(1L);

        assertNotNull(dadosCadastrado);
    }

    @Test
    @DisplayName("Deve retornar null caso não encontre um médico")
    public void deveRetornarNull_casoMedicoNaoExista() {
        when(medicoRepository.findByIdAndAtivoTrue(anyLong())).thenReturn(Optional.empty());

        DadosMedicoCadastrado dadosMedicoCadastrado = medicoService.findMedicoByID(1L);

        assertNull(dadosMedicoCadastrado);
    }

    @Test
    @DisplayName("Deve retornar todos os médicos cadastrados")
    public void deveRetornarTodosOsMedicos() {
        var dadosCadastroMedico = dadosDeCadastroMedico().validos().agora();
        var medico1 = new Medico(dadosCadastroMedico);
        var medico2 = new Medico(dadosCadastroMedico);
        var medico3 = new Medico(dadosCadastroMedico);
        var pageable = Pageable.unpaged();
        var pageImpl = new PageImpl<>(List.of(medico1, medico2, medico3));

        when(medicoRepository.findAllByAtivoTrue(any(Pageable.class))).thenReturn(pageImpl);
        Page<DadosMedicoCadastrado> medicosCadastrados = medicoService.findAll(pageable);

        assertNotNull(medicosCadastrados);
        assertFalse(medicosCadastrados.isEmpty());
        assertEquals(3, medicosCadastrados.getContent().size());
    }

    @Test
    @DisplayName("Deve atualizar os dados válidos de um médico existente")
    public void deveAtualizarDadosDeUmMedico() {
        var dadosMedicoCadastrado = dadosDeCadastroMedico().validos().agora();
        var dadosDeAtualizacao = dadosDeAtualização().validos().agora();

        when(medicoRepository.findByIdAndAtivoTrue(anyLong())).thenReturn(Optional.of(new Medico(dadosMedicoCadastrado)));
        DadosMedicoCadastrado dadosAtualizados = medicoService.atualizarMedico(1L, dadosDeAtualizacao);

        assertNotNull(dadosAtualizados);
        assertEquals(dadosDeAtualizacao.nome(), dadosAtualizados.nome());
    }

}