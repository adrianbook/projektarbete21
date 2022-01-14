package com.jasb.toiletproject.service.rating;

import com.jasb.entities.Rating;
import com.jasb.entities.ToiletUser;

public interface RatingService {
    public void addRating(long toiletId, Rating rating/*, ToiletUser
    toiletUser*/);
}
