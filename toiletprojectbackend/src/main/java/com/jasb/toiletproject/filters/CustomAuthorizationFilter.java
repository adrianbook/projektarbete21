package com.jasb.toiletproject.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Strings;
import com.jasb.toiletproject.exceptions.ToiletUserNotFoundException;
import com.jasb.toiletproject.util.ToiletUserFetcher;
import com.jasb.toiletproject.util.TokenHolder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;

/**
 * Authentication filer that authenticates a JWT and sets the securitycontext
 * based on the roles contained within the token.
 * Extends superclass BasicAuthenticationFilter
 *
 * Contains a authenticationmanager and a JwtConfig
 * Written by JASB
 */

@Slf4j
public class CustomAuthorizationFilter extends BasicAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private JwtConfig jwtConfig;


    /**
     * Constructor that calls the superclass constructor for authenticationmanager
     * @param authenticationManager
     * @param jwtConfig
     */
    public CustomAuthorizationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
        super(authenticationManager);
        this.jwtConfig = jwtConfig;
    }

    /**
     * Function that extracs a JWT from the AUTHORIZATION-header and parses it
     * using o 0auth library for JWT.
     * @param request
     * @return a token containing the roles given by the JWT
     */
    private UsernamePasswordAuthenticationToken parseToken(HttpServletRequest request) throws ToiletUserNotFoundException, IllegalAccessException {
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
        TokenHolder.TOKEN = authorizationHeader;
        String token = authorizationHeader.substring(jwtConfig.getTokenPrefix().length());
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecretKey().getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        if(ToiletUserFetcher.fetchToiletUserByUsername(username).isBlocked()) {
            log.info("blocked user {} tried to access service", username );
            return null;
        }
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        if(roles.length == 0) {
            roles[0] = "NO_ROLE";
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        return authenticationToken;
    }

    /**
     * Overriden function that first checks if the request contains a JWT
     * and if it does calls the parseToken function and sets the securitycontext based
     * on the roles contained in the token if it does not it passes
     * the request down the filterchain.
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

        if (Strings.isNullOrEmpty(authorizationHeader) ||
                !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = parseToken(request);

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}
