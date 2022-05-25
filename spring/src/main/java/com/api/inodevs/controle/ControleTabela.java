package com.api.inodevs.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.api.inodevs.entidades.Usuario;
import com.api.inodevs.repositorio.ConcessionariaRepositorio;
import com.api.inodevs.repositorio.ContaRepositorio;
import com.api.inodevs.repositorio.ContratoRepositorio;
import com.api.inodevs.repositorio.NotificacoesRepositorio;
import com.api.inodevs.repositorio.RegistrosRepositorio;
import com.api.inodevs.repositorio.UnidadeRepositorio;
import com.api.inodevs.repositorio.UsuarioRepositorio;

@Controller
public class ControleTabela {
	
	// Adicionando repositório da concessionária, conta, contrato e unidade para ler dados no banco:
	@Autowired
	private ConcessionariaRepositorio concessionariaRepo;
	@Autowired
	private ContaRepositorio contaRepo;
	@Autowired
	private ContratoRepositorio contratoRepo;
	@Autowired
	private UnidadeRepositorio unidadeRepo;
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	@Autowired
	private NotificacoesRepositorio notificacoesRepo;
	@Autowired
	private RegistrosRepositorio registrosRepo;

	// Entrar na página das tabelas com dados do banco de dados:
	@GetMapping("/tabela")
    public String tabela(Model modelo,@Param("palavraChave") String palavraChave, @AuthenticationPrincipal User user) {
		
		if (palavraChave != null) {
			modelo.addAttribute("listaConta", contaRepo.pesquisarConta(palavraChave));
			modelo.addAttribute("listaConcessionaria", concessionariaRepo.pesquisarConcessionaria(palavraChave));
			modelo.addAttribute("listaUnidade", unidadeRepo.pesquisarUnidade(palavraChave));
			modelo.addAttribute("listaContrato", contratoRepo.pesquisarContrato(palavraChave));
        } else {
        	modelo.addAttribute("listaConta", contaRepo.findAll());
            modelo.addAttribute("listaConcessionaria", concessionariaRepo.findAll());
            modelo.addAttribute("listaUnidade", unidadeRepo.findAll());
            modelo.addAttribute("listaContrato", contratoRepo.findAll());
        }
		
		modelo.addAttribute("palavraChave", palavraChave);
		
        modelo.addAttribute("quantidadeConta", notificacoesRepo.contar("Conta", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeConcessionaria", notificacoesRepo.contar("Concessionaria", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeUnidade", notificacoesRepo.contar("Unidade", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeContrato", notificacoesRepo.contar("Contrato", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeContaRep", notificacoesRepo.contar("Conta", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeConcessionariaRep", notificacoesRepo.contar("Concessionaria", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeUnidadeRep", notificacoesRepo.contar("Unidade", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeContratoRep", notificacoesRepo.contar("Contrato", "ROLE_DIGITADOR"));
            
        Optional<Usuario> usuarioOpt = usuarioRepo.findById(Long.parseLong(user.getUsername()));
        modelo.addAttribute("usuarioInfo", usuarioOpt.get());
        
        return "index";
    }
	@GetMapping("/log")
	public String log(Model modelo){
    	modelo.addAttribute("listaRegistros", registrosRepo.findAll());
		return "pages/log";
	}
}