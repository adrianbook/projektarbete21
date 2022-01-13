package com.jasb.toiletproject.service.rating;

import com.jasb.entities.Rating;
import com.jasb.entities.Role;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.repo.RatingRepository;
import com.jasb.toiletproject.service.toilet.ToiletService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.print.attribute.standard.Media;
import java.util.Collection;

@Service @Slf4j @RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {


    private final ToiletService toiletService;

    private final RatingRepository ratingDao;

    private final RestTemplate restTemplate;


    @Override
    public void addRating(long toiletId, Rating rating) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();

        /*ToiletUser toiletUser = restTemplate.getForObject("http://localhost" +
                ":8080/api/user/{username}" , ToiletUser.class, username);
        System.out.println("the id:"+toiletUser.getId());*/
        /*ResponseEntity<ToiletUser>  entity = restTemplate.getForEntity("http" +
                "://localhost:8080/api/user/{username}", ToiletUser.class,
                username);*/
        /*System.out.println("status code: " + entity.getStatusCode());
        System.out.println("The name: " + entity.getBody().getName());*/

        String username = (String) authentication.getPrincipal();
        String url = "http://userservice-dev:8080/api/user/{username}";
        //String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
        //".eyJzdWIiOiJhcGEiLCJyb2xlcyI6WyJST0xFX0FQUFVTRVIiXSwiaXNzIjoiL2xvZ2luIiwiZXhwIjoxNjQyMTcxMzQ5fQ.SQmoanm8kXWqYvqYD0L6ATdllzwnAeFsQtLF4pOmUuc";
        HttpHeaders headers = new HttpHeaders();
        //headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ToiletUser> response = restTemplate.exchange(url,
                HttpMethod.GET,entity, ToiletUser.class,username);
        System.out.println(response.getBody());

        log.info("Adding rating for {}", rating.getId());
        rating.setToiletUser(response.getBody());
        ratingDao.save(rating);
        toiletService.addRating(toiletId, rating);
    }
}
