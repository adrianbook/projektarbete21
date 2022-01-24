package com.jasb.toiletproject.service.rating;

import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;

import java.util.List;
import java.util.Optional;

public interface RatingService {
    Optional<Rating> checkIfRatingExistForUserAndToilet(ToiletUser user, Toilet toilet);
    Rating addRating(Rating rating);
    double getUpdatedAvgRating(long toiletId);
    void deleteRatingByToiletId(long id);

    List<Rating> getAllRatingsForSpecificToilet(long toiletId);
}
