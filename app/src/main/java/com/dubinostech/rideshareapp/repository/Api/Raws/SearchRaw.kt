package com.dubinostech.rideshareapp.repository.Api.Raws

/**
 * @param departure
 * @param destination
 * @param date
 */

class SearchRaw(departure: String, destination: String, date: String) {

    private var departure_city: String? = departure
    private var arrival_city: String? = destination

}