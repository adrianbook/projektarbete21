package com.jasb.toiletproject.service.toilet;

import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;

import java.util.List;
import java.util.Optional;

public interface ToiletService {
    /*Optional<Toilet> getAvgRating(long id);*/

    List<Toilet> getAllToilets();
    Toilet addToilet(Toilet toilet) throws ToCloseToAnotherToiletException;
    Optional<Toilet> getToiletById(long id);
    /*public void addRating(Long toiletId, Rating rating*//*, String username*//*);*/
}
