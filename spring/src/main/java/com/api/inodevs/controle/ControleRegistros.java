package com.api.inodevs.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.api.inodevs.entidades.Registros;
import com.api.inodevs.entidades.Usuario;
import com.api.inodevs.repositorio.NotificacoesRepositorio;
import com.api.inodevs.repositorio.RegistrosRepositorio;
import com.api.inodevs.repositorio.UsuarioRepositorio;

@Controller
public class ControleRegistros {
	
	@Autowired
	private RegistrosRepositorio registrosRepo;
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	@Autowired
	private NotificacoesRepositorio notificacoesRepo;

	@GetMapping("/log")
	public String log(Model modelo, @Param("palavraChave") String palavraChave, @AuthenticationPrincipal User user){
    	if (palavraChave != null) {
    		modelo.addAttribute("listaRegistros", registrosRepo.pesquisarRegistro(palavraChave));
    	} else {
    		modelo.addAttribute("listaRegistros", registrosRepo.todosRegistros());
    	}
    	modelo.addAttribute("palavraChave", palavraChave);
    	
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
        
		return "pages/log";
	}
	
	@GetMapping("/excluirLog/{id}")
	public String excluirLog(Model modelo, @AuthenticationPrincipal User user, @PathVariable("id") long id){
		 Optional<Registros> registrosOpt = registrosRepo.findById(id);
        if (registrosOpt.isEmpty()) {
            throw new IllegalArgumentException("Registro inválido");
        }
        registrosRepo.deleteById(id);
		return "redirect:/log";
	}
}
