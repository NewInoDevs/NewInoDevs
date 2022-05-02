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

	// Adicionando repositório do usuário para salvar e ler dados no banco:
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	// Entrar na página de Login
	@GetMapping("/login")
	public String login(){
		return "pages/login";
	}
	
	// Entrar na página da tabela de usuários com dados do banco:
	@GetMapping("/controleUsuario")
	public String controleUsuario(Model modelo) {
		modelo.addAttribute("listaUsuario", usuarioRepo.findAll());
		return "pages/controle";
	}
	
	// Entrar na página de cadastro de usuário com o modelo da entidade: 
	@GetMapping("/cadastroUsuario")
	public String cadastroUsuario(@ModelAttribute("usuario") Usuario usuario){
		usuario.setSecao("ROLE_DIGITADOR");
		usuario.setAtivo(1);
		return "pages/forms/usuario";
	}
	
	// Salvar um usuário no banco ao clicar em cadastrar:
	@PostMapping("/salvarUsuario")
	public String salvarUsuarios(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirect) {
		String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha()); // Criptografando senha
		usuario.setSenha(senhaCriptografada); // Inserindo a senha criptografada
		usuarioRepo.save(usuario);
		redirect.addFlashAttribute("sucesso", "Usuário salvo com sucesso!");
		return "redirect:/controleUsuario";
	}
	
	// Abrir mais inforações do usuário clicando na tabela para permitir a edição de um cadastro:
	@GetMapping("/editarUsuario/{username}")
	public String editarUsuarios(@PathVariable("username") long username, Model modelo) {
		Optional<Usuario> usuarioOpt = usuarioRepo.findById(username);
		if (usuarioOpt.isEmpty()) {
			throw new IllegalArgumentException("Usuário inválido");
		}
		modelo.addAttribute("usuario", usuarioOpt.get());
		return "pages/forms/edit/usuarioEdit";
	}
	
	// Salvar o usuário editado no banco de dados ao clicar em editar:
	@PostMapping("/salvarUsuarioEdit")
	public String salvarUsuarioEdit(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirect) {
		usuarioRepo.save(usuario);
		redirect.addFlashAttribute("sucesso", "Usuário salvo com sucesso!");
		return "redirect:/controleUsuario";
	}
	
	// Excluir um usuário ao clicar em excluir na tabela:
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
