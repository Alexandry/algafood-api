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
import com.comeon.algafood.domain.model.Cidade;
import com.comeon.algafood.domain.model.Estado;
import com.comeon.algafood.domain.repository.CidadeRepository;
import com.comeon.algafood.domain.repository.EstadoRepository;
import com.comeon.algafood.domain.service.CadastroCidadeService;
import com.comeon.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/cidades")
public class CicadeController {

	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cidadeService;
	
	@GetMapping
	public List<Cidade> listarCidades(){
		
		return cidadeRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long Id) {
		Optional<Cidade> cidade = cidadeRepository.findById(Id);
		
		if (cidade.isPresent()) {
			return ResponseEntity.ok(cidade.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody Cidade cidade) {
		return cidadeService.save(cidade);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Cidade> atualizar(@PathVariable Long id,
			@RequestBody Cidade estado) {
		Optional<Cidade> cidadeAtual = cidadeRepository.findById(id);
		
		if (cidadeAtual.isPresent()) {
			BeanUtils.copyProperties(estado, cidadeAtual.get(), "id");
			
			Cidade cidadeAtualizada = cidadeService.save(cidadeAtual.get());
			return ResponseEntity.ok(cidadeAtualizada);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		try {
			cidadeService.remove(id);	
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(e.getMessage());
		}
	}
}
