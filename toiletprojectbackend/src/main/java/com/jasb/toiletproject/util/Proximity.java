package com.jasb.toiletproject.util;

import com.jasb.entities.Toilet;

import java.util.List;

public class Proximity {
    public static boolean tooClose(Toilet toiletToCheck, List<Toilet> allTtoilets) {
        double minimumDistance = 0.00009;
        double startLat = toiletToCheck.getLatitude() - minimumDistance;
        double endLat = toiletToCheck.getLatitude() + minimumDistance;
        double startLong = toiletToCheck.getLongitude() - minimumDistance;
        double endLong = toiletToCheck.getLongitude() + minimumDistance;
        for (Toilet t :
                allTtoilets) {
            if (t.getLatitude() > startLat &&
                    t.getLatitude() < endLat &&
                    t.getLongitude() > startLong &&
                    t.getLongitude() < endLong) {
                return true;
            }
        }
        return false;
    }
}
