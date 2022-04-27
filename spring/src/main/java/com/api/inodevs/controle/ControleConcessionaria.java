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

@Controller
public class ControleConcessionaria {
	
	@Autowired
	private ConcessionariaRepositorio concessionariaRepo;
	
	@GetMapping("/cadastroConcessionaria")
	public String cadastroConcessionaria(@ModelAttribute("concessionaria") Concessionaria concessionaria){
		return "pages/forms/concessionaria";
	}
	
	@PostMapping("/salvarConcessionaria")
    public String salvarConcessionaria(@ModelAttribute("concessionaria") Concessionaria concessionaria, RedirectAttributes redirect) {
        concessionariaRepo.save(concessionaria);
        redirect.addFlashAttribute("successo", "Cadastrado com sucesso!");
        return "redirect:cadastroConcessionaria";
    }
	
	@GetMapping("/concessionaria/{codigo}")
    public String abrirConcessionaria(@PathVariable("codigo") long codigo, Model modelo) {
        Optional<Concessionaria> concessionariaOpt = concessionariaRepo.findById(codigo);
        if (concessionariaOpt.isEmpty()) {
            throw new IllegalArgumentException("Concessionária inválida");
        }
        modelo.addAttribute("concessionaria", concessionariaOpt.get());
        return "pages/forms/edit/concessionariaEdit";
    }
	
}
