package com.jasb.toiletproject.rest;

import com.jasb.toiletproject.domain.Toilet;

import java.util.List;

public class ToiletList {
    private List<Toilet> toilets;

    public ToiletList() {};

    public ToiletList(List<Toilet> toilets) {
        this.toilets = toilets;
    };

}
