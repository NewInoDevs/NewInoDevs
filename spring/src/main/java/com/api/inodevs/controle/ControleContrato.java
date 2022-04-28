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
import com.api.inodevs.repositorio.ConcessionariaRepositorio;
import com.api.inodevs.repositorio.ContratoRepositorio;
import com.api.inodevs.repositorio.UnidadeRepositorio;

@Controller
public class ControleContrato {
	
	@Autowired
	private ConcessionariaRepositorio concessionariaRepo;
	
	@Autowired
	private UnidadeRepositorio unidadeRepo;

	@Autowired
	private ContratoRepositorio contratoRepo;
	
	@GetMapping("/cadastroContrato")
	public String cadastroContrato(@ModelAttribute("contrato") Contrato contrato, Model modelo){
		modelo.addAttribute("listaConcessionaria", concessionariaRepo.findAll());
		modelo.addAttribute("listaUnidade", unidadeRepo.findAll());
		return "pages/forms/contrato";
	}
	
	@PostMapping("/salvarContrato")
	public String salvarConta(@ModelAttribute("contrato") Contrato contrato, RedirectAttributes redirect){
        redirect.addFlashAttribute("successo", "Cadastrado com sucesso!");
		contratoRepo.save(contrato);
		return "redirect:cadastroContrato";
	}
	
	@GetMapping("/contrato/{codigo}")
    public String abrirContrato(@PathVariable("codigo") long codigo, Model modelo) {
        Optional<Contrato> contratoOpt = contratoRepo.findById(codigo);
        if (contratoOpt.isEmpty()) {
            throw new IllegalArgumentException("Contrato inválida");
        }
        modelo.addAttribute("contrato", contratoOpt.get());
        return "pages/forms/edit/contrato";
    }
	
}

