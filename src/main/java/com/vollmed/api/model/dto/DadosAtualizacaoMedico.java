package com.vollmed.api.model.dto;

import com.vollmed.api.model.entity.UF;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DadosAtualizacaoMedico(
        @Size(min = 3, max = 100, message = "Nome deve conter entre 3 e 100 caracteres") 
        String nome,

        @Size(min = 11, max = 11, message = "O campo celular deve ter os 11 dígitos DDD(2) + Número(9)")
        @Pattern(regexp = "\\d{11}", message = "O celular deve conter apenas números")
        String celular,

        @Size(min = 5, max = 100, message = "O logradouro deve conter entre 5 e 100 caracteres")
        String logradouro,

        @Min(value = 1, message = "O valor mínimo para o número é 1")
        @Max(value = 99999, message = "O valor máximo para o número é 99999")
        Integer numero,

        @Size(min = 5, max = 200, message = "O complemento deve conter entre 5 e 200 caracteres")
        String complemento,

        @Size(min = 3, max = 50, message = "O bairro deve conter entre 3 e 50 caracteres")
        String bairro,

        @Size(min = 3, max = 50, message = "A cidade deve conter entre 3 e 50 caracteres")
        String cidade,

        @NotNull(message = "Deve conter um dos UF's do Brasil em caixa alta (ex: SP, AM...")
        UF UF,

        @NotBlank(message = "CEP não pode estar em branco")
        @Size(min = 8, max = 8, message = "Somente os 8 dígitos do CEP")
        @Pattern(regexp = "\\d{8}", message = "Deve conter apenas os números do CEP")
        String CEP
) {

}
