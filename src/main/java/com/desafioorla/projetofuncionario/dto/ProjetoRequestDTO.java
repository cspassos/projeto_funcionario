package com.desafioorla.projetofuncionario.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;

public record ProjetoRequestDTO(
    @NotBlank String nome,
    Set<Long> funcionariosIds
) {}
