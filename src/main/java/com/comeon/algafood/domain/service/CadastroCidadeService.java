package com.comeon.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.comeon.algafood.domain.exception.EntidadeEmUsoException;
import com.comeon.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.comeon.algafood.domain.model.Cidade;
import com.comeon.algafood.domain.model.Cozinha;
import com.comeon.algafood.domain.repository.CidadeRepository;
import com.comeon.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	public Cidade save(Cidade cozinha) {

		return cidadeRepository.save(cozinha);
	}

	public void remove(long id) {
		try {
			cidadeRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe cozinha com id %d ", id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Cozinha com o codigo %d não pode ser removida", id));
		}
	}

}
