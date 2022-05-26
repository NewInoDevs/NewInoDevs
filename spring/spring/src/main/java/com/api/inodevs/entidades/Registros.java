package com.api.inodevs.entidades;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Registros {

	@Id // Referenciando o Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Gerando id automaticamente (Auto Increment)
	private Long id;
	
	@Column
	private String atividade;
	
	@Column
	private LocalDateTime data_atividade;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAtividade() {
		return atividade;
	}

	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}

	public LocalDateTime getData_atividade() {
		return data_atividade;
	}

	public void setData_atividade(LocalDateTime data_atividade) {
		this.data_atividade = data_atividade;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
