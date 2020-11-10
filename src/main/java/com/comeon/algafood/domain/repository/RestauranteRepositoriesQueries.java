package com.comeon.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.comeon.algafood.domain.model.Restaurante;

public interface RestauranteRepositoriesQueries {
	
	
	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	List<Restaurante> buscar(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	List<Restaurante> findComFreteGratis(String nome);

}
