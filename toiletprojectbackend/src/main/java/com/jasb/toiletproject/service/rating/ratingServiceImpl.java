package com.jasb.toiletproject.service.rating;

import com.jasb.entities.Rating;
import com.jasb.entities.Role;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.repo.RatingRepository;
import com.jasb.toiletproject.service.toilet.ToiletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@Service @Slf4j @RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    @Autowired
    private final ToiletService toiletService;
    @Autowired
    RatingRepository ratingDao;
    @Autowired
    private final RestTemplate restTemplate;


    @Override
    public void addRating(long toiletId, Rating rating/*, ToiletUser
    visitor*/) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        System.out.println(username);
        ToiletUser toiletUser = restTemplate.getForObject("http://localhost" +
                ":8080" +
                "/api/user/{username}" , ToiletUser.class, username);
        System.out.println("the id:"+toiletUser.getId());
        log.info("Adding rating for {}", rating.getId());
        rating.setToiletUser(toiletUser);
        ratingDao.save(rating);
        toiletService.addRating(toiletId, rating/*, toiletUser*/);
    }
}
