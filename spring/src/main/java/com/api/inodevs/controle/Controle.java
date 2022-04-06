package com.api.inodevs.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/tabela")
	public String tabela(Model modelo) {
		modelo.addAttribute("listaConcessionaria", concessionariaRepo.findAll());
		modelo.addAttribute("listaUnidade", unidadeRepo.findAll());
		modelo.addAttribute("listaEndereco", enderecoRepo.findAll());
		modelo.addAttribute("listaConta", contaRepo.findAll());
		return "tabela";
	}
	@GetMapping("/concessionaria/{codigo}")
    public String abrirConcessionaria(@PathVariable("codigo") long codigo, Model modelo) {
        Optional<Concessionaria> concessionariaOpt = concessionariaRepo.findById(codigo);
        if (concessionariaOpt.isEmpty()) {
            throw new IllegalArgumentException("Concessionária inválida");
        }
        modelo.addAttribute("concessionaria", concessionariaOpt.get());
        return "abrir-concessionaria";
    }
	@GetMapping("/unidade/{cnpj}/{endereco}")
    public String abrirUnidade(@PathVariable("cnpj") long cnpj, @PathVariable("endereco") long endereco, Model modelo) {
        Optional<Unidade> unidadeOpt = unidadeRepo.findById(cnpj);
        if (unidadeOpt.isEmpty()) {
            throw new IllegalArgumentException("Unidade inválida");
        }
        modelo.addAttribute("unidade", unidadeOpt.get());
        Optional<Endereco> enderecoOpt = enderecoRepo.findById(endereco);
        if (enderecoOpt.isEmpty()) {
            throw new IllegalArgumentException("Endereco inválido");
        }
        modelo.addAttribute("endereco", enderecoOpt.get());
        return "abrir-unidade";
    }

}
