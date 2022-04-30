package com.api.inodevs.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

//Criando entidade da unidade:
@Entity
public class Unidade {
	// Colunas da unidade:
    @Id // Referenciando o Id
	private Long cnpj;
	
	private String nome;
	public String telefone;
	public Long endereco;
	 
	// Getters e Setters:
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getCnpj() {
		return cnpj;
	}
	public void setCnpj(Long cnpj) {
		this.cnpj = cnpj;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Long getEndereco() {
		return endereco;
	}
	public void setEndereco(Long endereco) {
		this.endereco = endereco;
	}
}