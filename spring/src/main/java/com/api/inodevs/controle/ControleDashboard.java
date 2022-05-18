package com.api.inodevs.controle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//Classe de controle que permite a navegação e funcionalidades no sistema:
@Controller
public class ControleDashboard {
	
	// Entrar no dashboard:
	@GetMapping("/")
	public String dash() {
		return "pages/dashboard";
	}
	
}