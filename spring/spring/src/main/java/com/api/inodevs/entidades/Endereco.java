package com.api.inodevs.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

//Criando entidade do endereço:
@Entity
public class Endereco {
	// Colunas da  concessionária:
    @Id // Referenciando o Id
	public Long cep;
	
	public String estado;
	public String municipio;
	public String bairro;
	public String rua;
	public Integer numero;
	public String complemento;
	
	// Getters e Setters:
	public Long getCep() {
		return cep;
	}
	public void setCep(Long cep) {
		this.cep = cep;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String município) {
		this.municipio = município;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
}
