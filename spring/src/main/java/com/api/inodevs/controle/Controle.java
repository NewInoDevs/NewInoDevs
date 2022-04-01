package com.api.inodevs.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.api.inodevs.entidades.Concessionaria;
import com.api.inodevs.entidades.Conta;
import com.api.inodevs.entidades.Endereco;
import com.api.inodevs.entidades.Unidade;
import com.api.inodevs.repositorio.ConcessionariaRepositorio;
import com.api.inodevs.repositorio.ContaRepositorio;
import com.api.inodevs.repositorio.EnderecoRepositorio;
import com.api.inodevs.repositorio.UnidadeRepositorio;

@Controller
public class Controle {
	
	@Autowired
	private ConcessionariaRepositorio concessionariaRepo;

	@GetMapping("/cadastroConcessionaria")
	public String cadastroConcessionaria(@ModelAttribute("concessionaria") Concessionaria concessionaria){
		return "concessionaria";
	}
	
	@PostMapping("/salvarConcessionaria")
	public String salvarConcessionaria(@ModelAttribute("concessionaria") Concessionaria concessionaria) {
		concessionariaRepo.save(concessionaria);	
		return "redirect:cadastroConcessionaria";
	}
	
	@Autowired
	private EnderecoRepositorio enderecoRepo;
	
	@Autowired
	private UnidadeRepositorio unidadeRepo;
	
	@GetMapping("/cadastroUnidade")
	public String cadastroUnidade(@ModelAttribute("endereco") Endereco endereco, @ModelAttribute("unidade") Unidade unidade){
		return "unidade";
	}
	
	@PostMapping("/salvarUnidade")
	public String salvarUnidade(@ModelAttribute("endereco") Endereco endereco, @ModelAttribute("unidade") Unidade unidade) {
		unidade.setEndereco(endereco.getCep());
		unidadeRepo.save(unidade);
		enderecoRepo.save(endereco);
		return "redirect:cadastroUnidade";
	}
	
	@Autowired
	private ContaRepositorio contaRepo;

	@GetMapping("/cadastroConta")
	public String cadastroConta(@ModelAttribute("conta") Conta conta){
		return "conta";
	}
	
	@PostMapping("/salvarConta")
	public String salvarConta(@ModelAttribute("conta") Conta conta) {
		contaRepo.save(conta);	
		return "redirect:cadastroConta";
	}
}
