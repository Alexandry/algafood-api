package com.comeon.algafood.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.comeon.algafood.domain.model.Cozinha;

@Component
public class CadastroCozinha {
	
	@PersistenceContext
	private EntityManager em;

	
	public List<Cozinha> listar(){
		return em.createQuery("from Cozinha", Cozinha.class).getResultList();
	}
	
	
	
	@Transactional
	public Cozinha save(Cozinha cozinha) {
		
		return em.merge(cozinha);
	}



	public Cozinha findById(long l) {
		return em.find(Cozinha.class, l);
	}
	
	@Transactional
	public void remove(Cozinha cozinha) {
		cozinha = findById(cozinha.getId());
		em.remove(cozinha);
		
	}
}
