package com.jasb.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * JPA Entity representation of a Toilet in the database
 * Written by JASB
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "longitude", "latitude" }) })
public class Toilet implements Serializable {
    private static final long serialVersionUID = 7727182784975702210L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    private double longitude;
    private double latitude;

    private boolean cost;
    private boolean urinal;
    private boolean separateGenders;
    private boolean changingTable;
    private boolean shower;
    private boolean handicapFriendly;

    @Transient
    private double avgRating;

    public String toString() {
        return String.format("Toilet nr %d at lat %f long %f", Id, latitude,
                longitude);
    }
}