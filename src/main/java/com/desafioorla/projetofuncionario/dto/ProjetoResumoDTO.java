package com.desafioorla.projetofuncionario.dto;

import java.time.LocalDate;

public record ProjetoResumoDTO(
    Long id,
    String nome,
    LocalDate dataCriacao
) {}