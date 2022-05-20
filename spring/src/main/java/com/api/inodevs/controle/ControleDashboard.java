package com.api.inodevs.controle;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.api.inodevs.entidades.Conta;
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
        
		// MÊS ANTERIOR:
		Date data = new Date();
		GregorianCalendar dataCal = new GregorianCalendar();
		dataCal.setTime(data);
		int mes = dataCal.get(Calendar.MONTH);
		if (mes == 0) {
			mes = 12;
		}
		int ano = dataCal.get(Calendar.YEAR);
		if (mes == 12) {
			ano = dataCal.get(Calendar.YEAR)-1;
		}
		
		modelo.addAttribute("ano", ano);
		switch (mes) {
			case 1:
				modelo.addAttribute("mes", "Janeiro");
				break;
			case 2:
				modelo.addAttribute("mes", "Fevereiro");
				break;
			case 3:
				modelo.addAttribute("mes", "Março");
				break;
			case 4:
				modelo.addAttribute("mes", "Abril");
				break;
			case 5:
				modelo.addAttribute("mes", "Maio");
				break;
			case 6:
				modelo.addAttribute("mes", "Junho");
				break;
			case 7:
				modelo.addAttribute("mes", "Julho");
				break;
			case 8:
				modelo.addAttribute("mes", "Agosto");
				break;
			case 9:
				modelo.addAttribute("mes", "Setembro");
				break;
			case 10:
				modelo.addAttribute("mes", "Outubro");
				break;
			case 11:
				modelo.addAttribute("mes", "Novembro");
				break;
			case 12:
				modelo.addAttribute("mes", "Dezembro");
				break;
		}
		
        List<Conta> contasAgua = contaRepo.contasContrato(contratoRepo.contratoAgua(cnpj));
        List<Conta> contasGas = contaRepo.contasContrato(contratoRepo.contratoGas(cnpj));
        List<Conta> contasEnergia = contaRepo.contasContrato(contratoRepo.contratoEnergia(cnpj));
        
        Conta contaAguaAtual = null;
        float contaAguaJan = 0;
        float contaAguaFev = 0;
        float contaAguaMar = 0;
        float contaAguaAbr = 0;
        float contaAguaMai = 0;
        float contaAguaJun = 0;
        float contaAguaJul = 0;
        float contaAguaAgo = 0;
        float contaAguaSet = 0;
        float contaAguaOut = 0;
        float contaAguaNov = 0;
        float contaAguaDez = 0;
        
        int contA = 0;
        float somaA = 0;
        for (Conta conta : contasAgua) {
        	String mesConta = conta.getData_de_lancamento();
        	String dataSplit[] = new String[3];
        	dataSplit = mesConta.split("-");
        	
        	if (Integer.parseInt(dataSplit[1]) == mes && Integer.parseInt(dataSplit[0]) == ano) {
        		contaAguaAtual = conta;
        	}
        	if (Integer.parseInt(dataSplit[1]) == 1 && Integer.parseInt(dataSplit[0]) == ano) {
        		contaAguaJan = conta.getConsumo();
        		somaA += conta.getConsumo();
        		contA++;
        	}
        	if (Integer.parseInt(dataSplit[1]) == 2 && Integer.parseInt(dataSplit[0]) == ano) {
        		contaAguaFev = conta.getConsumo();
        		somaA += conta.getConsumo();
        		contA++;
        	}
        	if (Integer.parseInt(dataSplit[1]) == 3 && Integer.parseInt(dataSplit[0]) == ano) {
        		contaAguaMar = conta.getConsumo();
        		somaA += conta.getConsumo();
        		contA++;
        	}
        	if (Integer.parseInt(dataSplit[1]) == 4 && Integer.parseInt(dataSplit[0]) == ano) {
        		contaAguaAbr = conta.getConsumo();
        		somaA += conta.getConsumo();
        		contA++;
        	}
        	if (Integer.parseInt(dataSplit[1]) == 5 && Integer.parseInt(dataSplit[0]) == ano) {
        		contaAguaMai = conta.getConsumo();
        		somaA += conta.getConsumo();
        		contA++;
        	}
        	if (Integer.parseInt(dataSplit[1]) == 6 && Integer.parseInt(dataSplit[0]) == ano) {
        		contaAguaJun = conta.getConsumo();
        		somaA += conta.getConsumo();
        		contA++;
        	}
        	if (Integer.parseInt(dataSplit[1]) == 7 && Integer.parseInt(dataSplit[0]) == ano) {
        		contaAguaJul = conta.getConsumo();
        		somaA += conta.getConsumo();
        		contA++;
        	}
        	if (Integer.parseInt(dataSplit[1]) == 8 && Integer.parseInt(dataSplit[0]) == ano) {
        		contaAguaAgo = conta.getConsumo();
        		somaA += conta.getConsumo();
        		contA++;
        	}
        	if (Integer.parseInt(dataSplit[1]) == 9 && Integer.parseInt(dataSplit[0]) == ano) {
        		contaAguaSet = conta.getConsumo();
        		somaA += conta.getConsumo();
        		contA++;
        	}
        	if (Integer.parseInt(dataSplit[1]) == 10 && Integer.parseInt(dataSplit[0]) == ano) {
        		contaAguaOut = conta.getConsumo();
        		somaA += conta.getConsumo();
        		contA++;
        	}
        	if (Integer.parseInt(dataSplit[1]) == 11 && Integer.parseInt(dataSplit[0]) == ano) {
        		contaAguaNov = conta.getConsumo();
        		somaA += conta.getConsumo();
        		contA++;
        	}
        	if (Integer.parseInt(dataSplit[1]) == 12 && Integer.parseInt(dataSplit[0]) == ano) {
        		contaAguaDez = conta.getConsumo();
        		somaA += conta.getConsumo();
        		contA++;
        	}
		}
      
        Conta contaGasAtual = null;
        for (Conta conta : contasGas) {
        	String mesConta = conta.getData_de_lancamento();
        	String dataSplit[] = new String[3];
        	dataSplit = mesConta.split("-");
        	
        	if (Integer.parseInt(dataSplit[1]) == mes && Integer.parseInt(dataSplit[0]) == ano) {
        		contaGasAtual = conta;
        	}
		}
        
        Conta contaEnergiaAtual = null;
        for (Conta conta : contasEnergia) {
        	String mesConta = conta.getData_de_lancamento();
        	String dataSplit[] = new String[3];
        	dataSplit = mesConta.split("-");
        	
        	if (Integer.parseInt(dataSplit[1]) == mes && Integer.parseInt(dataSplit[0]) == ano) {
        		contaEnergiaAtual = conta;
        	}
		}
        
        modelo.addAttribute("contaAguaAtual", contaAguaAtual);  
        modelo.addAttribute("contaGasAtual", contaGasAtual); 
        modelo.addAttribute("contaEnergiaAtual", contaEnergiaAtual); 
        
        modelo.addAttribute("contaAguaMedia", somaA/contA);
        
        modelo.addAttribute("contaAguaJan", contaAguaJan);  
        modelo.addAttribute("contaAguaFev", contaAguaFev);  
        modelo.addAttribute("contaAguaMar", contaAguaMar);  
        modelo.addAttribute("contaAguaAbr", contaAguaAbr);  
        modelo.addAttribute("contaAguaMai", contaAguaMai);  
        modelo.addAttribute("contaAguaJun", contaAguaJun);  
        modelo.addAttribute("contaAguaJul", contaAguaJul);  
        modelo.addAttribute("contaAguaAgo", contaAguaAgo);  
        modelo.addAttribute("contaAguaSet", contaAguaSet);  
        modelo.addAttribute("contaAguaOut", contaAguaOut);  
        modelo.addAttribute("contaAguaNov", contaAguaNov);  
        modelo.addAttribute("contaAguaDez", contaAguaDez); 
       
		return "pages/teste";
	}	
}