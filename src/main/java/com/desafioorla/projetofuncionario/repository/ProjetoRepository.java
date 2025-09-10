package com.desafioorla.projetofuncionario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.desafioorla.projetofuncionario.dto.ProjetoResumoDTO;
import com.desafioorla.projetofuncionario.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

	  @EntityGraph(attributePaths = "funcionarios")
	  List<Projeto> findAll();
	  
	  @EntityGraph(attributePaths = "funcionarios")
	  Optional<Projeto> findById(Long id);
	  
	  // Evita fazer varias consultas
	  @EntityGraph(attributePaths = "funcionarios")
	  List<Projeto> findByFuncionarios_Id(Long funcionarioId);
	  
	  @Query("""
		         select new com.desafioorla.projetofuncionario.dto.ProjetoResumoDTO(
		           p.id, p.nome, p.dataCriacao
		         )
		         from Projeto p
		         join p.funcionarios f
		         where f.id = :funcionarioId
		         order by p.id
		         """)
		  List<ProjetoResumoDTO> buscarApenasProjetosFuncionario(Long funcionarioId);

}