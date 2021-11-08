package com.jasb.toiletproject.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Toilet {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;
    private double longitude;
    private double latitude;

    public Toilet() {};
    public Toilet(Double longitude, Double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
