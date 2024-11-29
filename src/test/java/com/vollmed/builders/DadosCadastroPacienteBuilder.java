package com.vollmed.builders;

import com.vollmed.api.model.dto.DadosCadastroPaciente;
import com.vollmed.api.model.entity.UF;

public class DadosCadastroPacienteBuilder {
    
    private DadosCadastroPaciente dadosCadastroPaciente;

    private DadosCadastroPacienteBuilder() {}

    public static DadosCadastroPacienteBuilder dadosDeCadastro() {
        return new DadosCadastroPacienteBuilder();
    }

    public DadosCadastroPacienteBuilder validos() {
        this.dadosCadastroPaciente = 
            new DadosCadastroPaciente(
                "Teste", "teste@gmail.com", "91999990000", "12345678900",
                 "teste", 21, "teste de local", "compl", "Teste", 
                 UF.AC, "66777888");

        return this;
    }

    public DadosCadastroPaciente agora() {
        return this.dadosCadastroPaciente;
    }
}
