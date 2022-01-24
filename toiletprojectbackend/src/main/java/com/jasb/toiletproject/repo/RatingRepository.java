package com.jasb.toiletproject.repo;

import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating upsertRating(Rating rating);
    Optional<Rating> findByToiletUserAndToilet(ToiletUser toiletUser, Toilet toilet);
    double findAvgRating(long id);
    List<Rating> findAllRatingsForToilet(long id);
    void deleteRatingByToiletId(long id);
}


