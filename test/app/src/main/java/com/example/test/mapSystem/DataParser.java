package com.example.test.mapSystem;

import android.util.Log;

import com.example.test.listSystem.CalcDistance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    double curLat = MapsActivity.currentLocate.latitude;
    double curLon = MapsActivity.currentLocate.longitude;

    public List<HashMap<String, String>> parse(String jsonData) {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        //read data from URL
        try {
            Log.d("Places", "parse");
            jsonObject = new JSONObject((String) jsonData);
            //array of jsonObject [{}]
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            Log.d("Places", "parse error");
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray) {
        int placesCount = jsonArray.length(); //all the restaurants are stored in a json array  parse method
        List<HashMap<String, String>> placesList = new ArrayList<>();
        HashMap<String, String> placeMap = null;

        for (int i = 0; i < placesCount; i++) {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);
                Log.d("Places", "Adding places");

            } catch (JSONException e) {
                Log.d("Places", "Error in Adding places");
                e.printStackTrace();
            }
            //System.out.println();
        }
        return placesList;
    }

    private HashMap<String, String> getPlace(JSONObject googlePlaceJson) { // iteration getPlace
        HashMap<String, String> googlePlaceMap = new HashMap<String, String>();
        //first String -- key, second String -- value, one key--multiple values
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String resLatitude = "";
        String resLongitude = "";
        String distance = "";
        String rating = "";
        String code = "";
        String openTime = "";

        Log.d("getPlace", "Entered");

        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            googlePlaceMap.put("place_name", placeName);
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity");
            }
            googlePlaceMap.put("vicinity", vicinity);
            //if(!googlePlaceJson.isNull("loca"))

            resLatitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            resLongitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            googlePlaceMap.put("lat", resLatitude);
            googlePlaceMap.put("lng", resLongitude);
            CalcDistance calculator = new CalcDistance();
            double resLatitudeNum = Double.valueOf(resLatitude);
            double resLongitudeNum = Double.valueOf(resLongitude);
            distance = calculator.CalculationByDistance(curLat, curLon, resLatitudeNum, resLongitudeNum);
            googlePlaceMap.put("distance", distance);

            //rating = googlePlaceJson.getString("rating");
            if (!googlePlaceJson.isNull("rating")) {
                rating = googlePlaceJson.getString("rating");
            }
            googlePlaceMap.put("rating", rating);

            if (!googlePlaceJson.getJSONObject("plus_code").isNull("code")) {
                code = googlePlaceJson.getJSONObject("plus_code").getString("compound_code");
            }
            googlePlaceMap.put("code", code);

            if (!googlePlaceJson.getJSONObject("opening_hours").isNull("open_now")) {
                openTime = googlePlaceJson.getJSONObject("opening_hours").getString("open_now");
                if (openTime.equalsIgnoreCase("true")) {
                    openTime = "Open";
                } else {
                    openTime = "Closed";
                }
            }
            googlePlaceMap.put("openTime", openTime);


            Log.d("getPlace", "Putting Places");
        } catch (JSONException e) {
            Log.d("getPlace", "Error");
            e.printStackTrace();
        }
        return googlePlaceMap;
    }
}