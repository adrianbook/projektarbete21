package com.jasb.toiletproject.service.rating;

import com.jasb.entities.Rating;
import com.jasb.entities.Role;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.repo.RatingRepository;
import com.jasb.toiletproject.repo.ToiletUserRepo;
import com.jasb.toiletproject.service.toilet.ToiletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Http2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Service @Slf4j
public class RatingServiceImpl implements RatingService {


    private final ToiletService toiletService;

    RatingRepository ratingDao;

    ToiletUserRepo toiletUserRepo;
    @Autowired
    public RatingServiceImpl(@Lazy ToiletService toiletService, RatingRepository ratingDao, ToiletUserRepo toiletUserRepo) {
        this.toiletService = toiletService;
        this.ratingDao = ratingDao;
        this.toiletUserRepo = toiletUserRepo;
    }





    @Override
    public void addRating(long toiletId, Rating rating/*, ToiletUser
    visitor*/) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        System.out.println(username);
        ToiletUser toiletUser =toiletUserRepo.findToiletUserByUsername(username);
        System.out.println("the id:"+toiletUser.getId());
        log.info("Adding rating for {}", rating.getId());
        rating.setToiletUser(toiletUser);
        ratingDao.save(rating);
        toiletService.addRating(toiletId, rating/*, toiletUser*/);
    }

}
