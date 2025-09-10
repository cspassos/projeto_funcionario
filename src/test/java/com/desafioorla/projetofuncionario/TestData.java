package com.desafioorla.projetofuncionario;

import com.desafioorla.projetofuncionario.dto.FuncionarioRequestDTO;
import com.desafioorla.projetofuncionario.model.Funcionario;
import com.desafioorla.projetofuncionario.model.Projeto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

public final class TestData {

  private static final BigDecimal DEFAULT_SALARIO = new BigDecimal("1000.00");

  /**
   * Cria um Funcionario de exemplo.
   *
   * @param id   ID do funcionário (pode ser null se não precisar)
   * @param nome Nome do funcionário
   * @return Instância de Funcionario pronta para teste
   */
  public static Funcionario preencherFuncionario(Long id, String nome) {
    return Funcionario.builder()
        .id(id)
        .nome(nome)
        .cpf("1234567890" + (id != null ? Math.abs(id % 10) : 0))
        .email((nome != null ? nome.toLowerCase() : "user") + "@ex.com")
        .salario(DEFAULT_SALARIO)
        .build();
  }
  
  /**
   * Cria um Funcionario atualizado com base em um DTO.
   *
   * @param id  ID do funcionário que será atualizado
   * @param dto DTO com os novos dados
   * @return Instância de Funcionario atualizada conforme o DTO
   */
  public static Funcionario funcionarioAtualizado(Long id, FuncionarioRequestDTO dto) {
    return Funcionario.builder()
        .id(id)
        .nome(dto.nome())
        .cpf(dto.cpf())
        .email(dto.email())
        .salario(dto.salario())
        .build();
  }

  /**
   * Cria um Projeto de exemplo.
   *
   * @param id   ID do projeto (pode ser null se não precisar)
   * @param nome Nome do projeto
   * @return Instância de Projeto pronta para teste
   */
  public static Projeto preencherProjeto(Long id, String nome) {
    return Projeto.builder()
        .id(id)
        .nome(nome)
        .dataCriacao(LocalDate.now())
        .funcionarios(new HashSet<>())
        .build();
  }

}