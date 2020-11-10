package com.comeon.algafood.infrastructure.repository;

import static com.comeon.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;import static com.comeon.algafood.infrastructure.repository.spec.RestauranteSpecs.contemNome;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.comeon.algafood.domain.model.Restaurante;
import com.comeon.algafood.domain.repository.RestauranteRepositoriesQueries;
import com.comeon.algafood.domain.repository.RestauranteRepository;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoriesQueries {

	@PersistenceContext
	private EntityManager em;
	
	/*
	@Lazy utilizado para nao ter referencia circular, quando uma classe depende dela mesma, nesse caso RestauranteRepository
	essa annotation tem como objetivo informar ao Spring que ele deve aguardar para instanciar (injetar) esse repositorio
	apenas quando ele for chamado.
	*/
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;
	
	//JPQL
	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		var jpql =  new StringBuilder();
		var parametros = new HashMap<String, Object>();
		
				jpql.append("from Restaurante where 0 = 0");
				if (StringUtils.hasLength(nome)) {
					jpql.append("and nome like :nome ");
					parametros.put("nome", "%" + nome + "%");					
				}
				
				if (taxaFreteInicial != null) {
					jpql.append("and taxaFrete >= :taxaInicial ");
					parametros.put("taxaInicial", taxaFreteInicial);
					
				}
				
				if (taxaFreteFinal != null ) {
					jpql.append("and taxaFrete >= :taxaFinal ");
					parametros.put("taxaFinal", taxaFreteFinal);
										
				}
				
		TypedQuery<Restaurante> query = em.createQuery(jpql.toString(), Restaurante.class);
		parametros.forEach((key, value) -> query.setParameter(key, value));
		
		    return query.getResultList();
	}


	//Buscar com Criteria
	@Override
	public List<Restaurante> buscar(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		
		//Pegando uma instancia do builder 
		CriteriaBuilder builder = em.getCriteriaBuilder();
		
		//Criando query a partir do criteriaBuilder do tipo Restaurante
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		
		//Atribuindo de onde sera feito o from
		Root<Restaurante> root = criteria.from(Restaurante.class);
		
		//A partir daqui segue as restricoes com a classe Predicate
		List<Predicate> predicates = new ArrayList<>(); 
				
		//Validacao de parametros para input no array de Predicates
		if (StringUtils.hasText(nome)) {
			predicates.add(builder.like(root.get("nome"), "%%" +nome+ "%"));
		}
		if (taxaFreteInicial != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));			
		}
		if (taxaFreteFinal != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
			
		}
		
		//clausulas do select montadas e prontas para execucao
		criteria.where(predicates.toArray(new Predicate[0]));
		
		//Realizando select
		TypedQuery<Restaurante> query = em.createQuery(criteria);
		
		return query.getResultList();
		
	}


	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
		// TODO Auto-generated method stub
		return restauranteRepository.findAll(comFreteGratis().and(contemNome(nome)));
	}

}
