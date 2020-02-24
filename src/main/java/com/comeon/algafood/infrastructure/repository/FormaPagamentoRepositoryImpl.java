package com.comeon.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.comeon.algafood.domain.model.Cozinha;
import com.comeon.algafood.domain.model.FormaPagamento;
import com.comeon.algafood.domain.repository.CozinhaRepository;
import com.comeon.algafood.domain.repository.FormaPagamentoRepository;


@Component
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository{

	
	
	@PersistenceContext
	private EntityManager em;
	
	public List<FormaPagamento> listar() {
		return em.createQuery("from FormaPagamento", FormaPagamento.class).getResultList();
	}
	
	@Transactional	
	public FormaPagamento save(FormaPagamento frmPagamento) {
		return em.merge(frmPagamento);
	}
	
	
	public FormaPagamento findById(long l) {
		return em.find(FormaPagamento.class, l);
	}
	
	@Transactional	
	public void remove(FormaPagamento frmPagamento) {
		frmPagamento = findById(frmPagamento.getId());
		em.remove(frmPagamento);
		
	}

}
