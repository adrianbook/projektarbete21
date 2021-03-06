package com.jasb.toiletproject.filters;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/**
 * Configuration class for JWT
 * Gets the values from application.properties
 * Written by JASB
 */
@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
    private String secretKey;
    private String tokenPrefix;
    private String AuthorizationHeader = HttpHeaders.AUTHORIZATION;

}