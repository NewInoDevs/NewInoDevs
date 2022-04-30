package com.api.inodevs.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

//Criando entidade da cotrato:
@Entity
public class Contrato {
	// Colunas do contrato:
    @Id // Referenciando o Id
	private Long codigo;
	
	private Long concessionaria;
	private Long unidade;
	
	 // Getters e Setters:
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
