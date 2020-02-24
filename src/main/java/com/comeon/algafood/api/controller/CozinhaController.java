package com.comeon.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.comeon.algafood.domain.exception.EntidadeEmUsoException;
import com.comeon.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.comeon.algafood.domain.model.Cozinha;
import com.comeon.algafood.domain.repository.CozinhaRepository;
import com.comeon.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cozinhaService;

	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();

	}

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscarPorId(@PathVariable Long id) {

		Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
		if (cozinha.isPresent()) {
			return ResponseEntity.ok(cozinha.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha addNewCozinha(@RequestBody Cozinha cozinha) {

		return cozinhaService.save(cozinha);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> changeCozinha(@PathVariable long id, @RequestBody Cozinha c) {

		Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
		if (cozinha.isPresent()) {

			BeanUtils.copyProperties(c, cozinha.get(), "id");

			Cozinha cozinhaAtualizada = cozinhaService.save(cozinha.get());

			return ResponseEntity.ok(cozinhaAtualizada);
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Cozinha> remove(@PathVariable long id){
		
		
		try {			
				cozinhaService.remove(id);
				return ResponseEntity.noContent().build();
			
		}catch (EntidadeNaoEncontradaException e) {
			
		return ResponseEntity.notFound().build();
		
		}catch(EntidadeEmUsoException e){ 
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}

}

}
