package com.desafioorla.projetofuncionario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.desafioorla.projetofuncionario.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	  boolean existsByCpf(String cpf);
	  boolean existsByEmail(String email);

	// Evita fazer varias consultas
	  @EntityGraph(attributePaths = "projetos")
	  List<Funcionario> findByProjetos_Id(Long projetoId);
	  
	  boolean existsByCpfAndIdNot(String cpf, Long id);
	  boolean existsByEmailAndIdNot(String email, Long id);
}