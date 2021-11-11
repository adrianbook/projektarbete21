package com.jasb.toiletproject.domain;


import lombok.Data;

@Data
public class Toilet {

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
