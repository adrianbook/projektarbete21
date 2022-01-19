package com.jasb.toiletuserservice.security;

import com.jasb.toiletuserservice.filters.CustomAuthenticationFilter;
import com.jasb.toiletuserservice.filters.JwtConfig;
import com.jasb.toiletuserservice.filters.JwtTokenVerifier;
import com.jasb.toiletuserservice.service.ToiletUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@EnableWebSecurity @RequiredArgsConstructor @EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordConfiguration passwordConfiguration;
    private final JwtConfig jwtConfig;
    private final ToiletUserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordConfiguration.getbCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/api/user/*")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new CustomAuthenticationFilter(authenticationManagerBean(), jwtConfig, userService))
                .addFilterBefore(new JwtTokenVerifier(jwtConfig), CustomAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cc = new CorsConfiguration();
        cc.setAllowedOrigins(Arrays.asList("*"));
        cc.setAllowedMethods(Arrays.asList("*"));
        cc.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource ucs = new UrlBasedCorsConfigurationSource();
        ucs.registerCorsConfiguration("/**", cc);
        return ucs;
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
