package com.api.inodevs.entidades;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

//Criando entidade da unidade:
@Entity
public class Unidade {
	// Colunas da unidade:
    @Id // Referenciando o Id
	private Long cnpj;
	
	private String nome;
	public String telefone;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cep_endereco", referencedColumnName = "cep")
	public Endereco endereco;
	
	private String status;
	 
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_notificacoes", referencedColumnName = "id")
    private Notificacoes notificacoes;
	
	private String mensagem;
	
	@OneToMany(mappedBy = "unidade", cascade = CascadeType.REMOVE)
	private Set<Contrato> contrato;
	
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
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Notificacoes getNotificacoes() {
		return notificacoes;
	}
	public void setNotificacoes(Notificacoes notificacoes) {
		this.notificacoes = notificacoes;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Set<Contrato> getContrato() {
		return contrato;
	}
	public void setContrato(Set<Contrato> contrato) {
		this.contrato = contrato;
	}
}