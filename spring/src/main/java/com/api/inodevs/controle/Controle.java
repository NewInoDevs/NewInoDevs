package com.api.inodevs.controle;

import java.io.IOException;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	
	// IMPORTANDO REPOSITORIOS = Concessionaria, Unidade, Endereco e Conta
	
	@Autowired
	private ConcessionariaRepositorio concessionariaRepo;
	
	@Autowired
	private UnidadeRepositorio unidadeRepo;
	
	@Autowired
	private EnderecoRepositorio enderecoRepo;
	
	@Autowired
	private ContaRepositorio contaRepo;
	
	// TELA DE CADASTROS = Concessionaria, Unidade e Conta

	@GetMapping("/cadastroConcessionaria")
	public String cadastroConcessionaria(@ModelAttribute("concessionaria") Concessionaria concessionaria){
		return "concessionaria";
	}
	
	@GetMapping("/cadastroUnidade")
	public String cadastroUnidade(@ModelAttribute("endereco") Endereco endereco, @ModelAttribute("unidade") Unidade unidade){
		return "unidade";
	}
	
	@GetMapping("/cadastroConta")
	public String cadastroConta(@ModelAttribute("conta") Conta conta){
		return "conta";
	}
	
	// CADASTRANDO NO BANCO DE DADOS = Concessionaria, Unidade (+ Endereco) e Conta (+ Upload do Arquivo)
	
	@PostMapping("/salvarConcessionaria")
	public String salvarConcessionaria(@ModelAttribute("concessionaria") Concessionaria concessionaria) {
		concessionariaRepo.save(concessionaria);	
		return "redirect:cadastroConcessionaria";
	}
	
	@PostMapping("/salvarUnidade")
	public String salvarUnidade(@ModelAttribute("endereco") Endereco endereco, @ModelAttribute("unidade") Unidade unidade) {
		unidade.setEndereco(endereco.getCep());
		unidadeRepo.save(unidade);
		enderecoRepo.save(endereco);
		return "redirect:cadastroUnidade";
	}

	@PostMapping("/salvarConta")
	public String salvarConta(@ModelAttribute("conta") Conta conta, @RequestParam("faturaPdf") MultipartFile file) {
		String nome = file.getOriginalFilename();
		try {
			conta.setNome_fatura(nome);
			conta.setTipo_fatura(file.getContentType());
			conta.setFatura(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		contaRepo.save(conta);
		return "redirect:cadastroConta";
	}
	
	// TELA DAS TABELAS COM DADOS DO BANCO
		
	@GetMapping("/tabela")
	public String tabela(Model modelo) {
		modelo.addAttribute("listaConcessionaria", concessionariaRepo.findAll());
		modelo.addAttribute("listaUnidade", unidadeRepo.findAll());
		modelo.addAttribute("listaEndereco", enderecoRepo.findAll());
		modelo.addAttribute("listaConta", contaRepo.findAll());
		return "tabela";
	}
	
	// ABRINDO TODAS INFORMAÇOES DA TABELA = Concessionaria, Unidade (+ Endereco) e Conta
	
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
	
	@GetMapping("/conta/{codi}")
    public String abrirConta(@PathVariable("codi") long codi, Model modelo) {
        Optional<Conta> contaOpt = contaRepo.findById(codi);
        if (contaOpt.isEmpty()) {
            throw new IllegalArgumentException("Conta inválida");
        }
        modelo.addAttribute("conta", contaOpt.get());
        return "abrir-conta";
    }
	
	// DOWNLOAD DO ARQUIVO DE FATURA

	@GetMapping("/download/{codi}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable long codi){
		Conta conta = contaRepo.findById(codi).get();
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(conta.getTipo_fatura()))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + conta.getNome_fatura() + "\"")
				.body(new ByteArrayResource(conta.getFatura()));
	}
}