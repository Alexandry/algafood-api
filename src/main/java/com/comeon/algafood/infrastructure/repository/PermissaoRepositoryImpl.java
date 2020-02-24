package com.comeon.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.comeon.algafood.domain.model.Cozinha;
import com.comeon.algafood.domain.model.FormaPagamento;
import com.comeon.algafood.domain.model.Permissao;
import com.comeon.algafood.domain.repository.CozinhaRepository;
import com.comeon.algafood.domain.repository.FormaPagamentoRepository;
import com.comeon.algafood.domain.repository.PermissaoRepository;


@Component
public class PermissaoRepositoryImpl implements PermissaoRepository{

	
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Permissao> listar() {
		return em.createQuery("from Permissao", Permissao.class).getResultList();
	}
	
	@Transactional	
	public Permissao save(Permissao permissao) {
		return em.merge(permissao);
	}
	
	
	public Permissao findById(long l) {
		return em.find(Permissao.class, l);
	}
	
	@Transactional	
	public void remove(Permissao permissao) {
		permissao = findById(permissao.getId());
		em.remove(permissao);
		
	}

}
