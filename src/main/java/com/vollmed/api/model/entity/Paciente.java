package com.vollmed.api.model.entity;

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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setCPF(String cPF) {
        CPF = cPF;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    
}
