package entities;

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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "longitude", "latitude" }) })
public class Toilet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double longitude;
    private double latitude;
    public Toilet(){};

    public Toilet(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
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
