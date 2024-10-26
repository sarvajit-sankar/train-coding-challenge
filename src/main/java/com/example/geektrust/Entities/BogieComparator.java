package com.example.geektrust.Entities;

import java.util.Comparator;

import com.example.geektrust.Configs.TrainConfiguration;

public class BogieComparator implements Comparator<Bogie> {
    private String source;
    
    public BogieComparator(String source) {
        this.source = source;
    }

    @Override
    public int compare(Bogie a, Bogie b) {
        // TODO Auto-generated method stub
        int distanceB = TrainConfiguration.getStationDistanceFromGivenSource(b.getName(), source);
        int distanceA = TrainConfiguration.getStationDistanceFromGivenSource(a.getName(), source);
        return distanceB - distanceA;
    }
    
}
