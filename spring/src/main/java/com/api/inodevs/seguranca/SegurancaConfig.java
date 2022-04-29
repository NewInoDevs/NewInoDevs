package com.api.inodevs.seguranca;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SegurancaConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public void configAutenticacao(AuthenticationManagerBuilder authBuilder) throws Exception {
		authBuilder.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
		.dataSource(dataSource)
		.usersByUsernameQuery("select username, senha, ativo from usuario where username=?")
		.authoritiesByUsernameQuery("select username, secao from usuario where username=?");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.requestCache()
				.disable()
			.authorizeRequests()
			.antMatchers("/controleUsuario", "/cadastroUsuario", "/editarUsuario/{id}","/excluirUsuario/{id}")
				.hasAnyRole("ADMINISTRADOR", "GESTOR")
			.anyRequest()
			.authenticated()
			.and()
				.formLogin()
				.loginPage("/login")
				.permitAll()
			.and()
				.logout()
				.logoutSuccessUrl("/login?logout")
				.permitAll()
			;
	}
}