package com.api.inodevs.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

//Criando entidade da usuário:
@Entity
public class Usuario{
	// Colunas da unidade:
    @Id // Referenciando o Id
	private Long username;

	private String nome;
	private String sobrenome;
	private String senha;
	private String secao;
	private Integer ativo;
	
	// Getters e Setters:
	public Long getUsername() {
		return username;
	}
	public void setUsername(Long username) {
		this.username = username;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getSecao() {
		return secao;
	}
	public void setSecao(String secao) {
		this.secao = secao;
	}
	public Integer getAtivo() {
		return ativo;
	}
	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}
	
}
	

