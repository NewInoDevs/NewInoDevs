package com.api.inodevs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.api.inodevs.entidades.Usuario;
import com.api.inodevs.repositorio.UsuarioRepositorio;

// Classe que insere um usuário inicial para disponibilizar o login no sistema
// Username: 123
// Senha: 123

@Component
@Transactional
public class UsuarioInicial implements CommandLineRunner {

	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	@Override
	public void run(String... args) throws Exception {
		
	    String encodedPassword = new BCryptPasswordEncoder().encode("123");
		
		Usuario usuario = new Usuario();
		usuario.setUsername(123L);
		usuario.setSenha(encodedPassword);
		usuario.setNome("New");
		usuario.setSobrenome("Inodevs");
		usuario.setSecao("ROLE_ADMINISTRADOR");
		usuario.setAtivo(1);
		usuarioRepo.save(usuario);
		
	}

}