
package com.jasb.toiletproject.repo;

import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class RatingRepositoryImpl {

    @PersistenceContext
    private EntityManager em;

    public Optional<Rating> findByToiletUserAndToilet(ToiletUser toiletUser, Toilet toilet) {
        Optional rating = (Optional) em.createQuery("select rating from Rating as rating where rating.toiletUser=:toiletUser and rating.toilet=:toilet")
                .setParameter("toiletUser", toiletUser)
                .setParameter("toilet", toilet)
                .getResultList()
                .stream()
                .findFirst();
        return rating;
    }

    public Rating upsertRating(Rating rating) {
        rating = em.merge(rating);
        em.flush();
        em.detach(rating);
        rating.getToiletUser().setPassword(null);
        rating.getToiletUser().setRoles(null);
        rating.getToiletUser().setEmail(null);
        return rating;
    }

    public double findAvgRating(long id) {
        Object avgRating = em.createQuery("select avg (r.rating) from Rating r where r.toilet.Id=:id")
                .setParameter("id", id)
                .getSingleResult();
        if (avgRating == null) {
            return 0.0;
        } else {
            return (double) avgRating;
        }
    }

    public List<Rating> findAllRatingsForToilet(long id) {
        List<Rating> ratings = em.createQuery("select r from Rating as r " +
                "where r.toilet.Id=:id")
                .setParameter("id", id)
                .getResultList();
        for (Rating r :
                ratings) {
            r.getToiletUser().setPassword(null);
            r.getToiletUser().setRoles(null);
            r.getToiletUser().setEmail(null);
        }
       return ratings;
    }
}

