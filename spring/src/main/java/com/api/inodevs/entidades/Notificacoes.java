package com.api.inodevs.entidades;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;



@Entity
public class Notificacoes {
// Colunas da fatura:
@Id // Referenciando o Id
@GeneratedValue(strategy = GenerationType.IDENTITY) // Gerando id automaticamente (Auto Increment)
private Long id;

private String destinatario;
private String tipo;

@OneToOne(mappedBy = "notificacoes")
private Conta conta;

public Notificacoes() {
}

public Notificacoes(String destinatario, String tipo) {
	this.destinatario = destinatario;
	this.tipo = tipo;
}

public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}


public String getDestinatario() {
	return destinatario;
}


public void setDestinatario(String destinatario) {
	this.destinatario = destinatario;
}


public String getTipo() {
	return tipo;
}


public void setTipo(String tipo) {
this.tipo = tipo;
}

public Conta getConta() {
	return conta;
}

public void setConta(Conta conta) {
	this.conta = conta;
}



}