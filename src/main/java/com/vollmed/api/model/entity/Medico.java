package com.vollmed.api.model.entity;

import java.util.List;

import com.vollmed.api.model.dto.DadosAtualizacaoMedico;
import com.vollmed.api.model.dto.DadosCadastroMedico;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um médico na aplicação
 * @since branch medicos
 * @author Jean Maciel
 */
@NoArgsConstructor
@Getter
@Entity
@Table(name = "medico")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 11, unique = true)
    private String celular;

    @Column(nullable = false, length = 6, unique = true)
    private String CRM;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;
    
    @Column(nullable = false)
    private Boolean ativo;

    @OneToMany(mappedBy = "medico")
    private List<Consulta> consultas;

    /**
     * Construtor padrão para cadastrar um médico
     * @param nome até 100 caracteres
     * @param email até 100 caracteres
     * @param celular os 11 dígitos ddd(2) + número(9)
     * @param CRM os 6 dígitos
     * @param especialidade sendo uma das seguintes: ORTOPEDIA, CARDIOLOGIA, GINECOLOGIA, DERMATOLOGIA
     * @param endereco do médico
     */
    public Medico(String nome, String email, String celular, String CRM,
                  Especialidade especialidade, Endereco endereco) {
        setNome(nome);
        setEmail(email);
        setCelular(celular);
        setCRM(CRM);
        setEspecialidade(especialidade);
        setEndereco(endereco);
        setAtivo(true);
    }

    /**
     * Construtor para os dados do médico para cadastrar
     * @param dadosCadastroMedico que vieram na requisição
     */
    public Medico(DadosCadastroMedico dadosCadastroMedico) {
        this(dadosCadastroMedico.nome(), dadosCadastroMedico.email(),
        dadosCadastroMedico.celular(), dadosCadastroMedico.CRM(),
        dadosCadastroMedico.especialidade(), new Endereco(
            dadosCadastroMedico.logradouro(), dadosCadastroMedico.numero(),
            dadosCadastroMedico.complemento(), dadosCadastroMedico.bairro(),
            dadosCadastroMedico.cidade(), dadosCadastroMedico.UF(), dadosCadastroMedico.CEP()
            ));
    }

    /**
     * Atualiza na entidade conforme os dados recebidos.
     * @param dadosDeAtualizacao que vieram do serviço
     */
    public void atualizarMedico(DadosAtualizacaoMedico dadosDeAtualizacao) {
        setNome(dadosDeAtualizacao.nome());
        setCelular(dadosDeAtualizacao.celular());
        getEndereco().setLogradouro(dadosDeAtualizacao.logradouro());
        getEndereco().setNumero(dadosDeAtualizacao.numero());
        getEndereco().setComplemento(dadosDeAtualizacao.complemento());
        getEndereco().setBairro(dadosDeAtualizacao.bairro());
        getEndereco().setCidade(dadosDeAtualizacao.cidade());
        getEndereco().setUF(dadosDeAtualizacao.UF());
        getEndereco().setCEP(dadosDeAtualizacao.CEP());
    }
        
    public void setNome(String nome) {
        if (nome != null && !nome.isEmpty()) {
            this.nome = nome;
        }
    }

    public void setEmail(String email) {
        if (email != null && !email.isEmpty()) {
            this.email = email;
        }
    }

    public void setCelular(String celular) {
        if (celular != null && celular.length() == 11) {
            this.celular = celular;
        }
    }

    public void setCRM(String CRM) {
        if (CRM != null && CRM.length() == 6) {
            this.CRM = CRM;
        }
    }

    public void setEspecialidade(Especialidade especialidade) {
        if (especialidade != null) {
            this.especialidade = especialidade;
        }
    }

    public void setEndereco(Endereco endereco) {
        if (endereco != null) {
            this.endereco = endereco;
        }
    }

    public void setAtivo(Boolean status) {
        if (status != null) {
            this.ativo = status;
        }
    }

}
