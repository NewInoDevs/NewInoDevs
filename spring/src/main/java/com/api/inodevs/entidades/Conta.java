package com.api.inodevs.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Conta{
	
	@Id
	private long codi;
	
	public String nome;
	private String consumo;
	private String desconto;
	private String data_de_criacao;
	private String data_de_lancamento;
	private String data_de_vencimento;
	private String dias;
	private String dados_adicionais;
	private float valor;
	private float valor_total;
	private int base_calculo;
	private String tipo_conta;
	private long contrato;
	
	private String nome_fatura;
	private String tipo_fatura;
	
	@Lob
	private byte[] fatura;
	
	public long getCodi() {
		return codi;
	}
	public void setCodi(long codi) {
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
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public float getValor_total() {
		return valor_total;
	}
	public void setValor_total(float valor_total) {
		this.valor_total = valor_total;
	}
	public int getBase_calculo() {
		return base_calculo;
	}
	public void setBase_calculo(int base_calculo) {
		this.base_calculo = base_calculo;
	}
	public String getTipo_conta() {
		return tipo_conta;
	}
	public void setTipo_conta(String tipo_conta) {
		this.tipo_conta = tipo_conta;
	}
	public long getContrato() {
		return contrato;
	}
	public void setContrato(long contrato) {
		this.contrato = contrato;
	}
	public String getNome_fatura() {
		return nome_fatura;
	}
	public void setNome_fatura(String nomeFatura) {
		this.nome_fatura = nomeFatura;
	}
	public String getTipo_fatura() {
		return tipo_fatura;
	}
	public void setTipo_fatura(String tipo_fatura) {
		this.tipo_fatura = tipo_fatura;
	}
	public byte[] getFatura() {
		return fatura;
	}
	public void setFatura(byte[] fatura) {
		this.fatura = fatura;
	}
}