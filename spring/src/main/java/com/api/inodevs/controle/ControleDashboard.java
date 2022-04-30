package com.api.inodevs.controle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControleDashboard {
	@GetMapping("/")
	public String dash() {
		return "index";
	}
}
