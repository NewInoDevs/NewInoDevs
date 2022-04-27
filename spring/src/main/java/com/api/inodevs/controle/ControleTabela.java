package com.api.inodevs.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.api.inodevs.repositorio.ConcessionariaRepositorio;
import com.api.inodevs.repositorio.ContaRepositorio;
import com.api.inodevs.repositorio.EnderecoRepositorio;
import com.api.inodevs.repositorio.UnidadeRepositorio;

@Controller
public class ControleTabela {
	
	@Autowired
	private ConcessionariaRepositorio concessionariaRepo;
	
	@Autowired
	private UnidadeRepositorio unidadeRepo;
	
	@Autowired
	private EnderecoRepositorio enderecoRepo;
	
	@Autowired
	private ContaRepositorio contaRepo;

	@GetMapping("/tabela")
	public String tabela(Model modelo) {
		modelo.addAttribute("listaConcessionaria", concessionariaRepo.findAll());
		modelo.addAttribute("listaUnidade", unidadeRepo.findAll());
		modelo.addAttribute("listaEndereco", enderecoRepo.findAll());
		modelo.addAttribute("listaConta", contaRepo.findAll());
		return "pages/tables";
	}

}