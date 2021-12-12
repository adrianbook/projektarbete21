package com.jasb.toiletuserservice.filters;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 * Configuration class for JWT
 * Gets the values from application.properties
 */

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
@Data
@NoArgsConstructor
public class JwtConfig {
    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpirationAfterMilliseconds;
    private String authorizationHeader = HttpHeaders.AUTHORIZATION;
}