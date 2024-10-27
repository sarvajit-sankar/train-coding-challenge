package com.example.geektrust.Entities;

// import com.example.geektrust.Configs.TrainConfiguration;

public class Bogie {
    // station code is bogieName
    private String name;
    private BogieType bogieType;
    public Bogie(String name, BogieType bogieType) {
        this.name = name;
        this.bogieType = bogieType;
    }

    public String getName() {
        return name;
    }

    public boolean isEngine() {
        return bogieType.equals(BogieType.ENGINE);
    }
}
