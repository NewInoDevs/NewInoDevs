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

import com.api.inodevs.entidades.Fatura;
import com.api.inodevs.repositorio.FaturaRepositorio;
import com.api.inodevs.repositorio.NotificacoesRepositorio;

//Classe de controle que permite a navegação e funcionalidades no sistema:
@Controller
public class ControleFatura {
	
	// Adicionando repositório da fatura para salvar e ler dados no banco no banco:
	@Autowired
	private FaturaRepositorio faturaRepo;
	@Autowired
	private NotificacoesRepositorio notificacoesRepo;
	
	// Download da fatura:
	@GetMapping("/download/{id}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable long id){
		Fatura fatura = faturaRepo.findById(id).get();
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(fatura.getTipo_fatura()))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + fatura.getNome_fatura() + "\"")
				.body(new ByteArrayResource(fatura.getFatura()));
	}
	
	// Abrir página para alterar uma fatura ao clicar no botão Inserir Novo:
	@GetMapping("/inserirFatura/{id}")
	public String inserirFatura(@PathVariable long id, Model modelo) {
        Optional<Fatura> faturaOpt = faturaRepo.findById(id);
        if (faturaOpt.isEmpty()) {
            throw new IllegalArgumentException("Fatura inválida");
        }
        modelo.addAttribute("fatura", faturaOpt.get());
        modelo.addAttribute("quantidadeConta", notificacoesRepo.contar("Conta", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeConcessionaria", notificacoesRepo.contar("Concessionaria", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeUnidade", notificacoesRepo.contar("Unidade", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeContrato", notificacoesRepo.contar("Contrato", "ROLE_GESTOR"));
        modelo.addAttribute("quantidadeContaRep", notificacoesRepo.contar("Conta", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeConcessionariaRep", notificacoesRepo.contar("Concessionaria", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeUnidadeRep", notificacoesRepo.contar("Unidade", "ROLE_DIGITADOR"));
        modelo.addAttribute("quantidadeContratoRep", notificacoesRepo.contar("Contrato", "ROLE_DIGITADOR"));
		return "pages/forms/edit/faturaEdit";
	}
	
	// Salvar nova fatura no banco de dados:
	@PostMapping("/salvarFatura/{id}")
	public String salvarFatura(@ModelAttribute("fatura") Fatura fatura, @RequestParam("faturaPdf") MultipartFile file, RedirectAttributes redirect, @PathVariable long id) {
        redirect.addFlashAttribute("successo", "Cadastrado com sucesso!");
		try {
			fatura.setNome_fatura(file.getOriginalFilename());
			fatura.setTipo_fatura(file.getContentType());
			fatura.setFatura(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		faturaRepo.save(fatura);
		return "redirect:/conta/" + id;
	}
}
