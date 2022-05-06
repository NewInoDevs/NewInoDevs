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

import com.api.inodevs.entidades.Notificacoes;
import com.api.inodevs.entidades.Unidade;
import com.api.inodevs.repositorio.NotificacoesRepositorio;
import com.api.inodevs.repositorio.UnidadeRepositorio;

//Classe de controle que permite a navegação e funcionalidades no sistema:
@Controller
public class ControleUnidade {
	
	// Adicionando repositório da unidade para salvar e ler dados no banco:
	@Autowired
	private UnidadeRepositorio unidadeRepo;
	@Autowired
	private NotificacoesRepositorio notificacoesRepo;
	
	// Entrar na página de cadastro de unidade com o modelo da entidade com o modelo da entidade:
	@GetMapping("/cadastroUnidade")
	public String cadastroUnidade(@ModelAttribute("unidade") Unidade unidade){
		return "pages/forms/unidade";
	}
	
	// Salvar uma unidade e endereço no banco ao clicar em cadastrar:
	@PostMapping("/salvarUnidade")
    public String salvarUnidade(@ModelAttribute("unidade") Unidade unidade, RedirectAttributes redirect) {
        Notificacoes notificacoes = new Notificacoes("ROLE_GESTOR", "Unidade");
        redirect.addFlashAttribute("successo", "Cadastrado com sucesso!");
        unidade.setNotificacoes(notificacoes);
        unidade.setStatus("Pendente");
        unidadeRepo.save(unidade);
        return "redirect:cadastroUnidade";
    }
	
	// Abrir mais inforações da unidade com o seu endereço clicando na tabela para permitir a edição de um cadastro:
	@GetMapping("/unidade/{cnpj}")
    public String abrirUnidade(@PathVariable("cnpj") long cnpj, Model modelo) {
        Optional<Unidade> unidadeOpt = unidadeRepo.findById(cnpj);
        if (unidadeOpt.isEmpty()) {
            throw new IllegalArgumentException("Unidade inválida");
        }
        modelo.addAttribute("unidade", unidadeOpt.get());
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
    public String excluirUnidade(@PathVariable("cnpj") long cnpj) {
        Optional<Unidade> unidadeOpt = unidadeRepo.findById(cnpj);
        if (unidadeOpt.isEmpty()) {
            throw new IllegalArgumentException("Unidade inválido");
        }
        unidadeRepo.deleteById(cnpj);
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