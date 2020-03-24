package com.dubinostech.rideshareapp;


import com.dubinostech.rideshareapp.ui.fragment.HomeFragment;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestSearchTrip {

    HomeFragment activity = new HomeFragment();

    private static final int TRIP_ID = -1;
    private static final int TRIP_STATUS = 1;
    private static final String COST_PER_STOP = "5.00;8.00";
    private static final int START_DATE = 934238908;
    private static final int END_DATE = 934238918;
    private static final String START_LOCATION = "Montreal";
    private static final String STOPS = "Ottawa;Toronto";
    private static final String VEHICLE_TYPE = "SUV";
    private static final String VEHICLE_COLOUR = "Blue";
    private static final String VEHICLE_MODEL = "Ford Explorer";
    private static final String COST1 = "1.2";
    private static final String BADCOST = "$1.4";
    private static final String DATE = "01-02-2017";
    private static final String DATE_INVALID = "01/02/2017";
    private static final String TIME = "02:30";
    private static final String TIME_INVALID = "02-30:30";
    private static final long TIME_STAMP_MILLI = 1483324200000L;
    public static final String DATE_TIME_INIT = "Click to Set";


    searchResultContent.SearchResultItem item;

    @Before
    public void setup() {
        item = new searchResultContent.SearchResultItem(TRIP_ID, TRIP_STATUS, COST_PER_STOP, START_DATE, END_DATE, START_LOCATION, STOPS, VEHICLE_TYPE, VEHICLE_MODEL, VEHICLE_COLOUR);
    }

    @Test
    public void testTripCreatedAndMapped() {
        searchResultContent.addItem(item);
        assertEquals(searchResultContent.ITEM_MAP.get(String.valueOf(TRIP_ID)).startLocation, START_LOCATION);
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

    @Test
    public void testCheckCost() {
        double result = activity.checkCost(COST1);
        assertEquals(COST1, String.valueOf(result));
    }

    @Test
    public void testCheckCostNotEntered() {
        double result = activity.checkCost("");
        assertEquals(-1.0, result, .001);
    }

    @Test
    public void testCheckCostFailure() {
        double result = activity.checkCost(BADCOST);
        assertEquals(-1.0, result, .001);
    }

    @Test
    public void testGetUnixTimeStamp() {
        long stamp = activity.getUnixStamp(DATE, TIME);
        assertEquals(TIME_STAMP_MILLI, stamp);
    }

    @Test
    public void testGetUnixTimeStampFailure1() {
        long stamp = activity.getUnixStamp(DATE_INVALID, TIME);
        assertEquals(-1, stamp);
    }

    @Test
    public void testGetUnixTimeStampFailure2() {
        long stamp = activity.getUnixStamp(DATE, TIME_INVALID);
        assertEquals(-1, stamp);
    }

    @Test
    public void testGetUnixTimeStampNotEntered() {
        long stamp = activity.getUnixStamp(DATE_TIME_INIT, TIME);
        assertEquals(-1, stamp);
    }

    @Test
    public void testGetUnixTimeStampNotEntered2() {
        long stamp = activity.getUnixStamp(DATE, DATE_TIME_INIT);
        assertEquals(-1, stamp);
    }
}
