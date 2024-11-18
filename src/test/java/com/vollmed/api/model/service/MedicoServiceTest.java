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

import static com.vollmed.api.builders.DadosCadastroMedicoBuilder.dadosDeCadastroMedico;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicoServiceTest {

    @InjectMocks
    private MedicoService medicoService;
    @Mock
    private MedicoRepository medicoRepository;

    @Test
    @DisplayName("Deve cadastrar um médico corretamente")
    public void deveCadastrarUmMedico() throws MedicoNaoCadastradoException {
        var dadosCadastro = dadosDeCadastroMedico().validos().agora();
        var medicoCadastrado = new Medico(dadosCadastro);
        when(medicoRepository.save(Mockito.any(Medico.class))).thenReturn(medicoCadastrado);

        DadosMedicoCadastrado dadosRetornados = medicoService.cadastrarNovoMedico(dadosCadastro);
        assertNotNull(dadosRetornados);
    }

    @Test
    @DisplayName("Deve lançar exceção caso o DTO seja nulo no cadastro")
    public void deveLancaoExcecao_casoDTONuloCadastro() throws MedicoNaoCadastradoException {
        assertThrows(NullPointerException.class, () -> medicoService.cadastrarNovoMedico(null));
    }

    @Test
    @DisplayName("Deve lançar exceção caso o banco esteja fora e não consiga cadastrar")
    public void deveLancarExcecao_casoBancoForaNoCadastro() throws MedicoNaoCadastradoException {
        var dadosDeCadastro = dadosDeCadastroMedico().validos().agora();
        when(medicoRepository.save(Mockito.any(Medico.class))).thenThrow(PersistenceException.class);
        assertThrows(MedicoNaoCadastradoException.class, () -> medicoService.cadastrarNovoMedico(dadosDeCadastro));
    }

    @Test
    @DisplayName("Deve lançar exceção caso algum dado esteja repetido")
    public void deveLancarExcecao_casoAlgumDadoEstejaRepetido() throws MedicoNaoCadastradoException {
        var dadosDeCadastro = dadosDeCadastroMedico().validos().agora();
        when(medicoRepository.save(Mockito.any(Medico.class))).thenThrow(DataIntegrityViolationException.class);
        assertThrows(MedicoNaoCadastradoException.class, () -> medicoService.cadastrarNovoMedico(dadosDeCadastro));
    }
}