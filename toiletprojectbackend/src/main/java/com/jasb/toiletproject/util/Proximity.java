package com.jasb.toiletproject.util;

import com.jasb.entities.Toilet;

import java.util.List;

public class Proximity {
    public static boolean toClose(Toilet toiletToCheck, List<Toilet> allTtoilets) {
        double startLat = toiletToCheck.getLatitude() - 0.00009;
        double endLat = toiletToCheck.getLatitude() + 0.00009;
        double startLong = toiletToCheck.getLongitude() - 0.00009;
        double endLong = toiletToCheck.getLongitude() + 0.00009;
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
