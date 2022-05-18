package com.api.inodevs.controle;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.api.inodevs.entidades.Conta;
import com.api.inodevs.entidades.Contrato;
import com.api.inodevs.entidades.Fatura;
import com.api.inodevs.entidades.Notificacoes;
import com.api.inodevs.entidades.Registros;
import com.api.inodevs.repositorio.ContaRepositorio;
import com.api.inodevs.repositorio.ContratoRepositorio;
import com.api.inodevs.repositorio.NotificacoesRepositorio;
import com.api.inodevs.repositorio.RegistrosRepositorio;

//Classe de controle que permite a navegação e funcionalidades no sistema:
@Controller
public class ControleConta {

	// Adicionando repositório da conta e fatura para salvar e ler dados no banco:
	@Autowired
	private ContaRepositorio contaRepo;
	@Autowired
	private ContratoRepositorio contratoRepo;
	@Autowired
	private NotificacoesRepositorio notificacoesRepo;
	@Autowired
	private RegistrosRepositorio registrosRepo;
	
	// Entrar na página de cadastro de conta com o modelo da entidade:
	@GetMapping("/cadastroConta")
	public String cadastroConta(@ModelAttribute("conta") Conta conta, Model modelo){
		Contrato contrato = new Contrato();
        contrato.setCodigo(0L);
        conta.setContrato(contrato);
		modelo.addAttribute("listaContrato", contratoRepo.findAll());
        modelo.addAttribute("quantidadeConta", notificacoesRepo.contar("Conta", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeConcessionaria", notificacoesRepo.contar("Concessionaria", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeUnidade", notificacoesRepo.contar("Unidade", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeContrato", notificacoesRepo.contar("Contrato", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeContaRep", notificacoesRepo.contar("Conta", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeConcessionariaRep", notificacoesRepo.contar("Concessionaria", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeUnidadeRep", notificacoesRepo.contar("Unidade", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeContratoRep", notificacoesRepo.contar("Contrato", "ROLE_DIGITADOR"));
		return "pages/forms/contas";
	}
	
	// Salvar uma conta e uma fatura no banco ao clicar em cadastrar:
	@PostMapping("/salvarConta")
	public String salvarConta(@ModelAttribute("conta") Conta conta, @RequestParam("faturaPdf") MultipartFile file, RedirectAttributes redirect) {
		conta.setStatus("Pendente");
		// Salvando o arquivo da fatura:
		redirect.addFlashAttribute("successo", "Cadastrado com sucesso!");
		try {
			Fatura fatura = new Fatura();
			fatura.setNome_fatura(file.getOriginalFilename());
			fatura.setTipo_fatura(file.getContentType());
			fatura.setFatura(file.getBytes());
			conta.setFatura(fatura);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Notificacoes notificacoes = new Notificacoes("ROLE_GESTOR", "Conta");
		conta.setNotificacoes(notificacoes);
		contaRepo.save(conta);
		Registros registros = new Registros();
        registros.setAtividade("Cadastrou uma conta");
        registros.setData_atividade(LocalDateTime.now ());
        registrosRepo.save(registros);
		return "redirect:cadastroConta";
	}
	
	// Abrir mais inforações da conta clicando na tabela para permitir a edição de um cadastro:
	@GetMapping("/conta/{codi}")
    public String abrirConta(@PathVariable("codi") long codi, Model modelo) {
		modelo.addAttribute("listaContrato", contratoRepo.findAll());
        Optional<Conta> contaOpt = contaRepo.findById(codi);
        if (contaOpt.isEmpty()) {
            throw new IllegalArgumentException("Conta inválida");
        }
        modelo.addAttribute("conta", contaOpt.get());
        modelo.addAttribute("quantidadeConta", notificacoesRepo.contar("Conta", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeConcessionaria", notificacoesRepo.contar("Concessionaria", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeUnidade", notificacoesRepo.contar("Unidade", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeContrato", notificacoesRepo.contar("Contrato", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeContaRep", notificacoesRepo.contar("Conta", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeConcessionariaRep", notificacoesRepo.contar("Concessionaria", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeUnidadeRep", notificacoesRepo.contar("Unidade", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeContratoRep", notificacoesRepo.contar("Contrato", "ROLE_DIGITADOR"));
        return "pages/forms/edit/faturas";
    }
	
	// Salvar a conta editada no banco de dados ao clicar em editar:
	@PostMapping("/salvarContaEditRep/{id}")
    public String salvarContaEditRep(@ModelAttribute("conta") Conta conta, @PathVariable("id") long id) {
        conta.setNotificacoes(null);
        contaRepo.save(conta);
        notificacoesRepo.deleteById(id);
        conta.setStatus("Pendente");
        Notificacoes notificacoes = new Notificacoes("ROLE_GESTOR", "Conta");
        conta.setNotificacoes(notificacoes);
        contaRepo.save(conta);
        return "redirect:/tabela";
    }

    @PostMapping("/salvarContaEdit")
    public String salvarContaEdit(@ModelAttribute("conta") Conta conta, RedirectAttributes redirect) {
    	conta.setStatus("Pendente");
        Notificacoes notificacoes = new Notificacoes("ROLE_GESTOR", "Conta");
        conta.setNotificacoes(notificacoes);
        contaRepo.save(conta);
        return "redirect:/tabela";
    }
	
	// Excluir uma conta ao clicar em excluir na tabela:
	@GetMapping("/excluirConta/{codi}")
    public String excluirConta(@PathVariable("codi") long codi) {
        Optional<Conta> contaOpt = contaRepo.findById(codi);
        if (contaOpt.isEmpty()) {
            throw new IllegalArgumentException("Conta inválido");
        }
        contaRepo.deleteById(codi);
        Registros registros = new Registros();
        registros.setAtividade("Cadastrou uma concessionaria");
        registros.setData_atividade(LocalDateTime.now ());
        registrosRepo.save(registros);
        return "redirect:/tabela";
    }
	
	@PostMapping("/aprovarConta/{id}")
	public String aprovarConta(@ModelAttribute("conta") Conta conta, @PathVariable("id") long id) {
		conta.setNotificacoes(null);
		conta.setStatus("Aprovado");
		contaRepo.save(conta);
		notificacoesRepo.deleteById(id);
		return "redirect:/tabela";
	}
	
	@PostMapping("/aprovarContaRep")
	public String aprovarContaRep(@ModelAttribute("conta") Conta conta) {
		conta.setStatus("Aprovado");
		contaRepo.save(conta);
		return "redirect:/tabela";
	}
	
	@PostMapping("/reprovarConta/{id}")
    public String reprovarConta(@ModelAttribute("conta") Conta conta, @PathVariable("id") long id) {
        conta.setNotificacoes(null);
        contaRepo.save(conta);
        notificacoesRepo.deleteById(id);
        conta.setStatus("Reprovado");
        Notificacoes notificacoes = new Notificacoes("ROLE_DIGITADOR", "Conta");
        conta.setNotificacoes(notificacoes);
        contaRepo.save(conta);
        return "redirect:/tabela";
    }
	
	@PostMapping("/reprovarContaRep")
    public String reprovarContaRep(@ModelAttribute("conta") Conta conta) {
        conta.setStatus("Reprovado");
        Notificacoes notificacoes = new Notificacoes("ROLE_DIGITADOR", "Conta");
        conta.setNotificacoes(notificacoes);
        contaRepo.save(conta);
        return "redirect:/tabela";
    }
	
}