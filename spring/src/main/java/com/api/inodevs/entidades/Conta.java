package com.api.inodevs.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Conta{
	
	@Id
	private Long codi;
	
	public String nome;
	private String consumo;
	private String desconto;
	private String data_de_criacao;
	private String data_de_lancamento;
	private String data_de_vencimento;
	private String dias;
	private String dados_adicionais;
	private Float valor;
	private Float valor_total;
	private Integer base_calculo;
	private String tipo_conta;
	private Long contrato;

	private String concessionaria;
	private String unidade;
	private Long fatura;
	
	public Long getCodi() {
		return codi;
	}
	public void setCodi(Long codi) {
		this.codi = codi;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getConsumo() {
		return consumo;
	}
	public void setConsumo(String consumo) {
		this.consumo = consumo;
	}
	public String getDesconto() {
		return desconto;
	}
	public void setDesconto(String desconto) {
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
	public String getTipo_conta() {
		return tipo_conta;
	}
	public void setTipo_conta(String tipo_conta) {
		this.tipo_conta = tipo_conta;
	}
	public Long getContrato() {
		return contrato;
	}
	public void setContrato(Long contrato) {
		this.contrato = contrato;
	}
	public String getConcessionaria() {
		return concessionaria;
	}
	public void setConcessionaria(String concessionaria) {
		this.concessionaria = concessionaria;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public Long getFatura() {
		return fatura;
	}
	public void setFatura(Long fatura) {
		this.fatura = fatura;
	}
}