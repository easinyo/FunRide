package com.dubinostech.rideshareapp;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestMyTrip {

    private static final int TRIP_ID = -1;
    private static final int TRIP_STATUS = 1;
    private static final String COST_PER_STOP = "5.00;8.00";
    private static final int START_DATE = 934238908;
    private static final int END_DATE = 934238918;
    private static final String START_LOCATION = "Montreal";
    private static final String STOPS = "Ottawa;Toronto";


    myTripContent.TripItem item;

    @Before
    public void setup() {
        item = new myTripContent.TripItem(TRIP_ID, TRIP_STATUS, COST_PER_STOP, START_DATE, END_DATE, START_LOCATION, STOPS);
    }

    @Test
    public void testTripCreatedAndMapped() {
        myTripContent.addItem(item);
        assertEquals(myTripContent.ITEM_MAP.get(String.valueOf(TRIP_ID)).startLocation, START_LOCATION);
    }

    @Test
    public void testToString() {
        String result = item.toString(START_LOCATION, STOPS);
        System.out.println(result);
        assertEquals("Montreal to Ottawa, Toronto", result);
    }

    @Test
    public void testClear() {
        myTripContent.clear();
        assertTrue(myTripContent.ITEM_MAP.isEmpty());
    }
}
