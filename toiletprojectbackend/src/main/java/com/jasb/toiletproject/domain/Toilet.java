package com.jasb.toiletproject.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data public class Toilet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double longitude;
    private double latitude;

    public Toilet() {};

    public Toilet(long id, Double longitude, Double latitude){
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public String toString() {
        return String.format("Toilet nr %d at lat %f long %f", id, latitude, longitude);
    }
}
