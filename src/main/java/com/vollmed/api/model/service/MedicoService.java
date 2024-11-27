package com.vollmed.api.model.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.vollmed.api.model.dto.DadosAtualizacaoMedico;
import com.vollmed.api.model.dto.DadosCadastroMedico;
import com.vollmed.api.model.dto.DadosMedicoCadastrado;
import com.vollmed.api.model.entity.Medico;
import com.vollmed.api.model.repository.MedicoRepository;

import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

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
    @Transactional(rollbackOn = PersistenceException.class)
    public DadosMedicoCadastrado cadastrarNovoMedico(DadosCadastroMedico dadosCadastro) {
        Medico medicoParaCadastrar = new Medico(dadosCadastro);
        try {
            Medico medicoCadastrado = medicoRepository.save(medicoParaCadastrar);
            return new DadosMedicoCadastrado(medicoCadastrado);
        } catch (PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao persistir o médico no banco, tente novamente mais tarde");
        }
    }

    /**
     * Retorna um Médico a partir de um ID
     * @param ID que veio na url da requisição
     * @return um DTO com os dados do médico, caso tenha um com o id
     * correspondente
     */
    public DadosMedicoCadastrado findMedicoByID(Long ID) {
        Optional<Medico> medicoConsultado = medicoRepository.findByIdAndAtivoTrue(ID);
        return medicoConsultado.map(this::converterParaDTO).orElse(null);
    }

    /**
     * Retorna todos os médicos cadastrados no banco
     * @return um Page com os DTOs dos médicos cadastrados
     */
    public Page<DadosMedicoCadastrado> findAll(Pageable pageable) {
        return medicoRepository.findAllByAtivoTrue(pageable).map(this::converterParaDTO);
    }

    /**
     * Faz o parsing para o DTO, que deverá ser devolvido na paginação
     * @param medico consultado no findAll ou no findMedicoByID
     * @return um DTO com os dados do médico
     */
    private DadosMedicoCadastrado converterParaDTO(Medico medico) {
        return new DadosMedicoCadastrado(medico);
    }

    /**
     * Faz a atualização dos dados do médico com o id informado
     * @param ID que veio na url
     * @param dadosDeAtualizacao que vieram no corpo da requisição
     * @return um DTO com os dados atualizados do médico
     */
    public DadosMedicoCadastrado atualizarMedico(Long ID, DadosAtualizacaoMedico dadosDeAtualizacao) {

        if (dadosDeAtualizacao.nome() == null && dadosDeAtualizacao.celular() == null &&
            dadosDeAtualizacao.logradouro() == null && dadosDeAtualizacao.numero() == null &&
            dadosDeAtualizacao.complemento() == null && dadosDeAtualizacao.bairro() == null &&
            dadosDeAtualizacao.cidade() == null && dadosDeAtualizacao.UF() == null &&
            dadosDeAtualizacao.CEP() == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Nenhum campo atualizável informado, somente nome, celular e dados de endereço são atualizáveis");
        }

        Optional<Medico> medicoConsultado = medicoRepository.findByIdAndAtivoTrue(ID);
        if (medicoConsultado.isPresent()) {
            medicoConsultado.get().atualizarMedico(dadosDeAtualizacao);
            medicoRepository.flush();
            return new DadosMedicoCadastrado(medicoConsultado.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O ID informado não tem um correspondente");
        }
    }

    /**
     * Faz a exclusão de um médico no sistema
     * @param ID que vem na url
     * @return um DTO com os dados do médico excluido.
     */
    public DadosMedicoCadastrado excluirMedico(Long ID) {
        Optional<Medico> medicoConsultado = medicoRepository.findByIdAndAtivoTrue(ID);
        if (medicoConsultado.isPresent()) {
            medicoConsultado.get().setAtivo(false);
            medicoRepository.flush();
            return new DadosMedicoCadastrado(medicoConsultado.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O ID informado não tem um correspondente");
        }
    }
}
