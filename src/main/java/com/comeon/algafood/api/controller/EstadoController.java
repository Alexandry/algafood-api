package com.comeon.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.comeon.algafood.domain.exception.EntidadeEmUsoException;
import com.comeon.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.comeon.algafood.domain.model.Estado;
import com.comeon.algafood.domain.repository.EstadoRepository;
import com.comeon.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService estadoService;
	
	@GetMapping
	public List<Estado> listarEstados(){
		
		return estadoRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Estado> buscar(@PathVariable Long Id) {
		Optional<Estado> estado = estadoRepository.findById(Id);
		
		if (estado.isPresent()) {
			return ResponseEntity.ok(estado.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody Estado estado) {
		return estadoService.save(estado);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Estado> atualizar(@PathVariable Long id,
			@RequestBody Estado estado) {
		Optional<Estado> estadoAtual = estadoRepository.findById(id);
		
		if (estadoAtual.isPresent()) {
			BeanUtils.copyProperties(estado, estadoAtual, "id");
			
			Estado estadoAtualizado = estadoService.save(estadoAtual.get());
			return ResponseEntity.ok(estadoAtualizado);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		try {
			estadoService.remove(id);	
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(e.getMessage());
		}
	}
}
