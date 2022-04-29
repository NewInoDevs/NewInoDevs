package com.api.inodevs.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.api.inodevs.entidades.Endereco;
import com.api.inodevs.entidades.Unidade;
import com.api.inodevs.repositorio.EnderecoRepositorio;
import com.api.inodevs.repositorio.UnidadeRepositorio;

@Controller
public class ControleUnidade {
	
	@Autowired
	private UnidadeRepositorio unidadeRepo;
	
	@Autowired
	private EnderecoRepositorio enderecoRepo;
	
	@GetMapping("/cadastroUnidade")
	public String cadastroUnidade(@ModelAttribute("endereco") Endereco endereco, @ModelAttribute("unidade") Unidade unidade){
		return "pages/forms/unidade";
	}
	
	@PostMapping("/salvarUnidade")
	public String salvarUnidade(@ModelAttribute("endereco") Endereco endereco, @ModelAttribute("unidade") Unidade unidade, RedirectAttributes redirect) {
		unidade.setEndereco(endereco.getCep());
        redirect.addFlashAttribute("successo", "Cadastrado com sucesso!");
		unidadeRepo.save(unidade);
		enderecoRepo.save(endereco);
		return "redirect:cadastroUnidade";
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
        return "pages/forms/edit/unidadeEdit";
    }
	
	@PostMapping("/salvarUnidadeEdit")
	public String salvarUnidadeEdit(@ModelAttribute("unidade") Unidade unidade, RedirectAttributes redirect, @ModelAttribute("endereco") Endereco endereco) {
		unidade.setEndereco(endereco.getCep());
		unidadeRepo.save(unidade);
		enderecoRepo.save(endereco);
		redirect.addFlashAttribute("sucesso", "Unidade salvo com sucesso!");
		return "redirect:/tabela";
	}
	
	@GetMapping("/excluirUnidade/{cnpj}/{endereco}")
	public String excluirUnidade(@PathVariable("cnpj") long cnpj, @PathVariable("endereco") long endereco) {
		Optional<Unidade> unidadeOpt = unidadeRepo.findById(cnpj);
		if (unidadeOpt.isEmpty()) {
			throw new IllegalArgumentException("Unidade inválido");
		}
		unidadeRepo.deleteById(cnpj);
        Optional<Endereco> enderecoOpt = enderecoRepo.findById(endereco);
        if (enderecoOpt.isEmpty()) {
            throw new IllegalArgumentException("Endereco inválido");
        }
		enderecoRepo.deleteById(endereco);
		return "redirect:/tabela";
	}
}