package com.jasb.toiletproject.rest;

import com.jasb.entities.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * Helper class for REST-API
 * Written by JASB
 */
@AllArgsConstructor @NoArgsConstructor @Data
public class RatingList {
   private List<Rating> ratings;

   public RatingList(Optional<Rating> allRatingsForSpecificToilet) {

   }
}
