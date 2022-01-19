package com.jasb.toiletproject.service.toiletuser;

import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.exceptions.ToiletUserNotFoundException;
import com.jasb.toiletproject.util.TokenHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class ToiletUserServiceImpl implements ToiletUserService{

    private final RestTemplate restTemplate;

    @Nullable
    public ToiletUser fetchToiletUser() throws ToiletUserNotFoundException {
        try {
            UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)
                    SecurityContextHolder.getContext().getAuthentication();
            String username = (String) authentication.getPrincipal();
            String url = "http://userservice-dev:8080/api/user/{username}";
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, TokenHolder.TOKEN);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<ToiletUser> response = restTemplate.exchange(url,
                    HttpMethod.GET,entity, ToiletUser.class,username);
            ToiletUser user = response.getBody();

            return user;
        } catch (RestClientException e) {
            throw new ToiletUserNotFoundException(e.getCause());
        }
    }
}
