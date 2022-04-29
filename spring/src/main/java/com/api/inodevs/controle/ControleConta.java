package com.api.inodevs.controle;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.api.inodevs.repositorio.FaturaRepositorio;

@Controller
public class ControleConta {

	@Autowired
	private ContaRepositorio contaRepo;
	
	@Autowired
	private FaturaRepositorio faturaRepo;
	
	@GetMapping("/cadastroConta")
	public String cadastroConta(@ModelAttribute("conta") Conta conta){
		conta.setTipo_conta("energia");
		return "pages/forms/contas";
	}
	
	@PostMapping("/salvarConta")
	public String salvarConta(@ModelAttribute("conta") Conta conta, @ModelAttribute("fatura") Fatura fatura, @RequestParam("faturaPdf") MultipartFile file, RedirectAttributes redirect) {
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
		conta.setFatura(fatura.getId());
		contaRepo.save(conta);
		return "redirect:cadastroConta";
	}
	
	@PostMapping("/salvarContaEdit")
	public String salvarContaEdit(@ModelAttribute("conta") Conta conta, RedirectAttributes redirect) {
		contaRepo.save(conta);
		return "redirect:tabela";
	}
	
	@GetMapping("/conta/{codi}")
    public String abrirConta(@PathVariable("codi") long codi, Model modelo) {
        Optional<Conta> contaOpt = contaRepo.findById(codi);
        if (contaOpt.isEmpty()) {
            throw new IllegalArgumentException("Conta inválida");
        }
        modelo.addAttribute("conta", contaOpt.get());
        modelo.addAttribute("listaFatura", faturaRepo.findAll());
        return "pages/forms/edit/faturas";
    }

	@GetMapping("/download/{id}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable long id){
		Fatura fatura = faturaRepo.findById(id).get();
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(fatura.getTipo_fatura()))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + fatura.getNome_fatura() + "\"")
				.body(new ByteArrayResource(fatura.getFatura()));
	}
	
	@GetMapping("/inserirFatura/{id}")
	public String inserirFatura(@PathVariable long id, Model modelo) {
        Optional<Fatura> faturaOpt = faturaRepo.findById(id);
        if (faturaOpt.isEmpty()) {
            throw new IllegalArgumentException("Fatura inválida");
        }
        modelo.addAttribute("fatura", faturaOpt.get());
		return "pages/forms/edit/faturaEdit";
	}
	
	@PostMapping("/salvarFatura")
	public String salvarFatura(@ModelAttribute("fatura") Fatura fatura, @RequestParam("faturaPdf") MultipartFile file, RedirectAttributes redirect) {
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
		return "redirect:tabela";
	}
	
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
