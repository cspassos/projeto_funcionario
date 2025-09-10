package com.desafioorla.projetofuncionario.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
  info = @Info(
    title = "API Projetos & Funcionários",
    version = "v1",
    description = "CRUD parcial: criar e ler Projetos e Funcionários, incluindo vinculações.",
    contact = @Contact(name = "Desafio Orla", email = "contato@exemplo.com")
  )
)
public class OpenApiConfig {

  @Bean
  public GroupedOpenApi projetoFuncionarioApi() {
    return GroupedOpenApi.builder()
      .group("projeto-funcionario")
      .packagesToScan("com.desafioorla.projetofuncionario.controllers")
      .build();
  }
}