package com.desafioorla.projetofuncionario.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import com.desafioorla.projetofuncionario.dto.ProjetoRequestDTO;
import com.desafioorla.projetofuncionario.model.Funcionario;
import com.desafioorla.projetofuncionario.model.Projeto;
import com.desafioorla.projetofuncionario.repository.FuncionarioRepository;
import com.desafioorla.projetofuncionario.repository.ProjetoRepository;

@Service
public class ProjetoService {

	@Autowired
	private ProjetoRepository projetoRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	
	  @Transactional
	  public Projeto create(ProjetoRequestDTO dto) {
	    var projeto = Projeto.builder()
	        .nome(dto.nome())
	        .build();

	    if (!CollectionUtils.isEmpty(dto.funcionariosIds())) {
	      var func = new HashSet<Funcionario>();
	      for (Long id : dto.funcionariosIds()) {
	        var f = funcionarioRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Funcionário id " + id + " não encontrado"));
	        func.add(f);
	      }
	      projeto.setFuncionarios(func);
	    }
	    return projetoRepository.save(projeto);
	  }
	  
	  @Transactional(readOnly = true)
	  public List<Projeto> buscarTodosProjetos() {
	    return projetoRepository.findAll();
	  }

	  @Transactional(readOnly = true)
	  public Projeto buscarPeloId(Long id) {
	    return projetoRepository.findById(id)
	        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado"));
	  }

	  @Transactional(readOnly = true)
	  public List<Funcionario> funcionariosDoProjeto(Long projetoId) {
	    if (!projetoRepository.existsById(projetoId)) {
	      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado");
	    }
	    return funcionarioRepository.findByProjetos_Id(projetoId);
	  }
	  
	  @Transactional
	  public Projeto atualizar(Long id, ProjetoRequestDTO dto) {
	    var projeto = buscarPeloId(id);

	    projeto.setNome(dto.nome());

	    if (dto.funcionariosIds() != null) {
	      var func = new java.util.HashSet<Funcionario>();
	      for (Long fid : dto.funcionariosIds()) {
	        var f = funcionarioRepository.findById(fid)
	            .orElseThrow(() -> new IllegalArgumentException("Funcionário id " + fid + " não encontrado"));
	        func.add(f);
	      }
	      projeto.setFuncionarios(func);
	    }

	    return projetoRepository.save(projeto);
	  }
	  
	  @Transactional
	  public void deletar(Long id) {
	    projetoRepository.delete(buscarPeloId(id));
	  }
	  
}
