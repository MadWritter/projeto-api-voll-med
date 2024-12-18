package com.vollmed.api.model.service;

import static com.vollmed.api.builders.DadosAgendamentoConsultaBuilder.dadosDeAgendamento;
import static com.vollmed.api.builders.DadosCadastroMedicoBuilder.dadosDeCadastroMedico;
import static com.vollmed.api.builders.DadosCadastroPacienteBuilder.dadosDeCadastro;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vollmed.api.model.dto.DadosAgendamentoConsulta;
import com.vollmed.api.model.dto.DadosConsultaAgendada;
import com.vollmed.api.model.entity.Consulta;
import com.vollmed.api.model.entity.Especialidade;
import com.vollmed.api.model.entity.Medico;
import com.vollmed.api.model.entity.Paciente;
import com.vollmed.api.model.repository.ConsultaRepository;
import com.vollmed.api.model.repository.MedicoRepository;
import com.vollmed.api.model.repository.PacienteRepository;

@ExtendWith(MockitoExtension.class)
public class ConsultaServiceTest {

    @InjectMocks
    private ConsultaService consultaService;
    @Mock
    private ConsultaRepository consultaRepository;
    @Mock
    private PacienteRepository pacienteRepository;
    @Mock
    private MedicoRepository medicoRepository;

    private DadosAgendamentoConsulta dadosAgendamentoConsulta;
    private Paciente paciente;
    private Medico medico;

    @BeforeEach
    public void getDados() {
        dadosAgendamentoConsulta = dadosDeAgendamento().validos().agora();
        paciente = new Paciente(dadosDeCadastro().validos().agora());
        medico = new Medico(dadosDeCadastroMedico().validos().agora());
    }

    @Test
    @DisplayName("Deve registrar uma consulta pela Especialidade do médico")
    public void deveRegistrarUmaConsulta() {
        var consulta = new Consulta(paciente, medico, dadosAgendamentoConsulta.dataDaConsulta());

        when(pacienteRepository.findByIdAndAtivoTrue(anyLong())).thenReturn(Optional.of(paciente));
        when(medicoRepository.findAllByEspecialidadeAndAtivoTrue(any(Especialidade.class))).thenReturn(List.of(medico));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);

        DadosConsultaAgendada dados = consultaService.cadastrarConsulta(dadosAgendamentoConsulta);

        assertNotNull(dados);
        assertEquals(consulta.getMedico().getNome(), dados.nomeDoMedico());
        assertEquals(consulta.getPaciente().getNome(), dados.nomeDoPaciente());
    }

    //TODO implementar as demais validações do serviço
}
