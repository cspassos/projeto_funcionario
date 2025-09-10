package com.desafioorla.projetofuncionario.controllers;

import static com.desafioorla.projetofuncionario.TestData.preencherFuncionario;
import static com.desafioorla.projetofuncionario.TestData.preencherProjeto;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.desafioorla.projetofuncionario.dto.ProjetoRequestDTO;
import com.desafioorla.projetofuncionario.service.ProjetoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProjetoController.class)
class ProjetoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ProjetoService service;

  @Test
  void deveCriarProjeto() throws Exception {
    var request = new ProjetoRequestDTO("Apollo", Set.of(1L, 2L));

    Mockito.when(service.create(Mockito.any(ProjetoRequestDTO.class)))
        .thenReturn(preencherProjeto(1L, "Apollo"));

    mockMvc.perform(post("/api/projetos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.nome").value("Apollo"));
  }

  @Test
  void deveListarProjetos() throws Exception {
    Mockito.when(service.buscarTodosProjetos())
        .thenReturn(List.of(
            preencherProjeto(1L, "Apollo"),
            preencherProjeto(2L, "Zeus")
        ));

    mockMvc.perform(get("/api/projetos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  void deveBuscarProjetoPorId() throws Exception {
    Mockito.when(service.buscarPeloId(1L))
        .thenReturn(preencherProjeto(1L, "Apollo"));

    mockMvc.perform(get("/api/projetos/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Apollo"));
  }

  @Test
  void deveListarFuncionariosDoProjeto() throws Exception {
    Mockito.when(service.funcionariosDoProjeto(1L))
        .thenReturn(List.of(
            preencherFuncionario(1L, "Ana"),
            preencherFuncionario(2L, "Joao")
        ));

    mockMvc.perform(get("/api/projetos/1/funcionarios"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].nome").value("Ana"))
        .andExpect(jsonPath("$[1].nome").value("Joao"));
  }

  @Test
  void deveAtualizarProjeto() throws Exception {
    var request = new ProjetoRequestDTO("Apollo Atualizado", Set.of(1L));

    Mockito.when(service.atualizar(Mockito.eq(1L), Mockito.any(ProjetoRequestDTO.class)))
        .thenReturn(preencherProjeto(1L, "Apollo Atualizado"));

    mockMvc.perform(put("/api/projetos/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Apollo Atualizado"));
  }

  @Test
  void deveDeletarProjeto() throws Exception {
    mockMvc.perform(delete("/api/projetos/1"))
        .andExpect(status().isNoContent());

    Mockito.verify(service).deletar(1L);
  }

}