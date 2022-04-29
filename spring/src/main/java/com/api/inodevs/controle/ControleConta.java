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
import com.api.inodevs.entidades.Contrato;
import com.api.inodevs.repositorio.ContaRepositorio;

@Controller
public class ControleConta {

	@Autowired
	private ContaRepositorio contaRepo;
	
	@GetMapping("/cadastroConta")
	public String cadastroConta(@ModelAttribute("conta") Conta conta){
		conta.setTipo_conta("energia");
		return "pages/forms/contas";
	}
	
	@PostMapping("/salvarConta")
	public String salvarConta(@ModelAttribute("conta") Conta conta, @RequestParam("faturaPdf") MultipartFile file, RedirectAttributes redirect) {
		String nome = file.getOriginalFilename();
        redirect.addFlashAttribute("successo", "Cadastrado com sucesso!");
		try {
			conta.setNome_fatura(nome);
			conta.setTipo_fatura(file.getContentType());
			conta.setFatura(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		contaRepo.save(conta);
		return "redirect:cadastroConta";
	}
	
	@GetMapping("/conta/{codi}")
    public String abrirConta(@PathVariable("codi") long codi, Model modelo) {
        Optional<Conta> contaOpt = contaRepo.findById(codi);
        if (contaOpt.isEmpty()) {
            throw new IllegalArgumentException("Conta inválida");
        }
        modelo.addAttribute("conta", contaOpt.get());
        return "pages/forms/edit/faturas";
    }

	@GetMapping("/download/{codi}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable long codi){
		Conta conta = contaRepo.findById(codi).get();
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(conta.getTipo_fatura()))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + conta.getNome_fatura() + "\"")
				.body(new ByteArrayResource(conta.getFatura()));
	}
	
	@PostMapping("/salvarContaEdit")
    public String salvarContaEdit(@ModelAttribute("conta") Conta conta, RedirectAttributes redirect) {
        contaRepo.save(conta);
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
