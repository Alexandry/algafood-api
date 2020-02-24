package com.comeon.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.comeon.algafood.domain.exception.EntidadeEmUsoException;
import com.comeon.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.comeon.algafood.domain.model.Cozinha;
import com.comeon.algafood.domain.model.Restaurante;
import com.comeon.algafood.domain.repository.CozinhaRepository;
import com.comeon.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private static final String messageCozinha = "Não existe cozinha com o id %d cadastrado";
	private static final String messageRestaurante = "Não existe restaurante com o id %d cadastrado";

	public Restaurante save(Restaurante restaurante) {
		Long id = restaurante.getCozinha().getId();
		
		Cozinha cozinha = cozinhaRepository.findById(id).orElseThrow
				(() -> new EntidadeNaoEncontradaException(String.format(messageCozinha, id)));
		
		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}

	public void remove(long id) {
		try {
			Long restauranteId = id;
			
			Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow(
					() -> new EntidadeNaoEncontradaException(String.format(messageRestaurante, id)));
			
			restauranteRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe cozinha com id %d ", id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Cozinha com o codigo %d não pode ser removida", id));
		}
	}

	

}
