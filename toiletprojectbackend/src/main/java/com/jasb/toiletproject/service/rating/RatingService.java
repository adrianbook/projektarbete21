package com.jasb.toiletproject.service.rating;

import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;

public interface RatingService {
    public Rating addRating(Toilet toilet, int rating, String notes);
}
