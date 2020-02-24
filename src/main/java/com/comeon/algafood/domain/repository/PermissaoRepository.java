package com.comeon.algafood.domain.repository;

import java.util.List;

import com.comeon.algafood.domain.model.Permissao;

public interface PermissaoRepository {
	
	List<Permissao> listar();	
	
	public Permissao save(Permissao permissao);

	Permissao findById(long l);
	
	void remove(Permissao permissao);
		
	

}
