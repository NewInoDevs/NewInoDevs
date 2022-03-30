package com.api.inodevs.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Concessionaria {
	
    @Id
    private long codigo;
    
    public String nome;
    
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
} 