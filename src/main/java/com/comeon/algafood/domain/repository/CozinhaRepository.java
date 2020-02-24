package com.comeon.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.comeon.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long>{
	
	/*List<Cozinha> listar();	
	
	public Cozinha save(Cozinha cozinha);

	Cozinha findById(long l);
	
	void remove(long id);*/
		
	

}
