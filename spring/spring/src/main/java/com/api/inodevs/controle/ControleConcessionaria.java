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

import com.api.inodevs.entidades.Concessionaria;
import com.api.inodevs.repositorio.ConcessionariaRepositorio;

// Classe de controle que permite a navegação e funcionalidades no sistema:
@Controller
public class ControleConcessionaria {
	
	// Adicionando repositório da concessionária para salvar e ler dados no banco:
	@Autowired
	private ConcessionariaRepositorio concessionariaRepo;
	
	// Entrar na página de cadastro de concessionária com o modelo da entidade:
	@GetMapping("/cadastroConcessionaria")
	public String cadastroConcessionaria(@ModelAttribute("concessionaria") Concessionaria concessionaria){
		concessionaria.setTipo_conta("energia");
		return "pages/forms/concessionaria";
	}
	
	// Salvar uma concessionária no banco ao clicar em cadastrar:
	@PostMapping("/salvarConcessionaria")
    public String salvarConcessionaria(@ModelAttribute("concessionaria") Concessionaria concessionaria, RedirectAttributes redirect) {
        concessionariaRepo.save(concessionaria);
        redirect.addFlashAttribute("successo", "Cadastrado com sucesso!");
        return "redirect:cadastroConcessionaria";
    }
	
	// Abrir mais inforações da concessionária clicando na tabela para permitir a edição de um cadastro:
	@GetMapping("/concessionaria/{codigo}")
    public String abrirConcessionaria(@PathVariable("codigo") long codigo, Model modelo) {
        Optional<Concessionaria> concessionariaOpt = concessionariaRepo.findById(codigo);
        if (concessionariaOpt.isEmpty()) {
            throw new IllegalArgumentException("Concessionária inválida");
        }
        modelo.addAttribute("concessionaria", concessionariaOpt.get());
        return "pages/forms/edit/concessionariaEdit";
    }
	
	// Salvar a concessionária editada no banco de dados ao clicar em editar:
	@PostMapping("/salvarConcessionariaEdit")
    public String salvarConcessionariaEdit(@ModelAttribute("concessionaria") Concessionaria concessionaria, RedirectAttributes redirect) {
        concessionariaRepo.save(concessionaria);
        redirect.addFlashAttribute("successo", "Editado com sucesso!");
        return "redirect:tabela";
    }
	
	// Excluir uma concessionária ao clicar em excluir na tabela:
	@GetMapping("/excluirConcessionaria/{codigo}")
	public String excluirConcessionaria(@PathVariable("codigo") long codigo) {
		Optional<Concessionaria> usuarioOpt = concessionariaRepo.findById(codigo);
		if (usuarioOpt.isEmpty()) {
			throw new IllegalArgumentException("Concessionaria inválido");
		}
		concessionariaRepo.deleteById(codigo);
		return "redirect:/tabela";
	}
	
}