package com.example.geektrust.Entities;

import com.example.geektrust.Configs.TrainConfiguration;

public class Bogie {
    // station code is bogieName
    private String name;

    public Bogie(String name) {
        setName(name);;
        
    }

    private void setName(String name) {
        if (!name.equals("ENGINE") && !TrainConfiguration.getStationName(name).isPresent()) {
            throw new RuntimeException("Incorrect bogie specified: " + name);
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
