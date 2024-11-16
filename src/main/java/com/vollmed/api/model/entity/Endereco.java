package com.vollmed.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Representa o endereço de um médico ou paciente
 * @since branch medicos
 * @author Jean Maciel
 */
@NoArgsConstructor
@Getter
@Embeddable
public class Endereco {

    @Column(nullable = false, length = 200)
    private String logradouro;

    private Integer numero;

    @Column(length = 200)
    private String complemento;

    @Column(length = 50, nullable = false)
    private String bairro;

    @Column(length = 50, nullable = false)
    private String cidade;

    @Column(nullable = false)
    private UF UF;

    @Column(nullable = false, length = 8)
    private String CEP;

    /**
     * Construtor padrão para criar um endereço
     * @param logradouro até 100 caracteres
     * @param numero até 5 dígitos inteiros (não obrigatório)
     * @param complemento até 200 caracteres (não obrigatório)
     * @param bairro até 50 caracteres
     * @param cidade até 50 caracteres
     * @param UF sigla em caixa alta com o estado
     * @param CEP os 8 dígitos
     */
    public Endereco(String logradouro, Integer numero, String complemento, String bairro,
                    String cidade, UF UF, String CEP) {
        setLogradouro(logradouro);
        setNumero(numero);
        setComplemento(complemento);
        setBairro(bairro);
        setCidade(cidade);
        setUF(UF);
        setCEP(CEP);
    }

    public void setLogradouro(String logradouro) {
        if (logradouro != null && !logradouro.isEmpty()) {
            this.logradouro = logradouro;
        }
    }

    public void setNumero(Integer numero) {
        if (numero != null && numero > 0) {
            this.numero = numero;
        } else {
            this.numero = null;
        }
    }

    public void setComplemento(String complemento) {
        if (complemento != null && !complemento.isEmpty()) {
            this.complemento = complemento;
        } else {
            this.complemento = null;
        }
    }

    public void setBairro(String bairro) {
        if (bairro != null && !bairro.isEmpty()) {
            this.bairro = bairro;
        }
    }

    public void setCidade(String cidade) {
        if (cidade != null && !cidade.isEmpty()) {
            this.cidade = cidade;
        }
    }

    public void setUF(UF UF) {
        if (UF != null) {
            this.UF = UF;
        }
    }

    public void setCEP(String CEP) {
        if (CEP != null && CEP.length() == 8) {
            this.CEP = CEP;
        }
    }
}
