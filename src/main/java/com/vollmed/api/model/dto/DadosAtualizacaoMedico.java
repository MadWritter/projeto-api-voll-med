package com.vollmed.api.model.dto;

import com.vollmed.api.model.entity.UF;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO que representa os dados de atualização vindos na requisição.
 * @param nome entre 3 e 100 caracteres
 * @param celular com os 11 dígitos
 * @param logradouro entre 5 e 100 caracteres
 * @param numero entre 1 e 99999
 * @param complemento entre 5 e 200 caracteres
 * @param bairro entre 3 e 50 caracteres
 * @param cidade entre 3 e 50 caracteres
 * @param UF uma das siglas das unidades federativas do BR
 * @param CEP o CEP com 8 dígitos
 */
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

        UF UF,
        
        @Size(min = 8, max = 8, message = "Somente os 8 dígitos do CEP")
        @Pattern(regexp = "\\d{8}", message = "Deve conter apenas os números do CEP")
        String CEP
) {

}
