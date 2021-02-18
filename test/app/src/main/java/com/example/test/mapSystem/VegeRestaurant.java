package com.example.test.mapSystem;

public class VegeRestaurant {
    private String name;
    private String lat;
    private String lng;
    private String distance;
    private String code;
    private String rating;
    private String location;
    private String open;

    public VegeRestaurant(String name, String lat, String lng, String distance, String code, String rating, String location, String open) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.distance = distance;
        this.code = code;
        this.rating = rating;
        this.location = location;
        this.open = open;
    }

    public String getName() {
        return name;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getDistance() {
        return distance;
    }

    public String getCode() {
        return code;
    }

    public String getRating() {
        return rating;
    }

    public String getLocation() {
        return location;
    }

    public String getOpen() {
        return open;
    }

}
