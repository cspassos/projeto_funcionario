package com.desafioorla.projetofuncionario.controllers;

import static com.desafioorla.projetofuncionario.TestData.funcionarioAtualizado;
import static com.desafioorla.projetofuncionario.TestData.preencherFuncionario;
import static com.desafioorla.projetofuncionario.TestData.preencherProjeto;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.desafioorla.projetofuncionario.dto.FuncionarioRequestDTO;
import com.desafioorla.projetofuncionario.dto.ProjetoResumoDTO;
import com.desafioorla.projetofuncionario.service.FuncionarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(FuncionarioController.class)
class FuncionarioControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private FuncionarioService service;

  @Test
  void deveCriarFuncionario() throws Exception {
    var request = new FuncionarioRequestDTO("Ana", "12345678901", "ana@ex.com", new BigDecimal("5000.00"));

    Mockito.when(service.create(Mockito.any(FuncionarioRequestDTO.class)))
        .thenReturn(funcionarioAtualizado(1L, request));

    mockMvc.perform(post("/api/funcionarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.nome").value("Ana"));
  }

  @Test
  void deveListarFuncionarios() throws Exception {
    Mockito.when(service.buscarTodosFuncionarios())
        .thenReturn(List.of(
            preencherFuncionario(1L, "Ana"),
            preencherFuncionario(2L, "Joao")
        ));

    mockMvc.perform(get("/api/funcionarios"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  void deveBuscarFuncionarioPorId() throws Exception {
    Mockito.when(service.buscarPeloId(1L))
        .thenReturn(preencherFuncionario(1L, "Ana"));

    mockMvc.perform(get("/api/funcionarios/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Ana"));
  }

  @Test
  void deveListarProjetosDoFuncionario() throws Exception {
    Mockito.when(service.projetosDoFuncionario(1L))
        .thenReturn(List.of(preencherProjeto(1L, "Apollo")));

    mockMvc.perform(get("/api/funcionarios/1/projetos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].nome").value("Apollo"));
  }

  @Test
  void deveListarProjetosResumoDoFuncionario() throws Exception {
    Mockito.when(service.buscarApenasProjetosFuncionario(1L))
        .thenReturn(List.of(new ProjetoResumoDTO(1L, "Apollo", LocalDate.now())));

    mockMvc.perform(get("/api/funcionarios/1/projetos-resumo"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].nome").value("Apollo"));
  }

  @Test
  void deveAtualizarFuncionario() throws Exception {
    var request = new FuncionarioRequestDTO("Ana", "12345678901", "ana@ex.com", new BigDecimal("6000.00"));

    Mockito.when(service.atualizar(Mockito.eq(1L), Mockito.any(FuncionarioRequestDTO.class)))
        .thenReturn(funcionarioAtualizado(1L, request));

    mockMvc.perform(put("/api/funcionarios/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.salario").value(6000.00));
  }

  @Test
  void deveDeletarFuncionario() throws Exception {
    mockMvc.perform(delete("/api/funcionarios/1"))
        .andExpect(status().isNoContent());

    Mockito.verify(service).deletar(1L);
  }

}