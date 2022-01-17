package com.jasb.toiletproject.repo;

import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
@Transactional
public class RatingRepositoryImpl {

    @PersistenceContext
    private EntityManager em;

    private Rating findByToiletUserAndToilet(ToiletUser toiletUser, Toilet toilet) {
        Rating rating = (Rating) em.createQuery("select rating from Rating as rating where rating.toiletUser=:toiletUser and rating.toilet=:toilet")
                .setParameter("toiletUser", toiletUser)
                .setParameter("toilet", toilet)
                .getSingleResult();
        return rating;
    }

    public Rating upsertRating(Rating rating) {
        Rating oldRating = findByToiletUserAndToilet(rating.getToiletUser(), rating.getToilet());
        if (oldRating != null) {
            rating.setId(oldRating.getId());
        }
        em.merge(rating);
        rating.getToiletUser().setPassword(null);
        return rating;
    }
}
