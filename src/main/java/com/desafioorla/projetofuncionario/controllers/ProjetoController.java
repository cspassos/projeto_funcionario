package com.desafioorla.projetofuncionario.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.desafioorla.projetofuncionario.dto.FuncionarioResponseDTO;
import com.desafioorla.projetofuncionario.dto.ProjetoRequestDTO;
import com.desafioorla.projetofuncionario.dto.ProjetoResponseDTO;
import com.desafioorla.projetofuncionario.mapper.DtoMapper;
import com.desafioorla.projetofuncionario.service.ProjetoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Projetos", description = "Operações de criação e consulta de projetos")
@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {

  @Autowired
  private ProjetoService service;

  @Operation(summary = "Criar projeto")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProjetoResponseDTO create(@RequestBody @Valid ProjetoRequestDTO dto) {
    return DtoMapper.toDto(service.create(dto));
  }

  @Operation(summary = "Listar todos os projetos (com seus funcionários)")
  @GetMapping
  public List<ProjetoResponseDTO> buscarTodosProjetos() {
    return service.buscarTodosProjetos().stream().map(DtoMapper::toDto).toList();
  }

  @Operation(summary = "Buscar projeto por ID (com seus funcionários)")
  @GetMapping("/{id}")
  public ProjetoResponseDTO buscarPeloId(@PathVariable Long id) {
    return DtoMapper.toDto(service.buscarPeloId(id));
  }

  @Operation(summary = "Listar funcionários de um projeto")
  @GetMapping("/{id}/funcionarios")
  public List<FuncionarioResponseDTO> funcionariosDoProjeto(@PathVariable Long id) {
    return service.funcionariosDoProjeto(id).stream().map(DtoMapper::toDto).toList();
  }
  
  @Operation(summary = "Atualizar projeto")
  @PutMapping("/{id}")
  public ProjetoResponseDTO atualizar(@PathVariable Long id,
                                      @RequestBody @Valid ProjetoRequestDTO dto) {
    return DtoMapper.toDto(service.atualizar(id, dto));
  }

  @Operation(summary = "Excluir projeto")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletar(@PathVariable Long id) {
    service.deletar(id);
  }
  
}