package com.vollmed.api.builders;

import com.vollmed.api.model.dto.DadosAtualizacaoPaciente;
import com.vollmed.api.model.entity.UF;

public class DadosAtualizacaoPacienteBuilder {
    
    private DadosAtualizacaoPaciente dados;

    private DadosAtualizacaoPacienteBuilder() {}

    public static DadosAtualizacaoPacienteBuilder dadosDeAtualizacao() {
        return new DadosAtualizacaoPacienteBuilder();
    }

    public DadosAtualizacaoPacienteBuilder validos() {
        this.dados = 
            new DadosAtualizacaoPaciente(
                "Teste2", 
                "91999998765", 
                "logrd", 
                81, 
                "teste", 
                "bairrox", 
                "belém", 
                UF.PA, 
                "77680200");
        return this;
    }

    public DadosAtualizacaoPaciente agora() {
        return this.dados;
    }
}
