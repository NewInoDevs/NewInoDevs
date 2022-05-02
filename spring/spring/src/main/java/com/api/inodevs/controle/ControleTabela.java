package com.api.inodevs.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.api.inodevs.repositorio.ConcessionariaRepositorio;
import com.api.inodevs.repositorio.ContaRepositorio;
import com.api.inodevs.repositorio.ContratoRepositorio;
import com.api.inodevs.repositorio.EnderecoRepositorio;
import com.api.inodevs.repositorio.UnidadeRepositorio;

@Controller
public class ControleTabela {
	
	// Adicionando repositório da concessionária, conta, contrato e unidade para ler dados no banco:
	@Autowired
	private ConcessionariaRepositorio concessionariaRepo;
	@Autowired
	private ContaRepositorio contaRepo;
	@Autowired
	private ContratoRepositorio contratoRepo;
	@Autowired
	private UnidadeRepositorio unidadeRepo;
	@Autowired
	private EnderecoRepositorio enderecoRepo;

	// Entrar na página das tabelas com dados do banco de dados:
	@GetMapping("/tabela")
	public String tabela(Model modelo) {
		modelo.addAttribute("listaConcessionaria", concessionariaRepo.findAll());
		modelo.addAttribute("listaUnidade", unidadeRepo.findAll());
		modelo.addAttribute("listaEndereco", enderecoRepo.findAll());
		modelo.addAttribute("listaConta", contaRepo.findAll());
		modelo.addAttribute("listaContrato", contratoRepo.findAll());
		return "pages/tables";
	}

}