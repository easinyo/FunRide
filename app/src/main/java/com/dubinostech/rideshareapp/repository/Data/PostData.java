package com.dubinostech.rideshareapp.repository.Data;

import java.io.Serializable;

/**
 * Created by Emmanuel on January 25, 2020.
 * A Class that will store the data of PostFragment
 */

public class PostData implements Serializable{

    private int id;
    private double departureLongitude;
    private double departueLatitude;
    private double arrivalLongitude;
    private double arrivalLatitude;
    private double price;

    private int passengers;

    private String departureDate;

    private String departureCity;
    private String arrivalCity;

    /**
     * Post data contructor
     */
    public  PostData(double dLong, double dLat, double aLong, double  aLat, String departureCity, String arrivalCity, double price, int passengers, String dDate){

        this.departureLongitude = dLong;
        this.departueLatitude = dLat;
        this.arrivalLongitude = aLong;
        this.arrivalLatitude = aLat;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.passengers = passengers;
        this.departureDate = dDate;
        this.price = price;

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDepartureLongitude() {
        return departureLongitude;
    }

    public void setDepartureLongitude(double departureLongitude) {
        this.departureLongitude = departureLongitude;
    }

    public double getDepartueLatitude() {
        return departueLatitude;
    }

    public void setDepartueLatitude(double departueLatitude) {
        this.departueLatitude = departueLatitude;
    }

    public double getArrivalLongitude() {
        return arrivalLongitude;
    }

    public void setArrivalLongitude(double arrivalLongitude) {
        this.arrivalLongitude = arrivalLongitude;
    }

    public double getArrivalLatitude() {
        return arrivalLatitude;
    }

    public void setArrivalLatitude(double arrivalLatitude) {
        this.arrivalLatitude = arrivalLatitude;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

}
