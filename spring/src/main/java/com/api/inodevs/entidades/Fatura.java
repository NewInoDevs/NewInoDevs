package com.api.inodevs.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

//Criando entidade da fatura:
@Entity
public class Fatura {
	// Colunas da fatura:
    @Id // Referenciando o Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Gerando id automaticamente (Auto Increment)
	private Long id;
	
	@Lob // Referenciando como Lob (Tipo para arquivos - Imagens, pdfs etc.)
	private byte[] fatura;
	
	@Column
	private String nome_fatura;
	
	@Column
	private String tipo_fatura;
	
	@OneToOne(mappedBy = "fatura")
	private Conta conta;
	
	// Getters e Setters:
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
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
}