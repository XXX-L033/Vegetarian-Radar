package com.example.test.listSystem;

import android.util.Log;

public class CalcDistance {
    public String CalculationByDistance(double curLat, double curLon, double resLat, double resLon) {
        int Radius = 6371;// radius of earth in Km
        double dLat = Math.toRadians(resLat - curLat); //change angel to radians
        double dLon = Math.toRadians(resLon - curLon);
        double distance = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(curLat)) * Math.cos(Math.toRadians(resLat)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(distance));
        double km = Radius * c;
        String finalResult1 = String.format("%.2f", km);

        String finalResult = finalResult1 + " km";
        Log.i("Radius Value", "" + " KM   " + finalResult + " m");

        return finalResult;
    }
}
