package com.jasb.toiletproject.service.rating;

import com.jasb.entities.Rating;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.repo.RatingRepository;
import com.jasb.toiletproject.service.toilet.ToiletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service @Slf4j @RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    @Autowired
    private final ToiletService toiletService;
    @Autowired
    RatingRepository ratingDao;

    @Override
    public void addRating(long toiletId, Rating rating/*, ToiletUser
    visitor*/) {
        log.info("Adding rating for {}", rating.getId());
        ratingDao.save(rating);
        toiletService.addRating(toiletId, rating);
    }
}
