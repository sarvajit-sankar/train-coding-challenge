package com.example.geektrust.Entities;

// import com.example.geektrust.Configs.TrainConfiguration;

public class Bogie {
    // station code is bogieName
    private String name;
    private BogieType bogieType;

    public Bogie(String name, BogieType bogieType) {
        setName(name);
        this.bogieType = bogieType;
    }

    public boolean isEngine() {
        return bogieType.equals(BogieType.ENGINE);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        // if (!name.equals("ENGINE") && !TrainConfiguration.getStationName(name).isPresent()) {
        //     throw new RuntimeException("Incorrect bogie specified: " + name);
        // }
        this.name = name;
    }
}
