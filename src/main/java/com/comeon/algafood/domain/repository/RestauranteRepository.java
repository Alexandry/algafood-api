package com.comeon.algafood.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import com.comeon.algafood.domain.model.Restaurante;


public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteRepositoriesQueries,
JpaSpecificationExecutor<Restaurante>{
	
		
	

}
