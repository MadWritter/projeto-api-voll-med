package com.vollmed.api.builders;

import com.vollmed.api.model.dto.DadosCadastroMedico;
import com.vollmed.api.model.entity.Especialidade;
import com.vollmed.api.model.entity.UF;

public class DadosCadastroMedicoBuilder {

    private DadosCadastroMedico dadosCadastroMedico;

    private DadosCadastroMedicoBuilder() {}

    public static DadosCadastroMedicoBuilder dadosDeCadastroMedico() {
        return new DadosCadastroMedicoBuilder();
    }

    public DadosCadastroMedicoBuilder validos() {
        this.dadosCadastroMedico = new DadosCadastroMedico(
                "Teste",
                "teste@gmail.com",
                "91988880000",
                "123456",
                Especialidade.CARDIOLOGIA,
                "Rua das Flores",
                null,
                null,
                "São Jorge",
                "Belém",
                UF.PA,
                "66821500"
        );
        return this;
    }

    public DadosCadastroMedico agora() {
        return this.dadosCadastroMedico;
    }
}
