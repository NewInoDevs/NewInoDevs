package com.api.inodevs;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Senha {

	public static void main(String[] args) {
	    String encodedPassword = new BCryptPasswordEncoder().encode("123");
	    System.out.println(encodedPassword);
	}

}