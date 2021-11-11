package com.jasb.toiletproject.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Toilet {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
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
        return String.format("Toiler nr %d at lat %f long %f", id, latitude, longitude);
    }
}
