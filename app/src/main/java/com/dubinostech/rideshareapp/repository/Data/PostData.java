package com.dubinostech.rideshareapp.repository.Data;

import java.io.Serializable;

/**
 * Created by Emmanuel on January 25, 2020.
 * A Class that will store the data of PostFragment
 */

public class PostData implements Serializable{

    private String departure_city;
    private String departure_address;
    private String arrival_city;
    private String arrival_address;

    private float fare;
    private int available_spot;
    private String departure_datetime;

    /**
     * Post data contructor
     */
    public PostData(String departure_city, String departure_address, String arrival_city, String arrival_address, float fare, int available_spot, String departure_datetime) {
        this.departure_city = departure_city;
        this.departure_address = departure_address;
        this.arrival_city = arrival_city;
        this.arrival_address = arrival_address;
        this.fare = fare;
        this.available_spot = available_spot;
        this.departure_datetime = departure_datetime;
    }

    public String getDeparture_city() {
        return departure_city;
    }

    public void setDeparture_city(String departure_city) {
        this.departure_city = departure_city;
    }

    public String getDeparture_address() {
        return departure_address;
    }

    public void setDeparture_address(String departure_address) {
        this.departure_address = departure_address;
    }

    public String getArrival_city() {
        return arrival_city;
    }

    public void setArrival_city(String arrival_city) {
        this.arrival_city = arrival_city;
    }

    public String getArrival_address() {
        return arrival_address;
    }

    public void setArrival_address(String arrival_address) {
        this.arrival_address = arrival_address;
    }

    public float getFare() {
        return fare;
    }

    public void setFare(float fare) {
        this.fare = fare;
    }

    public int getAvailable_spot() {
        return available_spot;
    }

    public void setAvailable_spot(int available_spot) {
        this.available_spot = available_spot;
    }

    public String getDeparture_datetime() {
        return departure_datetime;
    }

    public void setDeparture_datetime(String departure_datetime) {
        this.departure_datetime = departure_datetime;
    }
}
