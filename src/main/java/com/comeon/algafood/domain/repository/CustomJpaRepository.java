package com.comeon.algafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/*
 * @NoRepositoryBean
  Indica ao spring que ele não deve instanciar uma implementacao para esta interface
*/
@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {
	
	Optional<T> buscarPrimeiro();

}
