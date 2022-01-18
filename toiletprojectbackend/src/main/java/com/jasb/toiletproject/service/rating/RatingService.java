package com.jasb.toiletproject.service.rating;

import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.exceptions.ToiletUserNotFoundException;

import java.util.Optional;

public interface RatingService {
    public Optional<Rating> checkIfRatingExistForUserAndToilet(ToiletUser user, Toilet toilet);
    public Rating addRating(Rating rating);
    public ToiletUser fetchToiletUser() throws ToiletUserNotFoundException;
}
