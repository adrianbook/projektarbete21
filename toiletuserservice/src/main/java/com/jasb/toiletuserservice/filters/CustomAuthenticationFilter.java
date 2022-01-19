package com.jasb.toiletuserservice.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasb.toiletuserservice.service.ToiletUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * A Custom authentication filter that allows a registerd user
 * to get a JWT token with their assigned roles for verification
 * in this service and in the toiletprojectbackend service
 *
 * Contains a JwtConfig and a AuthenticationManager
 * Written by JASB
 */
@Slf4j @AllArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private JwtConfig jwtConfig;
    private ToiletUserService userService;

    /**
     * A simple password and username authentication
     * Endpoint is /login and expects a REST POST request with parameters username and password
     * @param request
     * @param response
     * @return Authentication based on username and password authentication
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        log.info("Username is {}", username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        if(userService.getToiletUser(username).isBlocked()) {
            log.info("{} tried to log in but is blocked", username );
            throw new BadCredentialsException("User is blocked");

        }
        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * Generates a JWT containing the users username and authorities
     * on successful authentication on the /login endpoint
     * and sends it in the body of the response.
     * @param request
     * @param response
     * @param chain
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();

        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecretKey().getBytes());
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtConfig.getTokenExpirationAfterMilliseconds()))
                .withIssuer(request.getRequestURI().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("acces_token", jwtConfig.getTokenPrefix() + access_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
