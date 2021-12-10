package com.jasb.toiletproject.rest;

import entities.Toilet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Helper class for toilet API to return more than one toilet
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ToiletList {
    private List<Toilet> toilets;
}
