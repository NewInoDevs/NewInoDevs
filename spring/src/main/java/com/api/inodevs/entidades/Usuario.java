package com.api.inodevs.entidades;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

//Criando entidade da usuário:
@Entity
public class Usuario{
	// Colunas da unidade:
    @Id // Referenciando o Id
	private Long username;

    @Column(nullable = false)
	private String nome;
    
    @Column(nullable = false)
	private String sobrenome;
    
    @Column(nullable = false)
	private String senha;
    
    @Column(nullable = false)
	private String secao;
    
    @Column(nullable = false)
	private Integer ativo;
    
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
	private Set<Registros> registros;
	
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
	

