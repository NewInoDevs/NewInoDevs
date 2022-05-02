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

import com.api.inodevs.entidades.Contrato;
import com.api.inodevs.entidades.Unidade;
import com.api.inodevs.repositorio.ConcessionariaRepositorio;
import com.api.inodevs.repositorio.ContratoRepositorio;
import com.api.inodevs.repositorio.UnidadeRepositorio;

//Classe de controle que permite a navegação e funcionalidades no sistema:
@Controller
public class ControleContrato {
	
	// Adicionando repositório do contrato, concessionária e unidade para salvar e ler dados no banco:
	@Autowired
	private ContratoRepositorio contratoRepo;
	@Autowired
	private ConcessionariaRepositorio concessionariaRepo;
	@Autowired
	private UnidadeRepositorio unidadeRepo;
	
	// Entrar na página de cadastro de contrato com o modelo da entidade:
	@GetMapping("/cadastroContrato")
	public String cadastroContrato(@ModelAttribute("contrato") Contrato contrato, Model modelo){
		modelo.addAttribute("listaConcessionaria", concessionariaRepo.findAll());
		modelo.addAttribute("listaUnidade", unidadeRepo.findAll());
		return "pages/forms/contrato";
	}
	
	// Salvar uma contrato no banco ao clicar em cadastrar:
	@PostMapping("/salvarContrato")
	public String salvarConta(@ModelAttribute("contrato") Contrato contrato, RedirectAttributes redirect){
        redirect.addFlashAttribute("successo", "Cadastrado com sucesso!");
		contratoRepo.save(contrato);
		return "redirect:cadastroContrato";
	}
	
	// Abrir mais inforações do contrato clicando na tabela para permitir a edição de um cadastro:
	@GetMapping("/contrato/{codigo}")
    public String abrirContrato(@PathVariable("codigo") long codigo, Model modelo) {
		modelo.addAttribute("listaConcessionaria", concessionariaRepo.findAll()); // Adicionando todas as concessionárias cadastradas no select
		modelo.addAttribute("listaUnidade", unidadeRepo.findAll()); // Adicionando todas as unidades cadastradas no select
        Optional<Contrato> contratoOpt = contratoRepo.findById(codigo);
        if (contratoOpt.isEmpty()) {
            throw new IllegalArgumentException("Contrato inválida");
        }
        modelo.addAttribute("contrato", contratoOpt.get());
        return "pages/forms/edit/contratoEdit";
    }
	
	// Salvar o contrato editado no banco de dados ao clicar em editar:
	@PostMapping("/salvarContratoEdit")
	public String salvarContratoEdit(@ModelAttribute("contrato") Contrato contrato, RedirectAttributes redirect) {
		contrato.setStatus("Pendente");
		contratoRepo.save(contrato);
		redirect.addFlashAttribute("sucesso", "Contrato editado com sucesso!");
		return "redirect:/tabela";
	}
	
	// Excluir um contrato ao clicar em excluir na tabela:
	@GetMapping("/excluirContrato/{codigo}")
	public String excluirContrato(@PathVariable("codigo") long codigo) {
		Optional<Contrato> contratoOpt = contratoRepo.findById(codigo);
		if (contratoOpt.isEmpty()) {
			throw new IllegalArgumentException("Contrato inválido");
		}
		contratoRepo.deleteById(codigo);
		return "redirect:/tabela";
	}
	
	@PostMapping("/aprovarContrato")
	public String aprovarContrato(@ModelAttribute("contrato") Contrato contrato) {
		contrato.setStatus("Aprovado");
		contratoRepo.save(contrato);
		return "redirect:/tabela";
	}
	
	@PostMapping("/reprovarContrato")
	public String reprovarContrato(@ModelAttribute("contrato") Contrato contrato) {
		contrato.setStatus("Reprovado");
		contratoRepo.save(contrato);
		return "redirect:/tabela";
	}
	
}