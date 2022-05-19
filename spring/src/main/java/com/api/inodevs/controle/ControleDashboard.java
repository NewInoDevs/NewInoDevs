package com.api.inodevs.controle;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.api.inodevs.entidades.Unidade;
import com.api.inodevs.repositorio.ContaRepositorio;
import com.api.inodevs.repositorio.ContratoRepositorio;
import com.api.inodevs.repositorio.UnidadeRepositorio;

//Classe de controle que permite a navegação e funcionalidades no sistema:
@Controller
public class ControleDashboard {
	
	@Autowired
	private ContratoRepositorio contratoRepo;
	@Autowired
	private UnidadeRepositorio unidadeRepo;
	@Autowired
	private ContaRepositorio contaRepo;
	
	// Entrar no dashboard:
	@GetMapping("/")
	public String dash(Model modelo) {
        modelo.addAttribute("listaUnidade", unidadeRepo.findAll());
        modelo.addAttribute("listaContrato", contratoRepo.findAll());
		return "pages/dashboardtable";
	}
	
	@GetMapping("/dashboardmain")
	public String abrirDashboard() {
		return "pages/dashboard";
	}
	
	@GetMapping("/dashboard/{cnpj}")
	public String teste(@PathVariable("cnpj") long cnpj, Model modelo) {
		
        Optional<Unidade> unidadeOpt = unidadeRepo.findById(cnpj);
        if (unidadeOpt.isEmpty()) {
            throw new IllegalArgumentException("Unidade inválida");
        }
        modelo.addAttribute("unidade", unidadeOpt.get());
        
        modelo.addAttribute("listaContasAgua", contaRepo.contasContrato(contratoRepo.contratoAgua(cnpj)));  
        modelo.addAttribute("listaContasGas", contaRepo.contasContrato(contratoRepo.contratoGas(cnpj))); 
        modelo.addAttribute("listaContasEnergia", contaRepo.contasContrato(contratoRepo.contratoEnergia(cnpj))); 
        
		// MÊS ANTERIOR:
		Date data = new Date();
		GregorianCalendar dataCal = new GregorianCalendar();
		dataCal.setTime(data);
		int mes = dataCal.get(Calendar.MONTH);
		modelo.addAttribute("mes", mes);
	
		return "pages/teste";
	}	
}