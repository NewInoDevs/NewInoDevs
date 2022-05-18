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
import com.api.inodevs.entidades.Contrato;
import com.api.inodevs.entidades.Notificacoes;
import com.api.inodevs.entidades.Registros;
import com.api.inodevs.entidades.Unidade;
import com.api.inodevs.entidades.Usuario;
import com.api.inodevs.repositorio.ConcessionariaRepositorio;
import com.api.inodevs.repositorio.ContratoRepositorio;
import com.api.inodevs.repositorio.NotificacoesRepositorio;
import com.api.inodevs.repositorio.RegistrosRepositorio;
import com.api.inodevs.repositorio.UnidadeRepositorio;
import com.api.inodevs.repositorio.UsuarioRepositorio;

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
	@Autowired
	private NotificacoesRepositorio notificacoesRepo;
	@Autowired
	private RegistrosRepositorio registrosRepo;
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	// Entrar na página de cadastro de contrato com o modelo da entidade:
	@GetMapping("/cadastroContrato")
	public String cadastroContrato(@ModelAttribute("contrato") Contrato contrato, Model modelo){
		Concessionaria concessionaria = new Concessionaria();
		concessionaria.setCodigo(0L);
		contrato.setConcessionaria(concessionaria);
		Unidade unidade = new Unidade();
		unidade.setCnpj(0L);
		contrato.setUnidade(unidade);
		modelo.addAttribute("listaConcessionaria", concessionariaRepo.findAll());
		modelo.addAttribute("listaUnidade", unidadeRepo.findAll());
        modelo.addAttribute("quantidadeConta", notificacoesRepo.contar("Conta", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeConcessionaria", notificacoesRepo.contar("Concessionaria", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeUnidade", notificacoesRepo.contar("Unidade", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeContrato", notificacoesRepo.contar("Contrato", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeContaRep", notificacoesRepo.contar("Conta", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeConcessionariaRep", notificacoesRepo.contar("Concessionaria", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeUnidadeRep", notificacoesRepo.contar("Unidade", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeContratoRep", notificacoesRepo.contar("Contrato", "ROLE_DIGITADOR"));
		return "pages/forms/contrato";
	}
	
	// Salvar uma contrato no banco ao clicar em cadastrar:
	@PostMapping("/salvarContrato")
    public String salvarContrato(@ModelAttribute("contrato") Contrato contrato, RedirectAttributes redirect, @Param("username") String username) {
        Notificacoes notificacoes = new Notificacoes("ROLE_GESTOR", "Contrato");
        redirect.addFlashAttribute("successo", "Cadastrado com sucesso!");
        contrato.setNotificacoes(notificacoes);
        contrato.setStatus("Pendente");
        contratoRepo.save(contrato);
        Registros registros = new Registros();
        registros.setAtividade("cadastrou um contrato");
        registros.setData_atividade(LocalDateTime.now ());
		Optional <Usuario> usuarioOpt = usuarioRepo.findById(Long.parseLong(username, 10));
		if (usuarioOpt.isEmpty()) {
			throw new IllegalArgumentException("Usuário inválido");
		}
        registros.setUsuario(usuarioOpt.get());
        registrosRepo.save(registros);
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
        modelo.addAttribute("quantidadeConta", notificacoesRepo.contar("Conta", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeConcessionaria", notificacoesRepo.contar("Concessionaria", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeUnidade", notificacoesRepo.contar("Unidade", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeContrato", notificacoesRepo.contar("Contrato", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeContaRep", notificacoesRepo.contar("Conta", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeConcessionariaRep", notificacoesRepo.contar("Concessionaria", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeUnidadeRep", notificacoesRepo.contar("Unidade", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeContratoRep", notificacoesRepo.contar("Contrato", "ROLE_DIGITADOR"));
        return "pages/forms/edit/contratoEdit";
    }
	
	// Salvar o contrato editado no banco de dados ao clicar em editar:
	@PostMapping("/salvarContratoEditRep/{id}")
    public String salvarContratoEditRep(@ModelAttribute("contrato") Contrato contrato, @PathVariable("id") long id) {
        contrato.setNotificacoes(null);
        contratoRepo.save(contrato);
        notificacoesRepo.deleteById(id);
        contrato.setStatus("Pendente");
        Notificacoes notificacoes = new Notificacoes("ROLE_GESTOR", "Contrato");
        contrato.setNotificacoes(notificacoes);
        contratoRepo.save(contrato);
        return "redirect:/tabela";
    }

    @PostMapping("/salvarContratoEdit")
    public String salvarContratoEdit(@ModelAttribute("contrato") Contrato contrato, RedirectAttributes redirect) {
        contrato.setStatus("Pendente");
        Notificacoes notificacoes = new Notificacoes("ROLE_GESTOR", "Contrato");
        contrato.setNotificacoes(notificacoes);
        contratoRepo.save(contrato);
        return "redirect:/tabela";
    }
	
	// Excluir um contrato ao clicar em excluir na tabela:
	@GetMapping("/excluirContrato/{codigo}")
    public String excluirContrato(@PathVariable("codigo") long codigo, @AuthenticationPrincipal User usuario) {
        Optional<Contrato> contratoOpt = contratoRepo.findById(codigo);
        if (contratoOpt.isEmpty()) {
            throw new IllegalArgumentException("Contrato inválido");
        }
        contratoRepo.deleteById(codigo);
        Registros registros = new Registros();
        registros.setAtividade("excluiu um contrato");
        registros.setData_atividade(LocalDateTime.now ());
		Optional <Usuario> usuarioOpt = usuarioRepo.findById(Long.parseLong(usuario.getUsername(), 10));
		if (usuarioOpt.isEmpty()) {
			throw new IllegalArgumentException("Usuário inválido");
		}
        registros.setUsuario(usuarioOpt.get());
        registrosRepo.save(registros);
        return "redirect:/tabela";
    }

    @PostMapping("/aprovarContrato/{id}")
    public String aprovarContrato(@ModelAttribute("contrato") Contrato contrato, @PathVariable("id") long id) {
        contrato.setNotificacoes(null);
        contrato.setStatus("Aprovado");
        contratoRepo.save(contrato);
        notificacoesRepo.deleteById(id);
        return "redirect:/tabela";
    }
	
	@PostMapping("/reprovarContrato/{id}")
    public String reprovarContrato(@ModelAttribute("contrato") Contrato contrato, @PathVariable("id") long id) {
        contrato.setNotificacoes(null);
        contratoRepo.save(contrato);
        notificacoesRepo.deleteById(id);
        contrato.setStatus("Reprovado");
        Notificacoes notificacoes = new Notificacoes("ROLE_DIGITADOR", "Contrato");
        contrato.setNotificacoes(notificacoes);
        contratoRepo.save(contrato);
        return "redirect:/tabela";
    }
	
	@PostMapping("/reprovarContratoRep")
    public String reprovarContratoRep(@ModelAttribute("contrato") Contrato contrato) {
        contrato.setStatus("Reprovado");
        Notificacoes notificacoes = new Notificacoes("ROLE_DIGITADOR", "Contrato");
        contrato.setNotificacoes(notificacoes);
        contratoRepo.save(contrato);
        return "redirect:/tabela";
    }
	@PostMapping("/aprovarContratoRep")
    public String aprovarContratoRep(@ModelAttribute("contrato") Contrato contrato) {
        contrato.setStatus("Aprovado");
        contratoRepo.save(contrato);
        return "redirect:/tabela";
    }
}