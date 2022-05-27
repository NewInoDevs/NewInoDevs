package com.api.inodevs.seguranca;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Configurando a Segurança do sistema:
@Configuration
@EnableWebSecurity
public class SegurancaConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	// Login comparando com dados que estão no banco de dados (na tabela usuário):
	@Autowired
	public void configAutenticacao(AuthenticationManagerBuilder authBuilder) throws Exception {
		authBuilder.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
		.dataSource(dataSource)
		.usersByUsernameQuery("select username, senha, ativo from usuario where username=?")
		.authoritiesByUsernameQuery("select username, secao from usuario where username=?");
	}
	
	// Configurações do Http para Login:
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// Lista com os caminhos para o css, imagens, fontes e scripts
		String[] staticResources = {
			        "/assets/css/**",
			        "/assets/img/**",
			        "/assets/fonts/**",
			        "/assets/scripts/**",};
		
		 http
		 	// Corrigindo um bug ao logar pela primeira vez:
			.requestCache().disable()
			// Adicionando permissões:
			.authorizeRequests() // 
				// Permitindo que todos (mesmo sem logar) tenham acesso aos arquivos dentro do static
				.antMatchers(staticResources).permitAll()
			 	// Páginas somente disponível para Administradores e gestores:
				.antMatchers("/controleUsuario", "/cadastroUsuario", "/editarUsuario/{id}","/excluirUsuario/{id}", "/log", "/dashboard/{id}", 
						"/excluirConcessionaria/{id}", "/excluirUnidade/{id}", "/excluirContrato/{id}", "/excluirConta/{id}")
					.hasAnyRole("ADMINISTRADOR", "GESTOR")
				.anyRequest()
				.authenticated()
			.and()
				// Adicionando página de login personalizada:
				.formLogin()
				// .defaultSuccessUrl("/tabela", true)
				.loginPage("/login") 
				.permitAll()
			.and()
				// Adicionando logout:
				.logout()
				.logoutSuccessUrl("/login?logout")
				.permitAll()
			;
	}
}