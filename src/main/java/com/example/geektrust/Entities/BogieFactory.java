package com.example.geektrust.Entities;

import com.example.geektrust.Configs.TrainConfiguration;

public class BogieFactory {
    public static Bogie getBogie(String bogieName) {
        if (!TrainConfiguration.getStationName(bogieName).isPresent()) {
            return new Engine(bogieName);
        }
        return new PassengerBogie(bogieName);
    }
}
