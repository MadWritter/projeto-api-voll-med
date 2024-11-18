package com.vollmed.api.model.service;

import com.vollmed.api.model.dto.DadosCadastroMedico;
import com.vollmed.api.model.dto.DadosMedicoCadastrado;
import com.vollmed.api.model.entity.Medico;
import com.vollmed.api.model.exceptions.MedicoNaoCadastradoException;
import com.vollmed.api.model.repository.MedicoRepository;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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

    @Transactional(rollbackOn = MedicoNaoCadastradoException.class)
    public DadosMedicoCadastrado cadastrarNovoMedico(DadosCadastroMedico dadosCadastro) throws MedicoNaoCadastradoException {
        Medico medicoParaCadastrar = new Medico(dadosCadastro);
        try {
            Medico medicoCadastrado = medicoRepository.save(medicoParaCadastrar);
            return new DadosMedicoCadastrado(medicoCadastrado);
        } catch (PersistenceException e) {
            throw new MedicoNaoCadastradoException("Erro ao persistir o médico no banco");
        } catch (DataIntegrityViolationException dataException) {
            throw new MedicoNaoCadastradoException("Erro de integridade de dados: " + dataException.getMessage());
        }
    }
}
