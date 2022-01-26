package com.jasb.toiletproject.repo;

import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * An implementation of JPA-Repository RatingRepository with some custom queries
 */
@Component
@Slf4j
@Transactional
public class RatingRepositoryImpl {
    /**
     * Injection of the entity manager for this implementation
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Method to find a rating by providing toilet user and toilet for the purpose of
     * implementing put protocol
     * @param toiletUser
     * @param toilet
     * @return rating if found
     */

    public Optional<Rating> findByToiletUserAndToilet(ToiletUser toiletUser, Toilet toilet) {
        Optional rating = (Optional) em.createQuery("select rating from Rating as rating where rating.toiletUser=:toiletUser and rating.toilet=:toilet")
                .setParameter("toiletUser", toiletUser)
                .setParameter("toilet", toilet)
                .getResultList()
                .stream()
                .findFirst();
        return rating;
    }

    /**
     * method for persisting a rating. Will update if rating exists and create a new
     * entry in the database if rating does not exist
     * @param rating
     * @return the persisted rating
     */
    public Rating upsertRating(Rating rating) {
        rating = em.merge(rating);
        em.flush();
        em.detach(rating);
        rating.getToiletUser().setPassword(null);
        rating.getToiletUser().setRoles(null);
        rating.getToiletUser().setEmail(null);
        return rating;
    }

    /**
     * Method to find average rating for a specific toilet by toilet id
     * @param id toiletId
     * @return the average rating returns 0.0 if no rating exists
     */
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

    /**
     * Method to find all ratings for a specific toilet
     * @param id toiletId
     * @return a list of all the ratings for the toilet
     */
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

    /**
     * A method to delete all ratings of a toilet. Is called before deleting a toilet
     * @param id toiletID
     */
    public void deleteRatingByToiletId(long id){
        em.createQuery("delete from  Rating as rating where rating.toilet.Id=:toiletId")
                .setParameter("toiletId", id)
                .executeUpdate();
    }
}