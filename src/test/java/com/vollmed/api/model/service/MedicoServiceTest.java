package com.vollmed.api.model.service;

import com.vollmed.api.model.dto.DadosMedicoCadastrado;
import com.vollmed.api.model.entity.Medico;
import com.vollmed.api.model.exceptions.MedicoNaoCadastradoException;
import com.vollmed.api.model.repository.MedicoRepository;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static com.vollmed.api.builders.DadosCadastroMedicoBuilder.dadosDeCadastroMedico;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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
        when(medicoRepository.save(Mockito.any(Medico.class))).thenReturn(medicoCadastrado);

        DadosMedicoCadastrado dadosRetornados = medicoService.cadastrarNovoMedico(dadosCadastro);
        assertNotNull(dadosRetornados);
    }

    @Test
    @DisplayName("Deve lançar exceção caso o banco esteja fora e não consiga cadastrar")
    public void deveLancarExcecao_casoBancoForaNoCadastro() {
        var dadosDeCadastro = dadosDeCadastroMedico().validos().agora();
        when(medicoRepository.save(Mockito.any(Medico.class))).thenThrow(PersistenceException.class);
        assertThrows(MedicoNaoCadastradoException.class, () -> medicoService.cadastrarNovoMedico(dadosDeCadastro));
    }

    @Test
    @DisplayName("Deve lançar exceção caso algum dado esteja repetido")
    public void deveLancarExcecao_casoAlgumDadoEstejaRepetido() {
        var dadosDeCadastro = dadosDeCadastroMedico().validos().agora();
        when(medicoRepository.save(Mockito.any(Medico.class))).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DataIntegrityViolationException.class, () -> medicoService.cadastrarNovoMedico(dadosDeCadastro));
    }

    @Test
    @DisplayName("Deve buscar um médico a partir de um ID")
    public void deveBuscarUmMedico() {
        var dadosCadastroMedico = dadosDeCadastroMedico().validos().agora();
        var medicoCadastrado = new Medico(dadosCadastroMedico);
        when(medicoRepository.findById(anyLong())).thenReturn(Optional.of(medicoCadastrado));

        DadosMedicoCadastrado dadosCadastrado = medicoService.findMedicoByID(1L);

        assertNotNull(dadosCadastrado);
    }

    @Test
    @DisplayName("Deve retornar null caso não encontre um médico")
    public void deveRetornarNull_casoMedicoNaoExista() {
        when(medicoRepository.findById(anyLong())).thenReturn(Optional.empty());

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
        when(medicoRepository.findAll()).thenReturn(List.of(medico1, medico2, medico3));

        List<DadosMedicoCadastrado> medicosCadastrados = medicoService.findAll();
        assertNotNull(medicosCadastrados);
        assertFalse(medicosCadastrados.isEmpty());
    }
}