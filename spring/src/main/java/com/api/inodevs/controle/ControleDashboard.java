package com.api.inodevs.controle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.api.inodevs.entidades.Conta;
import com.api.inodevs.entidades.Unidade;
import com.api.inodevs.entidades.Usuario;
import com.api.inodevs.repositorio.ContaRepositorio;
import com.api.inodevs.repositorio.ContratoRepositorio;
import com.api.inodevs.repositorio.NotificacoesRepositorio;
import com.api.inodevs.repositorio.UnidadeRepositorio;
import com.api.inodevs.repositorio.UsuarioRepositorio;

//Classe de controle que permite a navegação e funcionalidades no sistema:
@Controller
public class ControleDashboard {
	
	@Autowired
	private ContratoRepositorio contratoRepo;
	@Autowired
	private UnidadeRepositorio unidadeRepo;
	@Autowired
	private ContaRepositorio contaRepo;
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	@Autowired
	private NotificacoesRepositorio notificacoesRepo;
	
	// Entrar no dashboard:
	@GetMapping("/")
	public String tabelaDashboards(Model modelo, @Param("palavraChave") String palavraChave, @AuthenticationPrincipal User user) {
		
        Optional<Usuario> usuarioOpt = usuarioRepo.findById(Long.parseLong(user.getUsername()));
		Usuario usuario = usuarioOpt.get();
        
        if(usuario.getSecao().equals("ROLE_DIGITADOR")) {
        	return "redirect:/tabela";
        } 
        
		if (palavraChave != null) {
			modelo.addAttribute("listaUnidade", unidadeRepo.pesquisarRelatorio(palavraChave));
		} else {
	        modelo.addAttribute("listaUnidade", unidadeRepo.findAll());    
		}
		modelo.addAttribute("listaContrato", contratoRepo.findAll());
		
        modelo.addAttribute("usuarioInfo", usuario);
        
        modelo.addAttribute("quantidadeConta", notificacoesRepo.contar("Conta", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeConcessionaria", notificacoesRepo.contar("Concessionaria", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeUnidade", notificacoesRepo.contar("Unidade", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeContrato", notificacoesRepo.contar("Contrato", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeContaRep", notificacoesRepo.contar("Conta", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeConcessionariaRep", notificacoesRepo.contar("Concessionaria", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeUnidadeRep", notificacoesRepo.contar("Unidade", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeContratoRep", notificacoesRepo.contar("Contrato", "ROLE_DIGITADOR"));
		return "pages/dashboardtable";
	}
	
	// Mes por extenso
	public String mesExtenso(int mes) {
		switch (mes) {
			case 1:
				return "Janeiro";
			case 2:
				return "Fevereiro";
			case 3:
				return "Março";
			case 4:
				return "Abril";
			case 5:
				return "Maio";
			case 6:
				return "Junho";
			case 7:
				return "Julho";
			case 8:
				return "Agosto";
			case 9:
				return "Setembro";
			case 10:
				return "Outubro";
			case 11:
				return "Novembro";
			case 12:
				return "Dezembro";
		}
		return null;
	}
	
	// Mes por extenso abreviado
	public String mesExtensoAbr(int mes) {
		switch (mes) {
			case 1:
				return "Jan";
			case 2:
				return "Fev";
			case 3:
				return "Mar";
			case 4:
				return "Abr";
			case 5:
				return "Mai";
			case 6:
				return "Jun";
			case 7:
				return "Jul";
			case 8:
				return "Ago";
			case 9:
				return "Set";
			case 10:
				return "Out";
			case 11:
				return "Nov";
			case 12:
				return "Dez";
		}
		return null;
	}
	
	@GetMapping("/dashboard/{cnpj}")
	public String dashboard(@PathVariable("cnpj") long cnpj, Model modelo, @AuthenticationPrincipal User user) {
        
		// Pegando unidade pelo cnpj
        Optional<Unidade> unidadeOpt = unidadeRepo.findById(cnpj);
        if (unidadeOpt.isEmpty()) {
            throw new IllegalArgumentException("Unidade inválida");
        }
        modelo.addAttribute("unidade", unidadeOpt.get());
        
		// Mês e Ano Atual
		Date data = new Date();
		GregorianCalendar dataCal = new GregorianCalendar();
		dataCal.setTime(data);
		int mes = dataCal.get(Calendar.MONTH) + 1;	
		int ano = dataCal.get(Calendar.YEAR);
		modelo.addAttribute("ano", ano);
		modelo.addAttribute("mes", mesExtenso(mes));
		
		// Adicionando em uma lista todas as contas de cada contrato (tipos)
		List<Conta> contasAgua = new ArrayList<>();
		List<Conta> contasEnergia = new ArrayList<>();
		List<Conta> contasGas = new ArrayList<>();
		if (contratoRepo.contratoAgua(cnpj) != null) {
			contasAgua = contaRepo.contasContrato(contratoRepo.contratoAgua(cnpj).getCodigo());
		}
        if (contratoRepo.contratoGas(cnpj) != null) {
        	contasGas = contaRepo.contasContrato(contratoRepo.contratoGas(cnpj).getCodigo());
        } 
        if (contratoRepo.contratoEnergia(cnpj) != null) {
        	contasEnergia = contaRepo.contasContrato(contratoRepo.contratoEnergia(cnpj).getCodigo());
        }
        
        // Verificando mês e ano para serem adicionados ao gráfico de água
        Conta contaAguaAtual = null;        
        int contA = 0;
        float somaA = 0;
        Float[] contaAguaMes = new Float[12];
        for (Conta conta : contasAgua) {
        	String mesConta = conta.getData_de_lancamento();
        	String dataSplit[] = new String[3];
        	dataSplit = mesConta.split("-");
        	
        	if (Integer.parseInt(dataSplit[1]) == mes && Integer.parseInt(dataSplit[0]) == ano) {
        		contaAguaAtual = conta;
        	}
        	
    		int mesVar = mes;
    		int anoVar = ano;
        	
        	for (int i = 0; i < 12; i++) {
           		if (mesVar <= 0) {
        			mesVar += 12;
        			anoVar -= 1;
        		}
            	if (Integer.parseInt(dataSplit[1]) == mesVar && Integer.parseInt(dataSplit[0]) == anoVar) {
            		contaAguaMes[i] = conta.getConsumo();
            		somaA += conta.getConsumo();
            		contA++;
            	}
            	mesVar -= 1;
			}
		}
      
        Conta contaGasAtual = null;        
        int contG = 0;
        float somaG = 0;
        Float[] contaGasMes = new Float[12];
        for (Conta conta : contasGas) {
        	String mesConta = conta.getData_de_lancamento();
        	String dataSplit[] = new String[3];
        	dataSplit = mesConta.split("-");
        	
        	if (Integer.parseInt(dataSplit[1]) == mes && Integer.parseInt(dataSplit[0]) == ano) {
        		contaGasAtual = conta;
        	}
        	
    		int mesVar = mes;
    		int anoVar = ano;
        	
        	for (int i = 0; i < 12; i++) {
           		if (mesVar <= 0) {
        			mesVar += 12;
        			anoVar -= 1;
        		}
            	if (Integer.parseInt(dataSplit[1]) == mesVar && Integer.parseInt(dataSplit[0]) == anoVar) {
            		contaGasMes[i] = conta.getConsumo();
            		somaG += conta.getConsumo();
            		contG++;
            	}
            	mesVar -= 1;
			}
		}
        
        Conta contaEnergiaAtual = null;        
        int contE = 0;
        float somaE = 0;
        Float[] contaEnergiaMes = new Float[12];
        for (Conta conta : contasEnergia) {
        	String mesConta = conta.getData_de_lancamento();
        	String dataSplit[] = new String[3];
        	dataSplit = mesConta.split("-");
        	
        	if (Integer.parseInt(dataSplit[1]) == mes && Integer.parseInt(dataSplit[0]) == ano) {
        		contaEnergiaAtual = conta;
        	}
        	
    		int mesVar = mes;
    		int anoVar = ano;
        	
        	for (int i = 0; i < 12; i++) {
           		if (mesVar <= 0) {
        			mesVar += 12;
        			anoVar -= 1;
        		}
            	if (Integer.parseInt(dataSplit[1]) == mesVar && Integer.parseInt(dataSplit[0]) == anoVar) {
            		contaEnergiaMes[i] = conta.getConsumo();
            		somaE += conta.getConsumo();
            		contE++;
            	}
            	mesVar -= 1;
			}
		}
        
        int mesRel = mes;
        String[] mesesGrafico = new String[12];
        for (int i = 0; i < 12; i++) {
        	if (mesRel <= 0) {
        		mesRel += 12;
        	}
			mesesGrafico[i] = mesExtensoAbr(mesRel);
			mesRel -= 1;
		}
        
        modelo.addAttribute("mes1", mesesGrafico[11]);  
        modelo.addAttribute("mes2", mesesGrafico[10]);  
        modelo.addAttribute("mes3", mesesGrafico[9]);  
        modelo.addAttribute("mes4", mesesGrafico[8]);  
        modelo.addAttribute("mes5", mesesGrafico[7]);  
        modelo.addAttribute("mes6", mesesGrafico[6]);  
        modelo.addAttribute("mes7", mesesGrafico[5]);  
        modelo.addAttribute("mes8", mesesGrafico[4]);  
        modelo.addAttribute("mes9", mesesGrafico[3]);  
        modelo.addAttribute("mes10", mesesGrafico[2]);  
        modelo.addAttribute("mes11", mesesGrafico[1]);  
        modelo.addAttribute("mes12", mesesGrafico[0]);    
        
        modelo.addAttribute("unidade", unidadeOpt.get());
        
        modelo.addAttribute("contratoAgua", contratoRepo.contratoAgua(cnpj));  
        modelo.addAttribute("contratoEnergia", contratoRepo.contratoEnergia(cnpj)); 
        modelo.addAttribute("contratoGas", contratoRepo.contratoGas(cnpj)); 
       
        modelo.addAttribute("contaAguaAtual", contaAguaAtual);  
        modelo.addAttribute("contaGasAtual", contaGasAtual); 
        modelo.addAttribute("contaEnergiaAtual", contaEnergiaAtual); 
        
        modelo.addAttribute("contaAguaMedia", somaA/contA);
        
        modelo.addAttribute("contaAguaMes1", contaAguaMes[11]);  
        modelo.addAttribute("contaAguaMes2", contaAguaMes[10]);  
        modelo.addAttribute("contaAguaMes3", contaAguaMes[9]);  
        modelo.addAttribute("contaAguaMes4", contaAguaMes[8]);  
        modelo.addAttribute("contaAguaMes5", contaAguaMes[7]);  
        modelo.addAttribute("contaAguaMes6", contaAguaMes[6]);  
        modelo.addAttribute("contaAguaMes7", contaAguaMes[5]);  
        modelo.addAttribute("contaAguaMes8", contaAguaMes[4]);  
        modelo.addAttribute("contaAguaMes9", contaAguaMes[3]);  
        modelo.addAttribute("contaAguaMes10", contaAguaMes[2]);  
        modelo.addAttribute("contaAguaMes11", contaAguaMes[1]);  
        modelo.addAttribute("contaAguaMes12", contaAguaMes[0]);    
        
		modelo.addAttribute("contaGasMedia", somaG/contG);
		
        modelo.addAttribute("contaGasMes1", contaGasMes[11]);  
        modelo.addAttribute("contaGasMes2", contaGasMes[10]);  
        modelo.addAttribute("contaGasMes3", contaGasMes[9]);  
        modelo.addAttribute("contaGasMes4", contaGasMes[8]);  
        modelo.addAttribute("contaGasMes5", contaGasMes[7]);  
        modelo.addAttribute("contaGasMes6", contaGasMes[6]);  
        modelo.addAttribute("contaGasMes7", contaGasMes[5]);  
        modelo.addAttribute("contaGasMes8", contaGasMes[4]);  
        modelo.addAttribute("contaGasMes9", contaGasMes[3]);  
        modelo.addAttribute("contaGasMes10", contaGasMes[2]);  
        modelo.addAttribute("contaGasMes11", contaGasMes[1]);  
        modelo.addAttribute("contaGasMes12", contaGasMes[0]); 
        
		modelo.addAttribute("contaEnergiaMedia", somaE/contE);
        
        modelo.addAttribute("contaEnergiaMes1", contaEnergiaMes[11]);  
        modelo.addAttribute("contaEnergiaMes2", contaEnergiaMes[10]);  
        modelo.addAttribute("contaEnergiaMes3", contaEnergiaMes[9]);  
        modelo.addAttribute("contaEnergiaMes4", contaEnergiaMes[8]);  
        modelo.addAttribute("contaEnergiaMes5", contaEnergiaMes[7]);  
        modelo.addAttribute("contaEnergiaMes6", contaEnergiaMes[6]);  
        modelo.addAttribute("contaEnergiaMes7", contaEnergiaMes[5]);  
        modelo.addAttribute("contaEnergiaMes8", contaEnergiaMes[4]);  
        modelo.addAttribute("contaEnergiaMes9", contaEnergiaMes[3]);  
        modelo.addAttribute("contaEnergiaMes10", contaEnergiaMes[2]);  
        modelo.addAttribute("contaEnergiaMes11", contaEnergiaMes[1]);  
        modelo.addAttribute("contaEnergiaMes12", contaEnergiaMes[0]); 
        
        Optional<Usuario> usuarioOpt = usuarioRepo.findById(Long.parseLong(user.getUsername()));
        modelo.addAttribute("usuarioInfo", usuarioOpt.get());
        
        modelo.addAttribute("quantidadeConta", notificacoesRepo.contar("Conta", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeConcessionaria", notificacoesRepo.contar("Concessionaria", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeUnidade", notificacoesRepo.contar("Unidade", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeContrato", notificacoesRepo.contar("Contrato", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeContaRep", notificacoesRepo.contar("Conta", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeConcessionariaRep", notificacoesRepo.contar("Concessionaria", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeUnidadeRep", notificacoesRepo.contar("Unidade", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeContratoRep", notificacoesRepo.contar("Contrato", "ROLE_DIGITADOR"));
               
		return "pages/dashboard";
	}	
}