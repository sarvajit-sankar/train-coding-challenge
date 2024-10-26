package com.example.geektrust.Entities;

// import com.example.geektrust.Configs.TrainConfiguration;

public abstract class Bogie {
    // station code is bogieName
    protected String name;

    public Bogie(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean isEngine();
}
