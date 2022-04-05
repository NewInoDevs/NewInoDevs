package com.api.inodevs.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Unidade {
	
	@Id
	private long cnpj;
	
	private String nome;
	public String telefone;
	public long endereco;
	  
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public long getCnpj() {
		return cnpj;
	}
	public void setCnpj(long cnpj) {
		this.cnpj = cnpj;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public long getEndereco() {
		return endereco;
	}
	public void setEndereco(long endereco) {
		this.endereco = endereco;
	}
}