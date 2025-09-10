package com.desafioorla.projetofuncionario.dto;

import java.time.LocalDate;
import java.util.List;

public record ProjetoResponseDTO(
    Long id,
    String nome,
    LocalDate dataCriacao,
    List<FuncionarioResponseDTO> funcionarios
) {}
