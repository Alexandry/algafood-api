package com.comeon.algafood.api.controller;

import static com.comeon.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.comeon.algafood.infrastructure.repository.spec.RestauranteSpecs.contemNome;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;



import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import com.comeon.algafood.domain.model.Restaurante;
import com.comeon.algafood.domain.repository.RestauranteRepository;
import com.comeon.algafood.domain.service.CadastroRestauranteService;
import com.comeon.algafood.infrastructure.repository.spec.RestauranteComFreteGratisSpec;
import com.comeon.algafood.infrastructure.repository.spec.RestauranteContemNomeSpec;
import com.comeon.algafood.infrastructure.repository.spec.RestauranteSpecs;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService restauranteService;

	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();

	}

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> buscarPorId(@PathVariable Long id) {

		Optional<Restaurante> restaurante = restauranteRepository.findById(id);
		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> addNewCozinha(@RequestBody Restaurante restaurante) {
		try {
			restaurante = restauranteService.save(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> putRestaurante(@PathVariable long id, @RequestBody Restaurante c) {

		try {
			Optional<Restaurante>  restaurante = restauranteRepository.findById(id);

			if (restaurante.isPresent()) {

				BeanUtils.copyProperties(c, restaurante.get(), "id");

				Restaurante restauranteAtualizado = restauranteService.save(restaurante.get());

				return ResponseEntity.ok(restauranteAtualizado);
			}
			return ResponseEntity.notFound().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}

	}
	
	@GetMapping("/por-nome-e-frete")
	public List<Restaurante> restaurantesPorNomeEFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
		
		//return restauranteRepository.find(nome, taxaInicial, taxaFinal);
				
		return restauranteRepository.buscar(nome, taxaInicial, taxaFinal);
	}
	
	@GetMapping("/por-nome-e-frete-spec")
	public List<Restaurante> buscarPorNomeEFreteGratis(String nome){
		
	
		return restauranteRepository.findAll(
					 comFreteGratis().and(contemNome(nome)));
	}
	
	@GetMapping("/por-nome-e-frete-spec-impl")
	public List<Restaurante> findFreteGratis(String nome){
		
	
		return restauranteRepository.findAll(
					 comFreteGratis().and(contemNome(nome)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> remove(@PathVariable long id) {

		try {
			restauranteService.remove(id);
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {

			return ResponseEntity.badRequest().body(e.getMessage());

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long id,
			@RequestBody Map<String, Object> campos) {
		Restaurante restauranteAtual = restauranteRepository.findById(id).orElseGet(null);

		if (restauranteAtual == null) {
			return ResponseEntity.notFound().build();
		}

		merge(campos, restauranteAtual);

		return putRestaurante(id, restauranteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);

			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

//			System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);

			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}

}
