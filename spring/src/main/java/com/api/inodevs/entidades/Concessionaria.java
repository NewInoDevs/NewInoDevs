package com.api.inodevs.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

// Criando entidade da concessionária:
@Entity
public class Concessionaria {
	// Colunas da  concessionária:
    @Id // Referenciando o Id
    private Long codigo;

    public String nome;
    
	private String tipo_conta;
	
	private String status;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_notificacoes", referencedColumnName = "id")
    private Notificacoes notificacoes;
    
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
	
}