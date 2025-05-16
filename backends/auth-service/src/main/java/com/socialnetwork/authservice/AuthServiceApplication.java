package com.socialnetwork.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
        /*BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "12345";              // tu contraseña en texto claro
		String bcryptHash = encoder.encode(rawPassword);
		System.out.println("aja con el codigo: "+ bcryptHash);*/

		SpringApplication.run(AuthServiceApplication.class, args);
		
	}

}
