package com.api.inodevs.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

// Criando entidade da concessionária:
@Entity
public class Concessionaria {
	// Colunas da  concessionária:
    @Id // Referenciando o Id
    private Long codigo;

    public String nome;
    
    // Getters e Setters:
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
} 