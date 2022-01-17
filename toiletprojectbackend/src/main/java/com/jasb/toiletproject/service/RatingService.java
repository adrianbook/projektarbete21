package com.jasb.toiletproject.service;

import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;

public interface RatingService {
    Rating upsertRating(Toilet toilet, String userName, int rating);
}
