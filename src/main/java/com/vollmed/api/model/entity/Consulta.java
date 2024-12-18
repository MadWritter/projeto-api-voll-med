package com.vollmed.api.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa uma consulta médica na aplicação
 * @since branch consultas
 * @author Jean Maciel
 */
@NoArgsConstructor
@Getter
@Entity
@Table(name = "consulta")
public class Consulta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(nullable = false)
    private LocalDateTime dataDaConsulta;

    /**
     * Construtor padrão para uma Consulta
     * @param paciente que irá se consultar
     * @param medico disponível para atender a consulta
     * @param dataDaConsulta que será efetuada.
     */
    public Consulta(Paciente paciente, Medico medico, LocalDateTime dataDaConsulta) {
        setPaciente(paciente);
        setMedico(medico);
        setDataDaConsulta(dataDaConsulta);
    }

    public void setPaciente(Paciente paciente) {
        if (paciente != null) {
            this.paciente = paciente;
        }
    }

    public void setMedico(Medico medico) {
        if (medico != null) {
            this.medico = medico;
        }
    }

    public void setDataDaConsulta(LocalDateTime dataDaConsulta) {
        if (dataDaConsulta != null) {
            this.dataDaConsulta = dataDaConsulta;
        }
    }

    
}
