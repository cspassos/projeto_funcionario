package com.desafioorla.projetofuncionario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.desafioorla.projetofuncionario.dto.FuncionarioRequestDTO;
import com.desafioorla.projetofuncionario.dto.ProjetoResumoDTO;
import com.desafioorla.projetofuncionario.model.Funcionario;
import com.desafioorla.projetofuncionario.model.Projeto;
import com.desafioorla.projetofuncionario.repository.FuncionarioRepository;
import com.desafioorla.projetofuncionario.repository.ProjetoRepository;

@Service
public class FuncionarioService {
	
	@Autowired
	private FuncionarioRepository repository;
	
	@Autowired
	private ProjetoRepository projetoRepository;

	  @Transactional
	  public Funcionario create(FuncionarioRequestDTO dto) {
	    if (repository.existsByCpf(dto.cpf())) {
	      throw new IllegalArgumentException("CPF já cadastrado");
	    }
	    if (repository.existsByEmail(dto.email())) {
	      throw new IllegalArgumentException("E-mail já cadastrado");
	    }
	    var f = Funcionario.builder()
						        .nome(dto.nome())
						        .cpf(dto.cpf())
						        .email(dto.email())
						        .salario(dto.salario())
						   .build();
	    return repository.save(f);
	  }

	  @Transactional(readOnly = true)
	  public List<Funcionario> buscarTodosFuncionarios() {
	    return repository.findAll();
	  }

	  @Transactional(readOnly = true)
	  public Funcionario buscarPeloId(Long id) {
	    return repository.findById(id)
	        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado"));
	  }

	  @Transactional(readOnly = true)
	  public List<Projeto> projetosDoFuncionario(Long funcionarioId) {
	    if (!repository.existsById(funcionarioId)) {
	      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado");
	    }
	    return projetoRepository.findByFuncionarios_Id(funcionarioId);
	  }
	  
	  @Transactional(readOnly = true)
	  public List<ProjetoResumoDTO> buscarApenasProjetosFuncionario(Long funcionarioId) {
	    if (!repository.existsById(funcionarioId))
	      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado");
	    return projetoRepository.buscarApenasProjetosFuncionario(funcionarioId);
	  }
	  
	  @Transactional
	  public Funcionario atualizar(Long id, FuncionarioRequestDTO dto) {

	    if (repository.existsByCpfAndIdNot(dto.cpf(), id)) {
	      throw new IllegalArgumentException("CPF já cadastrado");
	    }
	    if (repository.existsByEmailAndIdNot(dto.email(), id)) {
	      throw new IllegalArgumentException("E-mail já cadastrado");
	    }

	    
	    return repository.save(setarFuncionario(id, dto));
	  }

	private Funcionario setarFuncionario(Long id, FuncionarioRequestDTO dto) {
		return Funcionario.builder()
	            .id(buscarPeloId(id).getId())
	            .nome(dto.nome())
	            .cpf(dto.cpf())
	            .email(dto.email())
	            .salario(dto.salario())
	            .build();
	}
	
	@Transactional
	public void deletar(Long id) {
	  repository.delete(buscarPeloId(id));
	}
}
