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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



import com.api.inodevs.entidades.Usuario;
import com.api.inodevs.repositorio.UsuarioRepositorio;

@Controller
public class ControleUsuario {

	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	@GetMapping("/login")
	public String login(){
		return "pages/login";
	}
	
	@GetMapping("/cadastroUsuario")
	public String cadastroUsuario(@ModelAttribute("usuario") Usuario usuario){
		usuario.setSecao("ROLE_DIGITADOR");
		usuario.setAtivo(1);
		return "pages/forms/usuario";
	}
	
	@GetMapping("/controleUsuario")
	public String controleUsuario(Model modelo) {
		modelo.addAttribute("listaUsuario", usuarioRepo.findAll());
		return "pages/controle";
	}
	
	@PostMapping("/salvarUsuario")
	public String salvarUsuarios(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirect) {
		String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);
		usuarioRepo.save(usuario);
		redirect.addFlashAttribute("sucesso", "Usuário salvo com sucesso!");
		return "redirect:/controleUsuario";
	}
	
	@PostMapping("/salvarUsuarioEdit")
	public String salvarUsuarioEdit(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirect) {
		usuarioRepo.save(usuario);
		redirect.addFlashAttribute("sucesso", "Usuário salvo com sucesso!");
		return "redirect:/controleUsuario";
	}
	
	@GetMapping("/editarUsuario/{username}")
	public String editarUsuarios(@PathVariable("username") long username, Model modelo) {
		Optional<Usuario> usuarioOpt = usuarioRepo.findById(username);
		if (usuarioOpt.isEmpty()) {
			throw new IllegalArgumentException("Usuário inválido");
		}
		modelo.addAttribute("usuario", usuarioOpt.get());
		return "pages/forms/edit/usuarioEdit";
	}
	
	@GetMapping("/excluirUsuario/{username}")
	public String excluirUsuarios(@PathVariable("username") long username) {
		Optional<Usuario> usuarioOpt = usuarioRepo.findById(username);
		if (usuarioOpt.isEmpty()) {
			throw new IllegalArgumentException("Usuário inválido");
		}
		usuarioRepo.deleteById(username);
		return "redirect:/controleUsuario";
	}
}
