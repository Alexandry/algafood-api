package com.comeon.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.comeon.algafood.domain.model.Restaurante;
import com.comeon.algafood.domain.repository.RestauranteRepositoriesQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoriesQueries{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		var jpql = "from Restaurante where nome like :nome"
				+ " and taxaFrete between :taxaIni and :taxaFim";
		return em.createQuery(jpql, Restaurante.class)
				.setParameter("nome", "%"+ nome+ "%")
				.setParameter("taxaIni", taxaFreteInicial)
				.setParameter("taxaIni", taxaFreteFinal)
				.getResultList();
	}
	
	

}
