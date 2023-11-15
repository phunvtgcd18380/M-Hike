package com.example.m_hike;

public class ObservationModel {
    long id;
    String observation;
    String dateTime;


    public ObservationModel(long id, String observation, String dateTime) {
        this.observation = observation;
        this.id = id;
        this.dateTime = dateTime;
    }
}
