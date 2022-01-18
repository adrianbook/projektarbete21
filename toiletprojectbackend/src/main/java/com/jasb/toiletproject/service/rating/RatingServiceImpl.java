package com.jasb.toiletproject.service.rating;

import com.jasb.entities.Rating;
import com.jasb.entities.Role;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.repo.RatingRepository;
import com.jasb.toiletproject.repo.ToiletUserRepo;
import com.jasb.toiletproject.service.toilet.ToiletService;
import com.jasb.toiletproject.util.TokenHolder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service @Slf4j @RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {


    private final ToiletService toiletService;

    private final RatingRepository ratingDao;

    private final RestTemplate restTemplate;




    @Override
    public Rating addRating(int toiletId, int ratingVal, String notes) {
        Optional<Toilet> fetchedToilet = toiletService.getToiletById(toiletId);
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
        Rating rating = ratingDao.upsertRating( new Rating(fetchedToilet.get(), response.getBody(), ratingVal, notes));

        log.info("Adding rating for {}", rating.getId());
        return rating;
    }

}
