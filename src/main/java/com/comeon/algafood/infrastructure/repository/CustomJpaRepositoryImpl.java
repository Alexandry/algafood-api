package com.comeon.algafood.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.comeon.algafood.domain.repository.CustomJpaRepository;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {

	private EntityManager em;

	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
		super(entityInformation, em);

		this.em = em;
	}

	@Override
	public Optional<T> buscarPrimeiro() {
		 var jpql  = "from "+ getDomainClass().getName();
		 
		 T entity = em.createQuery(jpql, getDomainClass())
				 .setMaxResults(1)
				 .getSingleResult();
		return Optional.ofNullable(entity);
	}
}