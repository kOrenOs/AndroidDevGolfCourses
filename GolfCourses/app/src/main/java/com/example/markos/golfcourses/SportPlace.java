package com.example.markos.golfcourses;

/**
 * Created by Markos on 5. 11. 2016.
 */

public class SportPlace {
    private String type;
    private double latitude;
    private double longitude;
    private String field;
    private String address;
    private String phoneNumber;
    private String email;
    private String web;
    private String pricturePath;
    private String description;

    public SportPlace(String type, double latitude, double longitude, String field, String address, String phoneNumber, String email, String web, String pricturePath, String description) {
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.field = field;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.web = web;
        this.pricturePath = pricturePath;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getField() {
        return field;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getWeb() {
        return web;
    }

    public String getPricturePath() {
        return pricturePath;
    }

    public String getDescription() {
        return description;
    }
}
