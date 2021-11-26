package com.toiletuserservice;

import com.toiletuserservice.domain.Role;
import com.toiletuserservice.domain.ToiletUser;
import com.toiletuserservice.service.ToiletUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class ToiletuserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToiletuserserviceApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
