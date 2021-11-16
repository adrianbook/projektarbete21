package com.jasb.toiletproject.rest;

import com.jasb.toiletproject.domain.Toilet;

import java.util.List;

public class ToiletList {
    private List<Toilet> toilets;

    public ToiletList() {};

    public List<Toilet> getToilets() {
        return toilets;
    }

    public void setToilets(List<Toilet> toilets) {
        this.toilets = toilets;
    }

    public ToiletList(List<Toilet> toilets) {
        this.toilets = toilets;
    };

}
