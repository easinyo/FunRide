package com.dubinostech.rideshareapp.repository.Api.Raws;

import java.time.LocalDateTime;


public class PostRaw {
    String departure_city;
    String departure_address;
    String arrival_city;
    String arrival_address;
    Integer available_spot;
    Float fare;
    String departure_datetime;

    /**
     * Constructor
     * @param departure_city
     * @param departure_address
     * @param arrival_city
     * @param arrival_address
     * @param available_spot
     * @param fare
     * @param departure_datetime
     */
    public PostRaw(String departure_city, String departure_address, String arrival_city, String arrival_address, Integer available_spot, Float fare, String departure_datetime) {
        this.departure_city = departure_city;
        this.departure_address = departure_address;
        this.arrival_city = arrival_city;
        this.arrival_address = arrival_address;
        this.available_spot = available_spot;
        this.fare = fare;
        this.departure_datetime = departure_datetime;
    }
}
