package com.jasb.toiletproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * JPA Entity representation of a Toilet in the database
 * Written by JASB
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "longitude", "latitude" }) })
public class Toilet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double longitude;
    private double latitude;

    public String toString() {
        return String.format("Toilet nr %d at lat %f long %f", id, latitude, longitude);
    }
}
