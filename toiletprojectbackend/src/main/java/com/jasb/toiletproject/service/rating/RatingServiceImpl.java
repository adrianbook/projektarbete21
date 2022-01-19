package com.jasb.toiletproject.service.rating;

import com.jasb.entities.Rating;
import com.jasb.entities.Role;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.exceptions.ToiletUserNotFoundException;
import com.jasb.toiletproject.repo.RatingRepository;
import com.jasb.toiletproject.repo.ToiletUserRepo;
import com.jasb.toiletproject.service.toilet.ToiletService;
import com.jasb.toiletproject.util.TokenHolder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service @Slf4j @RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {


    private final ToiletService toiletService;

    private final RatingRepository ratingDao;

    private final RestTemplate restTemplate;


    public Optional<Rating> checkIfRatingExistForUserAndToilet(ToiletUser user, Toilet toilet) {
        return ratingDao.findByToiletUserAndToilet(user, toilet);
    }

    @Override
    public Rating addRating(Rating rating) {
        //Optional<Toilet> fetchedToilet = toiletService.getToiletById(toilet.getId());
       // ToiletUser user = fetchToiletUser();
        Rating upsertedRating = ratingDao.upsertRating(rating);

        log.info("Adding rating for {}", upsertedRating.getId());
        return upsertedRating;
    }


}
