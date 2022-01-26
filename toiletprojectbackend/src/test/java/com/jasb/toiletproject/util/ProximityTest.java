package com.jasb.toiletproject.util;

import com.jasb.entities.Toilet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProximityTest {
/*    private static final Double longitude = 10.00000;
    private static final Double latitude = 12.00000;
    private static final Double minimumDistance = 0.00009;
    private static final List<Toilet> existingToilets = new ArrayList<>();

    @BeforeAll
    public static void addExistingToilets(){
        existingToilets.add(new Toilet(1, longitude, latitude, 5));
    }

    @Test
    public void testToiletAtSameLatLng() {
        Toilet toiletAtSameCoordinates = new Toilet(2, longitude, latitude, 1);
        assertTrue(Proximity.toClose(toiletAtSameCoordinates, existingToilets));
    }

    @Test
    public void testToiletTooClose() {
        Toilet toiletTooClose = new Toilet(3, longitude+0.00001, latitude+0.00001, 1);
        assertTrue(Proximity.toClose(toiletTooClose, existingToilets));
    }

    @Test
    public void testToiletNotTooClose() {
        Toilet toiletLongitudeNotTooClose = new Toilet(4, longitude+minimumDistance, latitude, 1);
        Toilet toiletLatitudeNotTooClose = new Toilet(4, longitude, latitude+minimumDistance, 1);
        assertFalse(Proximity.toClose(toiletLongitudeNotTooClose, existingToilets));
        assertFalse(Proximity.toClose(toiletLatitudeNotTooClose, existingToilets));
    }*/

}
