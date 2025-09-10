package com.desafioorla.projetofuncionario.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "funcionario")
public class Funcionario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 150)
  private String nome;

  @Column(nullable = false, unique = true, length = 11)
  private String cpf;

  @Column(nullable = false, unique = true, length = 150)
  private String email;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal salario;

  @ManyToMany(mappedBy = "funcionarios")
  @Builder.Default
  private Set<Projeto> projetos = new HashSet<>();
}