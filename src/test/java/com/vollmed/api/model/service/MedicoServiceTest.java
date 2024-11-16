package com.vollmed.api.model.service;

import com.vollmed.api.model.dto.DadosMedicoCadastrado;
import com.vollmed.api.model.entity.Medico;
import com.vollmed.api.model.exceptions.MedicoNaoCadastradoException;
import com.vollmed.api.model.repository.MedicoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
}