package com.api.inodevs.controle;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.api.inodevs.entidades.Concessionaria;
import com.api.inodevs.entidades.Notificacoes;
import com.api.inodevs.entidades.Registros;
import com.api.inodevs.entidades.Usuario;
import com.api.inodevs.repositorio.ConcessionariaRepositorio;
import com.api.inodevs.repositorio.NotificacoesRepositorio;
import com.api.inodevs.repositorio.RegistrosRepositorio;
import com.api.inodevs.repositorio.UsuarioRepositorio;

// Classe de controle que permite a navegação e funcionalidades no sistema:
@Controller
public class ControleConcessionaria {
	
	// Adicionando repositório da concessionária para salvar e ler dados no banco:
	@Autowired
	private ConcessionariaRepositorio concessionariaRepo;
	@Autowired
	private NotificacoesRepositorio notificacoesRepo;
	@Autowired
	private RegistrosRepositorio registrosRepo;
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	// Entrar na página de cadastro de concessionária com o modelo da entidade:
	@GetMapping("/cadastroConcessionaria")
	public String cadastroConcessionaria(@ModelAttribute("concessionaria") Concessionaria concessionaria, Model modelo, @AuthenticationPrincipal User user){
        modelo.addAttribute("quantidadeConta", notificacoesRepo.contar("Conta", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeConcessionaria", notificacoesRepo.contar("Concessionaria", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeUnidade", notificacoesRepo.contar("Unidade", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeContrato", notificacoesRepo.contar("Contrato", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeContaRep", notificacoesRepo.contar("Conta", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeConcessionariaRep", notificacoesRepo.contar("Concessionaria", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeUnidadeRep", notificacoesRepo.contar("Unidade", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeContratoRep", notificacoesRepo.contar("Contrato", "ROLE_DIGITADOR"));
		concessionaria.setTipo_conta("Energia");
		
        Optional<Usuario> usuarioOpt = usuarioRepo.findById(Long.parseLong(user.getUsername()));
        modelo.addAttribute("usuarioInfo", usuarioOpt.get());
		return "pages/forms/concessionaria";
	}
	
	// Salvar uma concessionária no banco ao clicar em cadastrar:
	@PostMapping("/salvarConcessionaria")
    public String salvarConcessionaria(@ModelAttribute("concessionaria") Concessionaria concessionaria, RedirectAttributes redirect, @Param("username") String username) {
        Notificacoes notificacoes = new Notificacoes("ROLE_GESTOR", "Concessionaria");
        redirect.addFlashAttribute("successo", "Cadastrado com sucesso!");
        concessionaria.setNotificacoes(notificacoes);
        concessionaria.setStatus("Pendente");
        concessionariaRepo.save(concessionaria);
        Registros registros = new Registros();
        registros.setAtividade("cadastrou uma concessionaria");
        registros.setData_atividade(LocalDateTime.now ());
		Optional <Usuario> usuarioOpt = usuarioRepo.findById(Long.parseLong(username, 10));
		if (usuarioOpt.isEmpty()) {
			throw new IllegalArgumentException("Usuário inválido");
		}
        registros.setUsuario(usuarioOpt.get());
        registrosRepo.save(registros);
        return "redirect:cadastroConcessionaria";
    }
	
	// Abrir mais inforações da concessionária clicando na tabela para permitir a edição de um cadastro:
	@GetMapping("/concessionaria/{codigo}")
    public String abrirConcessionaria(@PathVariable("codigo") long codigo, Model modelo, @AuthenticationPrincipal User user) {
        Optional<Concessionaria> concessionariaOpt = concessionariaRepo.findById(codigo);
        if (concessionariaOpt.isEmpty()) {
            throw new IllegalArgumentException("Concessionária inválida");
        }
        modelo.addAttribute("concessionaria", concessionariaOpt.get());
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
        return "pages/forms/edit/concessionariaEdit";
    }
	
	// Salvar a concessionária editada no banco de dados ao clicar em editar:
	@PostMapping("/salvarConcessionariaEditRep/{id}")
    public String salvarConcessionariaEditRep(@ModelAttribute("concessionaria") Concessionaria concessionaria, @PathVariable("id") long id) {
        concessionaria.setNotificacoes(null);
        concessionariaRepo.save(concessionaria);
        notificacoesRepo.deleteById(id);
        concessionaria.setStatus("Pendente");
        Notificacoes notificacoes = new Notificacoes("ROLE_GESTOR", "Concessionaria");
        concessionaria.setNotificacoes(notificacoes);
        concessionariaRepo.save(concessionaria);
        return "redirect:/tabela";
    }

    @PostMapping("/salvarConcessionariaEdit")
    public String salvarConcessionariaEdit(@ModelAttribute("concessionaria") Concessionaria concessionaria, RedirectAttributes redirect) {
        concessionaria.setStatus("Pendente");
        Notificacoes notificacoes = new Notificacoes("ROLE_GESTOR", "Concessionaria");
        concessionaria.setNotificacoes(notificacoes);
        concessionariaRepo.save(concessionaria);
        return "redirect:/tabela";
    }
	
	// Excluir uma concessionária ao clicar em excluir na tabela:
	@GetMapping("/excluirConcessionaria/{codigo}")
    public String excluirConcessionaria(@PathVariable("codigo") long codigo, @AuthenticationPrincipal User usuario) {
        Optional<Concessionaria> concessionariaOpt = concessionariaRepo.findById(codigo);
        if (concessionariaOpt.isEmpty()) {
            throw new IllegalArgumentException("Concessionaria inválido");
        }
        concessionariaRepo.deleteById(codigo);
        Registros registros = new Registros();
        registros.setAtividade("excluiu uma concessionaria");
        registros.setData_atividade(LocalDateTime.now ());
		Optional <Usuario> usuarioOpt = usuarioRepo.findById(Long.parseLong(usuario.getUsername(), 10));
		if (usuarioOpt.isEmpty()) {
			throw new IllegalArgumentException("Usuário inválido");
		}
        registros.setUsuario(usuarioOpt.get());
        registrosRepo.save(registros);
        return "redirect:/tabela";
    }
	
	@PostMapping("/aprovarConcessionaria/{id}")
	public String aprovarConcessionaria(@ModelAttribute("concessionaria") Concessionaria concessionaria, @PathVariable("id") long id) {
		concessionaria.setNotificacoes(null);
		concessionaria.setStatus("Aprovado");
		concessionariaRepo.save(concessionaria);
		notificacoesRepo.deleteById(id);
		return "redirect:/tabela";
	}
	
	@PostMapping("/reprovarConcessionaria/{id}")
    public String reprovarConcessionaria(@ModelAttribute("concessionaria") Concessionaria concessionaria, @PathVariable("id") long id) {
        concessionaria.setNotificacoes(null);
        concessionariaRepo.save(concessionaria);
        notificacoesRepo.deleteById(id);
        concessionaria.setStatus("Reprovado");
        Notificacoes notificacoes = new Notificacoes("ROLE_DIGITADOR", "Concessionaria");
        concessionaria.setNotificacoes(notificacoes);
        concessionariaRepo.save(concessionaria);
        return "redirect:/tabela";
    }
	
	@PostMapping("/reprovarConcessionariaRep")
    public String reprovarConcessionariaRep(@ModelAttribute("concessionaria") Concessionaria concessionaria) {
        concessionaria.setStatus("Reprovado");
        Notificacoes notificacoes = new Notificacoes("ROLE_DIGITADOR", "Concessionaria");
        concessionaria.setNotificacoes(notificacoes);
        concessionariaRepo.save(concessionaria);
        return "redirect:/tabela";
    }
	@PostMapping("/aprovarCocessionariaRep")
    public String aprovarConcessionariaRep(@ModelAttribute("concessionaria") Concessionaria concessionaria) {
        concessionaria.setStatus("Aprovado");
        concessionariaRepo.save(concessionaria);
        return "redirect:/tabela";
    }
}