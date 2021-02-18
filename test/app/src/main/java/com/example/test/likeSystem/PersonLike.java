package com.example.test.likeSystem;

public class PersonLike {
    private String name;
    private String lat;
    private String lng;
    private String rating;
    private String location;
    private String open;
    private String phone;

    public PersonLike(String name, String lat, String lng, String rating, String location, String open, String phone) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.rating = rating;
        this.location = location;
        this.open = open;
        this.phone = phone;
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

    public String getRating() {
        return rating;
    }

    public String getLocation() {
        return location;
    }

    public String getOpen() {
        return open;
    }

    public String getPhone() {
        return getPhone();
    }
}
