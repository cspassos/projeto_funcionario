package com.desafioorla.projetofuncionario.dto;

import java.math.BigDecimal;

public record FuncionarioResponseDTO(
    Long id,
    String nome,
    String cpf,
    String email,
    BigDecimal salario
) {}