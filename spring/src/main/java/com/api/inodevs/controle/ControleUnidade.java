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

import com.api.inodevs.entidades.Notificacoes;
import com.api.inodevs.entidades.Registros;
import com.api.inodevs.entidades.Unidade;
import com.api.inodevs.entidades.Usuario;
import com.api.inodevs.repositorio.NotificacoesRepositorio;
import com.api.inodevs.repositorio.RegistrosRepositorio;
import com.api.inodevs.repositorio.UnidadeRepositorio;
import com.api.inodevs.repositorio.UsuarioRepositorio;

//Classe de controle que permite a navegação e funcionalidades no sistema:
@Controller
public class ControleUnidade {
	
	// Adicionando repositório da unidade para salvar e ler dados no banco:
	@Autowired
	private UnidadeRepositorio unidadeRepo;
	@Autowired
	private NotificacoesRepositorio notificacoesRepo;
	@Autowired
	private RegistrosRepositorio registrosRepo;
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	// Entrar na página de cadastro de unidade com o modelo da entidade com o modelo da entidade:
	@GetMapping("/cadastroUnidade")
	public String cadastroUnidade(@ModelAttribute("unidade") Unidade unidade, Model modelo, @AuthenticationPrincipal User user){
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
		return "pages/forms/unidade";
	}
	
	// Salvar uma unidade e endereço no banco ao clicar em cadastrar:
	@PostMapping("/salvarUnidade")
    public String salvarUnidade(@ModelAttribute("unidade") Unidade unidade, RedirectAttributes redirect, @Param("username") String username) {
        Notificacoes notificacoes = new Notificacoes("ROLE_GESTOR", "Unidade");
        redirect.addFlashAttribute("successo", "Cadastrado com sucesso!");
        unidade.setNotificacoes(notificacoes);
        unidade.setStatus("Pendente");
        unidadeRepo.save(unidade);
        Registros registros = new Registros();
        registros.setAtividade("cadastrou uma unidade");
        registros.setData_atividade(LocalDateTime.now ());
		Optional <Usuario> usuarioOpt = usuarioRepo.findById(Long.parseLong(username, 10));
		if (usuarioOpt.isEmpty()) {
			throw new IllegalArgumentException("Usuário inválido");
		}
        registros.setUsuario(usuarioOpt.get());
        registrosRepo.save(registros);
        return "redirect:cadastroUnidade";
    }
	
	// Abrir mais inforações da unidade com o seu endereço clicando na tabela para permitir a edição de um cadastro:
	@GetMapping("/unidade/{cnpj}")
    public String abrirUnidade(@PathVariable("cnpj") long cnpj, Model modelo, @AuthenticationPrincipal User user) {
        Optional<Unidade> unidadeOpt = unidadeRepo.findById(cnpj);
        if (unidadeOpt.isEmpty()) {
            throw new IllegalArgumentException("Unidade inválida");
        }
        modelo.addAttribute("unidade", unidadeOpt.get());
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
        return "pages/forms/edit/unidadeEdit";
    }
	
	// Salvar a unidade e endereço editados no banco de dados ao clicar em editar:
	@PostMapping("/salvarUnidadeEditRep/{id}")
    public String salvarUnidadeEditRep(@ModelAttribute("unidade") Unidade unidade, @PathVariable("id") long id) {
        unidade.setNotificacoes(null);
        unidadeRepo.save(unidade);
        notificacoesRepo.deleteById(id);
        unidade.setStatus("Pendente");
        Notificacoes notificacoes = new Notificacoes("ROLE_GESTOR", "Unidade");
        unidade.setNotificacoes(notificacoes);
        unidadeRepo.save(unidade);
        return "redirect:/tabela";
    }

    @PostMapping("/salvarUnidadeEdit")
    public String salvaUnidadeaEdit(@ModelAttribute("unidade") Unidade unidade, RedirectAttributes redirect) {
        unidade.setStatus("Pendente");
        Notificacoes notificacoes = new Notificacoes("ROLE_GESTOR", "Unidade");
        unidade.setNotificacoes(notificacoes);
        unidadeRepo.save(unidade);
        return "redirect:/tabela";
    }
	
	// Excluir uma unidade (junto com o seu endereço) ao clicar em excluir na tabela:
	@GetMapping("/excluirUnidade/{cnpj}")
    public String excluirUnidade(@PathVariable("cnpj") long cnpj, @AuthenticationPrincipal User usuario) {
        Optional<Unidade> unidadeOpt = unidadeRepo.findById(cnpj);
        if (unidadeOpt.isEmpty()) {
            throw new IllegalArgumentException("Unidade inválido");
        }
        unidadeRepo.deleteById(cnpj);
        Registros registros = new Registros();
        registros.setAtividade("excluiu uma unidade");
        registros.setData_atividade(LocalDateTime.now ());
		Optional <Usuario> usuarioOpt = usuarioRepo.findById(Long.parseLong(usuario.getUsername(), 10));
		if (usuarioOpt.isEmpty()) {
			throw new IllegalArgumentException("Usuário inválido");
		}
        registros.setUsuario(usuarioOpt.get());
        registrosRepo.save(registros);
        return "redirect:/tabela";
    }
	
	@PostMapping("/aprovarUnidade/{id}")
	public String aprovarConta(@ModelAttribute("unidade") Unidade unidade, @PathVariable("id") long id) {
		unidade.setNotificacoes(null);
		unidade.setStatus("Aprovado");
		unidadeRepo.save(unidade);
		notificacoesRepo.deleteById(id);
		return "redirect:/tabela";
	}
	
	@PostMapping("/reprovarUnidade/{id}")
    public String reprovarUnidade(@ModelAttribute("unidade") Unidade unidade, @PathVariable("id") long id) {
        unidade.setNotificacoes(null);
        unidadeRepo.save(unidade);
        notificacoesRepo.deleteById(id);
        unidade.setStatus("Reprovado");
        Notificacoes notificacoes = new Notificacoes("ROLE_DIGITADOR", "Unidade");
        unidade.setNotificacoes(notificacoes);
        unidadeRepo.save(unidade);
        return "redirect:/tabela";
    }
	
	@PostMapping("/reprovarUnidadeRep")
    public String reprovarUnidadeRep(@ModelAttribute("unidade") Unidade unidade) {
        unidade.setStatus("Reprovado");
        Notificacoes notificacoes = new Notificacoes("ROLE_DIGITADOR", "Unidade");
        unidade.setNotificacoes(notificacoes);
        unidadeRepo.save(unidade);
        return "redirect:/tabela";
    }
	
	@PostMapping("/aprovarUnidadeRep")
    public String aprovarUnidadeRep(@ModelAttribute("unidade") Unidade unidade) {
        unidade.setStatus("Aprovado");
        unidadeRepo.save(unidade);
        return "redirect:/tabela";
    }
}