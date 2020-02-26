package com.comeon.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.comeon.algafood.domain.model.Restaurante;

public interface RestauranteRepositoriesQueries {
	
	
	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

}
