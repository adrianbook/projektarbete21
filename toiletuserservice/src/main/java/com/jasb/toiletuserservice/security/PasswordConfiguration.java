package com.jasb.toiletuserservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
@Configuration
public class PasswordConfiguration {
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());

    public BCryptPasswordEncoder getbCryptPasswordEncoder() {
        return this.bCryptPasswordEncoder;
    }
}
