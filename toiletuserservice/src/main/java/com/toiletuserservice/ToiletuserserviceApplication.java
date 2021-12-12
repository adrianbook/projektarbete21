package com.toiletuserservice;

import com.jasb.entities.Role;
import com.jasb.entities.ToiletUser;
import com.toiletuserservice.service.ToiletUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EntityScan("com.jasb.entities")
public class ToiletuserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToiletuserserviceApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
