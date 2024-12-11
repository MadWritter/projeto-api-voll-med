package com.vollmed.api.model.dto;

import com.vollmed.api.model.entity.UF;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO que representa os dados de cadastro do paciente 
 * enviados na requisição
 * @param nome até 100 caracteres
 * @param email até 100 caracteres
 * @param celular os 11 dígitos, DDD(2) + número(9)
 * @param cpf os 11 dígitos do CPF
 * @param logradouro rua, em outras palavras (até 100 caracteres)
 * @param numero até 5 dígitos inteiros (não obrigatório)
 * @param complemento até 200 caracteres (não obrigatório)
 * @param bairro até 50 caracteres
 * @param cidade até 50 caracteres
 * @param UF uma das siglas dos estados em caixa alta (ex: AM, RN...)
 * @param CEP os 8 dígitos do CEP
 */
public record DadosCadastroPaciente(

    @NotBlank(message = "O nome não pode ser nulo")
    @Size(min = 3, max = 100, message = "O nome deve conter entre 3 e 100 caracteres")
    String nome,

    @NotBlank(message = "O email não pode ser nulo")
    @Email(message = "O formato do campo email está incorreto")
    @Size(max = 100, message = "Email deve conter até 100 caracteres")
    String email,

    @NotBlank(message = "O campo celular não pode ser vazio")
    @Size(min = 11, max = 11, message = "O campo celular deve ter os 11 dígitos DDD(2) + Número(9)")
    @Pattern(regexp = "\\d{11}", message = "O celular deve conter apenas números")
    String celular,

    @NotBlank
    @Size(min = 11, max = 11, message = "O CPF deve conter os 11 dígitos obrigatórios")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter apenas números")
    String CPF,

    @NotBlank(message = "Deve conter o logradouro do endereço")
    @Size(min = 5, max = 100, message = "O logradouro deve conter entre 5 e 100 caracteres")
    String logradouro,

    @Min(value = 1, message = "O valor mínimo para o número é 1")
    @Max(value = 99999, message = "O valor máximo para o número é 99999")
    Integer numero,

    @Size(min = 5, max = 200, message = "O complemento deve conter entre 5 e 200 caracteres")
    String complemento,

    @NotBlank(message = "O bairro não pode ser vazio")
    @Size(min = 3, max = 50, message = "O bairro deve conter entre 3 e 50 caracteres")
    String bairro,

    @NotBlank(message = "A cidade não pode ser vazio")
    @Size(min = 3, max = 50, message = "A cidade deve conter entre 3 e 50 caracteres")
    String cidade,

    @NotNull(message = "Deve conter um dos UF's do Brasil em caixa alta (ex: SP, AM...)")
    UF UF,

    @NotBlank(message = "CEP não pode estar em branco")
    @Size(min = 8, max = 8, message = "Somente os 8 dígitos do CEP")
    @Pattern(regexp = "\\d{8}", message = "Deve conter apenas os números do CEP")
    String CEP
) {
    
}
