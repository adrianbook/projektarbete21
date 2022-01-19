package com.jasb.toiletproject.service.rating;

import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.repo.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service @Slf4j @RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingDao;


    public Optional<Rating> checkIfRatingExistForUserAndToilet(ToiletUser user, Toilet toilet) {
        return ratingDao.findByToiletUserAndToilet(user, toilet);
    }

    @Override
    public Rating addRating(Rating rating) {
        Rating upsertedRating = ratingDao.upsertRating(rating);
        log.info("Adding rating for {}", upsertedRating.getId());
        return upsertedRating;
    }

}
