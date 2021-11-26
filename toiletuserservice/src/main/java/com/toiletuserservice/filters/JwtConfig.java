package com.toiletuserservice.filters;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpirationAfterMiliseconds;

    public JwtConfig() {}

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public Integer getTokenExpirationAfterMiliseconds() {
        return tokenExpirationAfterMiliseconds;
    }

    public void setTokenExpirationAfterMiliseconds(Integer tokenExpirationAfterMiliseconds) {
        this.tokenExpirationAfterMiliseconds = tokenExpirationAfterMiliseconds;
    }
    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }

}