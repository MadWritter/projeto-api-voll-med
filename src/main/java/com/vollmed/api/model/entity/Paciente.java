package com.vollmed.api.model.entity;

import com.vollmed.api.model.dto.DadosCadastroPaciente;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um Paciente no sistema
 * @since branch paciente
 * @author Jean Maciel
 */
@NoArgsConstructor
@Getter
@Entity
@Table(name = "paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = false, length = 100, unique = true)
    private String email;
    @Column(nullable = false, length = 11, unique = true)
    private String celular;
    @Column(nullable = false, length = 11, unique = true)
    private String CPF;
    @Embedded
    private Endereco endereco;
    
    /**
     * Construtor padrão para a entidade
     * @param nome até 100 caracteres
     * @param email até 100 caracteres
     * @param celular os 11 dígitos ddd(2) + número (9)
     * @param CPF os 11 dígitos
     * @param endereco com todos os dados internos
     */
    public Paciente(String nome, String email, String celular, String CPF, Endereco endereco) {
        setNome(nome);
        setEmail(email);
        setCelular(celular);
        setCPF(CPF);
        setEndereco(endereco);
    }

    /**
     * Construtor a partir de um DTO com os dados de cadastro.
     * @param dados que vieram na requisição do cadastro.
     */
    public Paciente(DadosCadastroPaciente dados) {
        this(dados.nome(), dados.email(), dados.celular(), dados.CPF(), 
            new Endereco(
                dados.logradouro(), dados.numero(), dados.complemento(), 
                dados.bairro(), dados.cidade(), dados.UF(), dados.CEP()));
    }

    public void setNome(String nome) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        }
    }

    public void setEmail(String email) {
        if (email != null && !email.isBlank()) {
            this.email = email;
        }
    }

    public void setCelular(String celular) {
        if (celular != null && !celular.isBlank()) {
            this.celular = celular;
        }
    }

    public void setCPF(String CPF) {
        if (CPF != null && !CPF.isBlank()) {
            this.CPF = CPF;
        }
    }

    public void setEndereco(Endereco endereco) {
        if (endereco != null) {
            this.endereco = endereco;
        }
    }

    
}
