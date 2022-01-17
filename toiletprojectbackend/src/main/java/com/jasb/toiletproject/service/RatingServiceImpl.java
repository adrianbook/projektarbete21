package com.jasb.toiletproject.service;

import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.repo.RatingRepository;
import com.jasb.toiletproject.repo.ToiletRepository;
import com.jasb.toiletproject.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {
    @Autowired
    ToiletRepository toiletRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    RatingRepository ratingRepo;

    @Override
    public Rating upsertRating(Toilet toilet, String userName, int ratingVal) {
        Toilet fetchedToilet = toiletRepo.getById(toilet.getId());
        ToiletUser toilerUser = userRepo.findByUsername(userName);
        Rating rating = new Rating(fetchedToilet, toilerUser, ratingVal);
        System.out.println(rating);

        return ratingRepo.upsertRating(rating);
    }

}

