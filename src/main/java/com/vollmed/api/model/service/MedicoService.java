package com.vollmed.api.model.service;

import com.vollmed.api.model.dto.DadosCadastroMedico;
import com.vollmed.api.model.dto.DadosMedicoCadastrado;
import com.vollmed.api.model.entity.Medico;
import com.vollmed.api.model.exceptions.MedicoNaoCadastradoException;
import com.vollmed.api.model.repository.MedicoRepository;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço para a entidade Médico
 * @see Medico
 * @since branch medico
 * @author Jean Maciel
 */
@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    /**
     * Realiza o cadastro do Médico no sistema
     * @param dadosCadastro que vem da requisição
     * @return um DTO com os dados cadastrados
     */
    @Transactional(rollbackOn = MedicoNaoCadastradoException.class)
    public DadosMedicoCadastrado cadastrarNovoMedico(DadosCadastroMedico dadosCadastro) {
        Medico medicoParaCadastrar = new Medico(dadosCadastro);
        try {
            Medico medicoCadastrado = medicoRepository.save(medicoParaCadastrar);
            return new DadosMedicoCadastrado(medicoCadastrado);
        } catch (PersistenceException e) {
            throw new MedicoNaoCadastradoException("Erro ao persistir o médico no banco");
        }
    }

    /**
     * Retorna um Médico a partir de um ID
     * @param ID que veio na url da requisição
     * @return um DTO com os dados do médico, caso tenha um com o id
     * correspondente
     */
    public DadosMedicoCadastrado findMedicoByID(Long ID) {
        Optional<Medico> medicoConsultado = medicoRepository.findById(ID);
        return medicoConsultado.map(DadosMedicoCadastrado::new).orElse(null);
    }

    /**
     * Retorna todos os médicos cadastrados no banco
     * @return uma Lista com os DTOs dos médicos cadastrados
     */
    public List<DadosMedicoCadastrado> findAll() {
        return medicoRepository.findAll().stream()
                .map(DadosMedicoCadastrado::new).collect(Collectors.toList());
    }
}
