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

//Classe de controle que permite a navegação e funcionalidades no sistema:
@Controller
public class ControleUnidade {
	
	// Adicionando repositório da unidade e endereço para salvar e ler dados no banco:
	@Autowired
	private UnidadeRepositorio unidadeRepo;
	@Autowired
	private EnderecoRepositorio enderecoRepo;
	
	// Entrar na página de cadastro de unidade com o modelo da entidade com o modelo da entidade:
	@GetMapping("/cadastroUnidade")
	public String cadastroUnidade(@ModelAttribute("endereco") Endereco endereco, @ModelAttribute("unidade") Unidade unidade){
		return "pages/forms/unidade";
	}
	
	// Salvar uma unidade e endereço no banco ao clicar em cadastrar:
	@PostMapping("/salvarUnidade")
	public String salvarUnidade(@ModelAttribute("endereco") Endereco endereco, @ModelAttribute("unidade") Unidade unidade, RedirectAttributes redirect) {
		unidade.setEndereco(endereco.getCep()); // Adicionando no endereço da unidade o cep (ligando as duas entidades)
        redirect.addFlashAttribute("successo", "Cadastrado com sucesso!");
		unidadeRepo.save(unidade);
		enderecoRepo.save(endereco);
		return "redirect:cadastroUnidade";
	}
	
	// Abrir mais inforações da unidade com o seu endereço clicando na tabela para permitir a edição de um cadastro:
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
	
	// Salvar a unidade e endereço editados no banco de dados ao clicar em editar:
	@PostMapping("/salvarUnidadeEdit")
	public String salvarUnidadeEdit(@ModelAttribute("unidade") Unidade unidade, RedirectAttributes redirect, @ModelAttribute("endereco") Endereco endereco) {
		unidade.setEndereco(endereco.getCep());
		unidadeRepo.save(unidade);
		enderecoRepo.save(endereco);
		redirect.addFlashAttribute("sucesso", "Unidade salvo com sucesso!");
		return "redirect:/tabela";
	}
	
	// Excluir uma unidade (junto com o seu endereço) ao clicar em excluir na tabela:
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