package com.jasb.toiletproject.util;

import com.jasb.entities.Toilet;
import java.util.List;

/**
 * Util class to check if a toilet is to close to another toilet
 * before adding it to the database
 * Written by JASB
 */
public class Proximity {

    /**
     * Method to check if a toilet is too close to existing toilets
     * @param toiletToCheck
     * @param allTtoilets
     * @return true if it is too close
     */
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
