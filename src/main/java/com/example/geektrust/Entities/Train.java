package com.example.geektrust.Entities;

import com.example.geektrust.Configs.TrainConfiguration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Train {
    private String name;
    // engine + bogies
    private final int minValidBogieCount;

    private List<Bogie> bogies;
    private HashMap<String, Integer> bogieDistanceFromSource;

    public Train(String name, int minBogieCount) {
        setName(name);
        this.bogies = new ArrayList<>();
        this.minValidBogieCount = minBogieCount;
        bogieDistanceFromSource = TrainConfiguration.getStationDistances(name);
    }

    public void addBogie(Bogie bogie) {
        this.bogies.add(bogie);
    }

    private void setName(String name) {
        if (name.isEmpty() || name == null) {
            throw new RuntimeException("Train name is either null or Empty");
        }
        this.name = name;
    }

    public List<Bogie> getBogies() {
        return bogies;
    }

    public String getName() {
        return name;
    }

    public int getMinValidBogieCount() {
        return minValidBogieCount;
    }

    public void clearBogies() {
        bogies.clear();
    }

    public Optional<Integer> getBogieDistanceFromSource(String stationCode) {
        return Optional.ofNullable(bogieDistanceFromSource.get(stationCode));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(name);
        for (int i = 0; i < bogies.size(); i++) {
            result.append(" " + bogies.get(i).getName());
        }
        return result.toString();
    }
}
