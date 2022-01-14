package com.jasb.toiletproject.service.rating;

import com.jasb.entities.Rating;
import com.jasb.entities.Role;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.repo.RatingRepository;
import com.jasb.toiletproject.repo.ToiletUserRepo;
import com.jasb.toiletproject.service.toilet.ToiletService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD:toiletprojectbackend/src/main/java/com/jasb/toiletproject/service/rating/RatingServiceImpl.java
import org.springframework.boot.web.server.Http2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
=======
import org.springframework.http.*;
>>>>>>> e05bd74c24a14a1a6cdcaeefcb240f0ac055f3cb:toiletprojectbackend/src/main/java/com/jasb/toiletproject/service/rating/ratingServiceImpl.java
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
        System.out.println(authentication.toString());
        String username = (String) authentication.getPrincipal();

        String url = "http://userservice-dev:8080/api/user/{username}";
        HttpHeaders headers = new HttpHeaders();

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
