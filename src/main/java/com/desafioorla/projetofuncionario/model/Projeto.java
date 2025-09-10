package com.desafioorla.projetofuncionario.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "projeto")
public class Projeto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 150)
  private String nome;

  @Column(name = "data_criacao", nullable = false)
  private LocalDate dataCriacao;

  @ManyToMany
  @JoinTable(
      name = "projeto_funcionario",
      joinColumns = @JoinColumn(name = "projeto_id"),
      inverseJoinColumns = @JoinColumn(name = "funcionario_id")
  )
  @Builder.Default
  private Set<Funcionario> funcionarios = new HashSet<>();

  @PrePersist
  public void prePersist() {
    if (this.dataCriacao == null) this.dataCriacao = LocalDate.now();
  }
}
