package com.example.android.quakereport;

import java.util.Date;

/**
 * Created by gilho on 2/06/18.
 */

public class Earthquake {

    private String magnitute;
    private String city;
    private String eventDate;

    public Earthquake(String magnitute, String city, String eventDate) {
        this.magnitute = magnitute;
        this.city = city;
        this.eventDate = eventDate;
    }

    public String getMagnitute() {
        return magnitute;
    }

    public String getCity() {
        return city;
    }

    public String getEventDate() {
        return eventDate;
    }
}
