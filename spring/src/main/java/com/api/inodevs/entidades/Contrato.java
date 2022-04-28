package com.api.inodevs.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Contrato {
	
	@Id
	private Long codigo;
	
	private Long concessionaria;
	private Long unidade;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Long getConcessionaria() {
		return concessionaria;
	}
	public void setConcessionaria(Long concessionaria) {
		this.concessionaria = concessionaria;
	}
	public Long getUnidade() {
		return unidade;
	}
	public void setUnidade(Long unidade) {
		this.unidade = unidade;
	}
	
}
