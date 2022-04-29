package com.api.inodevs.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Fatura {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	private byte[] fatura;
	
	private String nome_fatura;
	private String tipo_fatura;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public byte[] getFatura() {
		return fatura;
	}
	public void setFatura(byte[] fatura) {
		this.fatura = fatura;
	}
	public String getNome_fatura() {
		return nome_fatura;
	}
	public void setNome_fatura(String nome_fatura) {
		this.nome_fatura = nome_fatura;
	}
	public String getTipo_fatura() {
		return tipo_fatura;
	}
	public void setTipo_fatura(String tipo_fatura) {
		this.tipo_fatura = tipo_fatura;
	}
	
}
