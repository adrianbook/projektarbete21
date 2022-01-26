package com.jasb.toiletproject.util;

import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.exceptions.ToiletUserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Class for getting ToiletUser from user service REST-api
 */
@Slf4j
public class ToiletUserFetcher {
    private static String USER_URL ="http://userservice-dev:8080/api/user/{username}";

    private static RestTemplate restTemplate = new RestTemplate();

    /**
     * Method that uses the security context to get the ToiletUsers
     * username for the call to the UserService REST-api
     * @return toiletUser from UserService
     * @throws ToiletUserNotFoundException
     */
    public static ToiletUser fetchToiletUserByContext() throws ToiletUserNotFoundException {
        try {
            UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)
                    SecurityContextHolder.getContext().getAuthentication();
            String username = (String)  authentication.getPrincipal();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, TokenHolder.TOKEN);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<ToiletUser> response = restTemplate.exchange(USER_URL,
                    HttpMethod.GET,entity, ToiletUser.class,username);
            ToiletUser user = response.getBody();

            return user;
        } catch (RestClientException e) {
            throw new ToiletUserNotFoundException(e.getCause());
        }
    }

    /**
     * Method that takes the ToiletUsers username as a parameter
     * for the call to the UserService REST-api
     * @param username
     * @return toiletUser from UserService
     * @throws ToiletUserNotFoundException
     */
    public static ToiletUser fetchToiletUserByUsername(String username) throws ToiletUserNotFoundException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, TokenHolder.TOKEN);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<ToiletUser> response = restTemplate.exchange(USER_URL,
                    HttpMethod.GET,entity, ToiletUser.class,username);
            ToiletUser user = response.getBody();

            return user;
        } catch (RestClientException e) {
            throw new ToiletUserNotFoundException(e.getCause());
        }
    }


}
