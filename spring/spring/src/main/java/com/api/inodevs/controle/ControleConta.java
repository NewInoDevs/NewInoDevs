package com.api.inodevs.controle;

import java.io.IOException;
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
import com.api.inodevs.entidades.Fatura;
import com.api.inodevs.repositorio.ContaRepositorio;
import com.api.inodevs.repositorio.ContratoRepositorio;
import com.api.inodevs.repositorio.FaturaRepositorio;

//Classe de controle que permite a navegação e funcionalidades no sistema:
@Controller
public class ControleConta {

	// Adicionando repositório da conta e fatura para salvar e ler dados no banco:
	@Autowired
	private ContaRepositorio contaRepo;
	@Autowired
	private FaturaRepositorio faturaRepo;
	@Autowired
	private ContratoRepositorio contratoRepo;
	
	// Entrar na página de cadastro de conta com o modelo da entidade:
	@GetMapping("/cadastroConta")
	public String cadastroConta(@ModelAttribute("conta") Conta conta, Model modelo){
		modelo.addAttribute("listaContrato", contratoRepo.findAll());
		return "pages/forms/contas";
	}
	
	// Salvar uma conta e uma fatura no banco ao clicar em cadastrar:
	@PostMapping("/salvarConta")
	public String salvarConta(@ModelAttribute("conta") Conta conta, @ModelAttribute("fatura") Fatura fatura, @RequestParam("faturaPdf") MultipartFile file, RedirectAttributes redirect) {
		// Salvando o arquivo da fatura:
		String nome = file.getOriginalFilename();
        redirect.addFlashAttribute("successo", "Cadastrado com sucesso!");
		try {
			fatura.setNome_fatura(nome);
			fatura.setTipo_fatura(file.getContentType());
			fatura.setFatura(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		faturaRepo.save(fatura);
		contaRepo.save(conta);
		return "redirect:cadastroConta";
	}
	
	// Abrir mais inforações da conta clicando na tabela para permitir a edição de um cadastro:
	@GetMapping("/conta/{codi}")
    public String abrirConta(@PathVariable("codi") long codi, Model modelo) {
        Optional<Conta> contaOpt = contaRepo.findById(codi);
        if (contaOpt.isEmpty()) {
            throw new IllegalArgumentException("Conta inválida");
        }
        modelo.addAttribute("conta", contaOpt.get());
        return "pages/forms/edit/faturas";
    }
	
	// Salvar a conta editada no banco de dados ao clicar em editar:
	@PostMapping("/salvarContaEdit")
	public String salvarContaEdit(@ModelAttribute("conta") Conta conta, RedirectAttributes redirect) {
		contaRepo.save(conta);
		return "redirect:tabela";
	}
	
	// Excluir uma conta ao clicar em excluir na tabela:
	@GetMapping("/excluirConta/{codi}")
    public String excluirConta(@PathVariable("codi") long codi) {
        Optional<Conta> contaOpt = contaRepo.findById(codi);
        if (contaOpt.isEmpty()) {
            throw new IllegalArgumentException("Conta inválido");
        }
        contaRepo.deleteById(codi);
        return "redirect:/tabela";
    }
}