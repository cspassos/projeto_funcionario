package com.desafioorla.projetofuncionario.mapper;

import com.desafioorla.projetofuncionario.dto.FuncionarioResponseDTO;
import com.desafioorla.projetofuncionario.dto.ProjetoResponseDTO;
import com.desafioorla.projetofuncionario.model.Funcionario;
import com.desafioorla.projetofuncionario.model.Projeto;

import java.util.List;
import java.util.stream.Collectors;

public final class DtoMapper {

  private DtoMapper(){}

  public static FuncionarioResponseDTO toDto(Funcionario f){
    return new FuncionarioResponseDTO(
        f.getId(), f.getNome(), f.getCpf(), f.getEmail(), f.getSalario()
    );
  }

  public static ProjetoResponseDTO toDto(Projeto p){
    List<FuncionarioResponseDTO> fs = p.getFuncionarios()
        .stream()
        .map(DtoMapper::toDto)
        .collect(Collectors.toList());
    return new ProjetoResponseDTO(p.getId(), p.getNome(), p.getDataCriacao(), fs);
  }
}