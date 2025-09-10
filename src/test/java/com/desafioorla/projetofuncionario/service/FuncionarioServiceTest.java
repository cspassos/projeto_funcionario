package com.desafioorla.projetofuncionario.service;

import static com.desafioorla.projetofuncionario.TestData.funcionarioAtualizado;
import static com.desafioorla.projetofuncionario.TestData.preencherFuncionario;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.desafioorla.projetofuncionario.dto.FuncionarioRequestDTO;
import com.desafioorla.projetofuncionario.dto.ProjetoResumoDTO;
import com.desafioorla.projetofuncionario.model.Funcionario;
import com.desafioorla.projetofuncionario.model.Projeto;
import com.desafioorla.projetofuncionario.repository.FuncionarioRepository;
import com.desafioorla.projetofuncionario.repository.ProjetoRepository;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {

  @Mock
  private FuncionarioRepository repository;

  @Mock
  private ProjetoRepository projetoRepository;

  @InjectMocks
  private FuncionarioService service;

  private FuncionarioRequestDTO dto;

  @BeforeEach
  void setUp() {
    dto = new FuncionarioRequestDTO(
        "Ana",
        "12345678901",
        "ana@ex.com",
        new BigDecimal("5000.00")
    );
  }

  @Test
  void deveCriarFuncionario_quandoCpfEEmailDisponiveis() {
    when(repository.existsByCpf("12345678901")).thenReturn(false);
    when(repository.existsByEmail("ana@ex.com")).thenReturn(false);

    Funcionario funcionario = preencherFuncionario(null, "Ana");
    funcionario.setId(1L);
    when(repository.save(any(Funcionario.class))).thenReturn(funcionario);

    Funcionario out = service.create(dto);

    assertThat(out.getId()).isEqualTo(1L);
    assertThat(out.getNome()).isEqualTo("Ana");
    verify(repository).save(any(Funcionario.class));
  }

  @Test
  void deveLancarExcecao_quandoCpfDuplicado() {
    when(repository.existsByCpf("12345678901")).thenReturn(true);

    assertThatThrownBy(() -> service.create(dto))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("CPF já cadastrado");

    verify(repository, never()).save(any());
  }

  @Test
  void deveBuscarFuncionario_quandoExiste() {
    when(repository.findById(10L)).thenReturn(Optional.of(preencherFuncionario(10L, "Joao")));

    Funcionario out = service.buscarPeloId(10L);

    assertThat(out.getId()).isEqualTo(10L);
    assertThat(out.getNome()).isEqualTo("Joao");
  }

  @Test
  void deveLancar404_quandoFuncionarioNaoExiste() {
    when(repository.findById(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.buscarPeloId(99L))
        .isInstanceOf(ResponseStatusException.class)
        .extracting("statusCode").isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void deveAtualizarFuncionario_quandoDadosValidos() {
      when(repository.findById(5L)).thenReturn(Optional.of(preencherFuncionario(5L, "Old")));
      when(repository.existsByCpfAndIdNot("12345678901", 5L)).thenReturn(false);
      when(repository.existsByEmailAndIdNot("ana@ex.com", 5L)).thenReturn(false);

      when(repository.save(any(Funcionario.class)))
          .thenReturn(funcionarioAtualizado(5L, dto));

      Funcionario out = service.atualizar(5L, dto);

      assertThat(out.getId()).isEqualTo(5L);
      assertThat(out.getNome()).isEqualTo("Ana");
      verify(repository).save(any(Funcionario.class));
  }

  @Test
  void deveDeletarFuncionario_quandoExiste() {
    when(repository.findById(3L)).thenReturn(Optional.of(preencherFuncionario(3L, "Del")));

    service.deletar(3L);

    verify(repository).delete(any(Funcionario.class));
  }

  @Test
  void deveListarProjetosDoFuncionario_quandoExiste() {
    when(repository.existsById(7L)).thenReturn(true);
    when(projetoRepository.findByFuncionarios_Id(7L)).thenReturn(List.of(new Projeto()));

    List<Projeto> lista = service.projetosDoFuncionario(7L);

    assertThat(lista).hasSize(1);
  }

  @Test
  void deveBuscarResumoProjetos_quandoFuncionarioExiste() {
    when(repository.existsById(2L)).thenReturn(true);
    when(projetoRepository.buscarApenasProjetosFuncionario(2L))
        .thenReturn(List.of(new ProjetoResumoDTO(1L, "P1", LocalDate.now())));

    var out = service.buscarApenasProjetosFuncionario(2L);

    assertThat(out).hasSize(1);
    assertThat(out.get(0).nome()).isEqualTo("P1");
  }

}