package com.example.m_hike;

public class MHike {
    int id;
    String name;
    String location;
    String dateOfHike;
    String parkingAvailable;
    String lengthOfHike;
    String levelOfDifficult;
    String description;

    public MHike(int id, String name, String location, String dateOfHike, String parkingAvailable, String lengthOfHike, String levelOfDifficult, String description) {
        this.name = name;
        this.id = id;
        this.location = location;
        this.dateOfHike = dateOfHike;
        this.parkingAvailable = parkingAvailable;
        this.lengthOfHike = lengthOfHike;
        this.levelOfDifficult = levelOfDifficult;
        this.description = description;
    }
}
