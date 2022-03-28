package com.api.inodevs.entidades;

public class Conta extends Contrato{
	private int codigo;
	public String nome;
	private String consumo;
	private String desconto;
	private String dataDeCriação;
	private String dataDeLançamento;
	private String dataDeVencimento;
	private String dias;
	private String dadosAdicionais;
	private float valor;
	private float valorTotal;
	private int baseDeCalculo;
	private String tipoDeConta;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
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
	public String getDataDeCriação() {
		return dataDeCriação;
	}
	public void setDataDeCriação(String dataDeCriação) {
		this.dataDeCriação = dataDeCriação;
	}
	public String getDataDeLançamento() {
		return dataDeLançamento;
	}
	public void setDataDeLançamento(String dataDeLançamento) {
		this.dataDeLançamento = dataDeLançamento;
	}
	public String getDataDeVencimento() {
		return dataDeVencimento;
	}
	public void setDataDeVencimento(String dataDeVencimento) {
		this.dataDeVencimento = dataDeVencimento;
	}
	public String getDias() {
		return dias;
	}
	public void setDias(String dias) {
		this.dias = dias;
	}
	public String getDadosAdicionais() {
		return dadosAdicionais;
	}
	public void setDadosAdicionais(String dadosAdicionais) {
		this.dadosAdicionais = dadosAdicionais;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public float getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}
	public int getBaseDeCalculo() {
		return baseDeCalculo;
	}
	public void setBaseDeCalculo(int baseDeCalculo) {
		this.baseDeCalculo = baseDeCalculo;
	}
	public String getTipoDeConta() {
		return tipoDeConta;
	}
	public void setTipoDeConta(String tipoDeConta) {
		this.tipoDeConta = tipoDeConta;
	}  
}