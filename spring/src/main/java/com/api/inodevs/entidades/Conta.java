package com.api.inodevs.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

//Criando entidade da conta:
@Entity
public class Conta{
	// Colunas da contrato:
    @Id // Referenciando o Id
	private Long codi;
	
    @Column(nullable = false)
	private Float consumo;
    
    @Column
	private Float desconto;
    
    @Column(nullable = false)
	private String data_de_criacao;
    
    @Column(nullable = false)
	private String data_de_lancamento;
    
    @Column(nullable = false)
	private String data_de_vencimento;
    
    @Column(nullable = false)
	private String dias;
    
    @Column
	private String dados_adicionais;
	
    @Column(nullable = false)
	private Float valor;
    
    @Column(nullable = false)
	private Float valor_total;
    
    @Column(nullable = false)
	private Integer base_calculo;
    
    @Column
	private String status;
	
    @Column
	private String mensagem;

	@ManyToOne
	@JoinColumn(name = "codigo_contrato")
	private Contrato contrato;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_fatura", referencedColumnName = "id")
	private Fatura fatura;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_notificacoes", referencedColumnName = "id")
	private Notificacoes notificacoes;
	
    // Getters e Setters:
	public Long getCodi() {
		return codi;
	}
	public void setCodi(Long codi) {
		this.codi = codi;
	}
	public Float getConsumo() {
		return consumo;
	}
	public void setConsumo(Float consumo) {
		this.consumo = consumo;
	}
	public Float getDesconto() {
		return desconto;
	}
	public void setDesconto(Float desconto) {
		this.desconto = desconto;
	}
	public String getData_de_criacao() {
		return data_de_criacao;
	}
	public void setData_de_criacao(String data_de_criacao) {
		this.data_de_criacao = data_de_criacao;
	}
	public String getData_de_lancamento() {
		return data_de_lancamento;
	}
	public void setData_de_lancamento(String data_de_lancamento) {
		this.data_de_lancamento = data_de_lancamento;
	}
	public String getData_de_vencimento() {
		return data_de_vencimento;
	}
	public void setData_de_vencimento(String data_de_vencimento) {
		this.data_de_vencimento = data_de_vencimento;
	}
	public String getDias() {
		return dias;
	}
	public void setDias(String dias) {
		this.dias = dias;
	}
	public String getDados_adicionais() {
		return dados_adicionais;
	}
	public void setDados_adicionais(String dados_adicionais) {
		this.dados_adicionais = dados_adicionais;
	}
	public Float getValor() {
		return valor;
	}
	public void setValor(Float valor) {
		this.valor = valor;
	}
	public Float getValor_total() {
		return valor_total;
	}
	public void setValor_total(Float valor_total) {
		this.valor_total = valor_total;
	}
	public Integer getBase_calculo() {
		return base_calculo;
	}
	public void setBase_calculo(Integer base_calculo) {
		this.base_calculo = base_calculo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Contrato getContrato() {
		return contrato;
	}
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	public Fatura getFatura() {
		return fatura;
	}
	public void setFatura(Fatura fatura) {
		this.fatura = fatura;
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
}