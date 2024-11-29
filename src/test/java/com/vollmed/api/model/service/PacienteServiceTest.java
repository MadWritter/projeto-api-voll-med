package com.vollmed.api.model.service;

import static com.vollmed.builders.DadosCadastroPacienteBuilder.dadosDeCadastro;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
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
}