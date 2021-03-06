package com.jasb.toiletproject.rest;

import com.jasb.entities.Toilet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Helper class for toilet API to return more than one toilet
 * Written by JASB
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ToiletList {
    private List<Toilet> toilets;
}
