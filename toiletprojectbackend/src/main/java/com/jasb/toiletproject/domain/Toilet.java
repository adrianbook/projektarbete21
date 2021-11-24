package com.jasb.toiletproject.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String toString() {
        return String.format("Toilet nr %d at lat %f long %f", id, latitude, longitude);
    }
}
