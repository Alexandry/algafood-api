package com.comeon.algafood.domain.repository;

import java.util.List;

import com.comeon.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {
	
	List<FormaPagamento> listar();	
	
	public FormaPagamento save(FormaPagamento frmPagamento);

	FormaPagamento findById(long l);
	
	void remove(FormaPagamento frmPagamento);
		
	

}
