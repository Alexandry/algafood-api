package com.comeon.algafood.infrastructure.repository.spec;

import java.math.BigDecimal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.comeon.algafood.domain.model.Restaurante;

public class RestauranteContemNomeSpec implements Specification<Restaurante> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	public RestauranteContemNomeSpec(String nome) {
		
		this.nome = nome;
		
	}

	public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		// TODO Auto-generated method stub
		return builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}

}
