package com.jasb.toiletproject.service;

import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.repo.ToiletRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RatingServiceImpl implements RatingService {
    @Autowired
    ToiletRepository toiletRepo;

    @Override
    public Rating upsertRating(Toilet toilet, String userName, int rating) {
        toilet = toiletRepo.getById(toilet.getId());
        
    }

    @Override
    public Rating deleteRating(Rating ratingToDelete) {
        return null;
    }
}
