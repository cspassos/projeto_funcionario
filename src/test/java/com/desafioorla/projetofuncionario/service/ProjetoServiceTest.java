package com.desafioorla.projetofuncionario.service;

import com.desafioorla.projetofuncionario.dto.ProjetoRequestDTO;
import com.desafioorla.projetofuncionario.model.Funcionario;
import com.desafioorla.projetofuncionario.model.Projeto;
import com.desafioorla.projetofuncionario.repository.FuncionarioRepository;
import com.desafioorla.projetofuncionario.repository.ProjetoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

import static com.desafioorla.projetofuncionario.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetoServiceTest {

  @Mock
  private ProjetoRepository projetoRepository;

  @Mock
  private FuncionarioRepository funcionarioRepository;

  @InjectMocks
  private ProjetoService service;

  @Test
  void deveCriarProjeto_semFuncionarios_quandoIdsNull() {
    var dto = new ProjetoRequestDTO("Apollo", null);

    Projeto esperado = preencherProjeto(null, "Apollo");

    when(projetoRepository.save(any(Projeto.class))).thenReturn(esperado);

    Projeto out = service.create(dto);

    assertThat(out.getNome()).isEqualTo("Apollo");
    assertThat(out.getFuncionarios()).isEmpty();
  }

  @Test
  void deveCriarProjeto_comFuncionarios_quandoIdsPresentes() {
    var dto = new ProjetoRequestDTO("Apollo", Set.of(1L, 2L));

    when(funcionarioRepository.findById(1L))
        .thenReturn(Optional.of(preencherFuncionario(1L, "A")));
    when(funcionarioRepository.findById(2L))
        .thenReturn(Optional.of(preencherFuncionario(2L, "B")));

    Projeto esperado = preencherProjeto(null, "Apollo");
    esperado.getFuncionarios().addAll(Set.of(
        preencherFuncionario(1L, "A"),
        preencherFuncionario(2L, "B")
    ));

    when(projetoRepository.save(any(Projeto.class))).thenReturn(esperado);

    Projeto out = service.create(dto);

    assertThat(out.getNome()).isEqualTo("Apollo");
    assertThat(out.getFuncionarios())
        .extracting(Funcionario::getId)
        .containsExactlyInAnyOrder(1L, 2L);
  }

  @Test
  void deveLancar404_quandoProjetoNaoEncontrado() {
    when(projetoRepository.findById(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.buscarPeloId(99L))
        .isInstanceOf(ResponseStatusException.class)
        .extracting("statusCode").isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void deveAtualizarProjeto_substituindoVinculos_quandoIdsPresentes() {
    var existente = preencherProjeto(5L, "Antigo");
    existente.getFuncionarios().add(preencherFuncionario(10L, "X"));

    when(projetoRepository.findById(5L)).thenReturn(Optional.of(existente));
    when(funcionarioRepository.findById(1L))
        .thenReturn(Optional.of(preencherFuncionario(1L, "A")));
    when(funcionarioRepository.findById(2L))
        .thenReturn(Optional.of(preencherFuncionario(2L, "B")));

    Projeto atualizado = preencherProjeto(5L, "Novo");
    atualizado.getFuncionarios().addAll(Set.of(
        preencherFuncionario(1L, "A"),
        preencherFuncionario(2L, "B")
    ));

    when(projetoRepository.save(any(Projeto.class))).thenReturn(atualizado);

    var dto = new ProjetoRequestDTO("Novo", Set.of(1L, 2L));
    Projeto out = service.atualizar(5L, dto);

    assertThat(out.getNome()).isEqualTo("Novo");
    assertThat(out.getFuncionarios())
        .extracting(Funcionario::getId)
        .containsExactlyInAnyOrder(1L, 2L);
  }

  @Test
  void deveAtualizarProjeto_mantendoVinculos_quandoIdsNull() {
    var existente = preencherProjeto(7L, "Antigo");
    existente.getFuncionarios().add(preencherFuncionario(100L, "Y"));

    when(projetoRepository.findById(7L)).thenReturn(Optional.of(existente));

    Projeto atualizado = preencherProjeto(7L, "Atualizado");
    atualizado.getFuncionarios().add(preencherFuncionario(100L, "Y"));

    when(projetoRepository.save(any(Projeto.class))).thenReturn(atualizado);

    var dto = new ProjetoRequestDTO("Atualizado", null);
    Projeto out = service.atualizar(7L, dto);

    assertThat(out.getNome()).isEqualTo("Atualizado");
    assertThat(out.getFuncionarios())
        .extracting(Funcionario::getId)
        .containsExactly(100L);
  }

  @Test
  void deveDeletarProjeto_quandoExiste() {
    var existente = preencherProjeto(3L, "Del");
    when(projetoRepository.findById(3L)).thenReturn(Optional.of(existente));

    service.deletar(3L);

    verify(projetoRepository).delete(existente);
  }

}