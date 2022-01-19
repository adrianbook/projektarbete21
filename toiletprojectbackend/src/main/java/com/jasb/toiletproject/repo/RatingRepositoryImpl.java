package com.jasb.toiletproject.repo;

import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
        /* System.out.println("!!!!!!!!!!!!!!RATINGID!!!!!!!!!!!!!!!!!!!
        "+rating.getId());
        Rating oldRating = findByToiletUserAndToilet(rating.getToiletUser(), rating.getToilet());
        if (oldRating != null) {
            rating.setId(oldRating.getId());
        } */
        rating = em.merge(rating);
        rating.getToiletUser().setPassword(null);
        rating.getToiletUser().setRoles(null);
        return rating;
    }

    public double getAvgRating(long id) {
        Object avgRating = em.createQuery("select avg (r.rating) from Rating r where r.toilet.Id=:id")
                .setParameter("id", id)
                .getSingleResult();
        if (avgRating == null) {
            return 0.0;
        } else {
            return (double) avgRating;
        }
    }
}
