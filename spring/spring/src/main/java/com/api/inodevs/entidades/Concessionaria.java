package com.api.inodevs.entidades;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

// Criando entidade da concessionária:
@Entity
public class Concessionaria {
	// Colunas da  concessionária:
    @Id // Referenciando o Id
    private Long codigo;

    @Column(nullable = false)
    public String nome;
    
    @Column(nullable = false)
	private String tipo_conta;
	
    @Column
	private String status;
    
	@Column
	private String mensagem;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_notificacoes", referencedColumnName = "id")
    private Notificacoes notificacoes;
	
	@OneToMany(mappedBy = "concessionaria", cascade = CascadeType.REMOVE)
	private Set<Contrato> contrato;
	
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
	public String getTipo_conta() {
		return tipo_conta;
	}
	public void setTipo_conta(String tipo_conta) {
		this.tipo_conta = tipo_conta;
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
	public Set<Contrato> getContrato() {
		return contrato;
	}
	public void setContrato(Set<Contrato> contrato) {
		this.contrato = contrato;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}