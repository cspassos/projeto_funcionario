package com.desafioorla.projetofuncionario.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.desafioorla.projetofuncionario.dto.FuncionarioRequestDTO;
import com.desafioorla.projetofuncionario.dto.FuncionarioResponseDTO;
import com.desafioorla.projetofuncionario.dto.ProjetoResponseDTO;
import com.desafioorla.projetofuncionario.dto.ProjetoResumoDTO;
import com.desafioorla.projetofuncionario.mapper.DtoMapper;
import com.desafioorla.projetofuncionario.service.FuncionarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Funcionários", description = "Operações de criação e consulta de funcionários")
@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

  @Autowired
  private FuncionarioService service;

  @Operation(summary = "Criar funcionário")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public FuncionarioResponseDTO create(@RequestBody @Valid FuncionarioRequestDTO dto) {
    return DtoMapper.toDto(service.create(dto));
  }

  @Operation(summary = "Listar todos os funcionários")
  @GetMapping
  public List<FuncionarioResponseDTO> buscarTodosFuncionarios() {
    return service.buscarTodosFuncionarios().stream().map(DtoMapper::toDto).toList();
  }

  @Operation(summary = "Buscar funcionário por ID")
  @GetMapping("/{id}")
  public FuncionarioResponseDTO buscarPeloId(@PathVariable Long id) {
    return DtoMapper.toDto(service.buscarPeloId(id));
  }

  @Operation(summary = "Listar projetos de um funcionário (com funcionários em cada projeto)")
  @GetMapping("/{id}/projetos")
  public List<ProjetoResponseDTO> projetosDoFuncionario(@PathVariable Long id) {
    return service.projetosDoFuncionario(id).stream().map(DtoMapper::toDto).toList();
  }

  @Operation(summary = "Listar apenas os projetos de um funcionário (sem lista de funcionários)")
  @GetMapping("/{id}/projetos-resumo")
  public List<ProjetoResumoDTO> buscarApenasProjetosFuncionario(@PathVariable Long id) {
    return service.buscarApenasProjetosFuncionario(id);
  }
  
  @Operation(summary = "Atualizar funcionário")
  @PutMapping("/{id}")
  public FuncionarioResponseDTO atualizar(@PathVariable Long id,
                                          @RequestBody @Valid FuncionarioRequestDTO dto) {
    return DtoMapper.toDto(service.atualizar(id, dto));
  }

  @Operation(summary = "Excluir funcionário")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletar(@PathVariable Long id) {
    service.deletar(id);
  }
  
}