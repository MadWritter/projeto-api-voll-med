package com.vollmed.api.builders;

import com.vollmed.api.model.dto.DadosAtualizacaoMedico;
import com.vollmed.api.model.entity.UF;

public class DadosAtualizacaoMedicoBuilder {

    private DadosAtualizacaoMedico dadosAtualizacaoMedico;

    private DadosAtualizacaoMedicoBuilder() {}

    public static DadosAtualizacaoMedicoBuilder dadosDeAtualização() {
        return new DadosAtualizacaoMedicoBuilder();
    }

    public DadosAtualizacaoMedicoBuilder validos() {
        this.dadosAtualizacaoMedico = 
            new DadosAtualizacaoMedico("Gustavo Antonelli", "93984489090", null, 48, null, null, null, UF.AC, null);
        return this;
    }

    public DadosAtualizacaoMedico agora() {
        return this.dadosAtualizacaoMedico;
    }
}