package com.api.inodevs.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//Criando entidade da cotrato:
@Entity
public class Contrato {
	// Colunas do contrato:
    @Id // Referenciando o Id
	private Long codigo;
	
	@ManyToOne
	@JoinColumn(name = "codigo_concessionaria")
	private Concessionaria concessionaria;
	
	@ManyToOne
	@JoinColumn(name = "cnpj_unidade")
	private Unidade unidade;
	
	 // Getters e Setters:
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Concessionaria getConcessionaria() {
		return concessionaria;
	}
	public void setConcessionaria(Concessionaria concessionaria) {
		this.concessionaria = concessionaria;
	}
	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
}
