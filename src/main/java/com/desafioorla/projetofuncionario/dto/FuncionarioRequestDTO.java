package com.desafioorla.projetofuncionario.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record FuncionarioRequestDTO(
		@NotBlank String nome,
		@Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos") String cpf, 
		@Email @NotBlank String email,
		@NotNull @DecimalMin("0.0") BigDecimal salario) {
}
