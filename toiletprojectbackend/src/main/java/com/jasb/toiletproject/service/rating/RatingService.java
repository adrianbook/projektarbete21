package com.jasb.toiletproject.service.rating;

import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.exceptions.ToiletUserNotFoundException;

import java.util.Optional;

public interface RatingService {
    Optional<Rating> checkIfRatingExistForUserAndToilet(ToiletUser user, Toilet toilet);
    Rating addRating(Rating rating);
    double getUpdatedAvgRating(long toiletId);
}
