package com.example.geektrust.Entities;

import com.example.geektrust.Configs.Route;
import com.example.geektrust.Constants.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Train {
    private String name;
    // engine(s) + 1
    private final int minValidBogieCount;
    private List<Bogie> bogies;
    private HashMap<String, Integer> route;

    public Train(String name, int minBogieCount) {
        this.name = name;
        this.bogies = new ArrayList<>();
        this.minValidBogieCount = minBogieCount;
        route = Route.getInstance().getStationDistances(name);
    }

    public void addBogie(Bogie bogie) {
        this.bogies.add(bogie);
    }

    public void addBogies(List<Bogie> bogies) {
        this.bogies.addAll(bogies);
    }

    public int getMinValidBogieCount() {
        return minValidBogieCount;
    }

    public Bogie getBogie(int bogieCount) {
        return bogies.get(bogieCount);
    }
    
    public List<Bogie> getBogies() {
        return bogies;
    }

    public int getTotalBogiesCount() {
        return bogies.size();
    }

    public void removeAllBogies() {
        bogies.clear();
    }

    public Optional<Integer> getBogieDistanceFromSource(String stationCode) {
        return Optional.ofNullable(route.get(stationCode));
    }

    private boolean shouldDetachBogie(Bogie bogie, int destinationDistance) {
        return getBogieDistanceFromSource(bogie.getName()).map(
            distance -> distance < destinationDistance).orElse(false);
    }

    public void travelTo(String destination) {
        int destinationDistance = getBogieDistanceFromSource(destination).get();
        bogies.removeIf(bogie -> !bogie.isEngine() && shouldDetachBogie(bogie, destinationDistance));
    }

    public void addEngine() {
        for (int i = 1; i < minValidBogieCount; i++) {
            Bogie bogie = new Bogie(Constants.engineName, BogieType.ENGINE);
            bogies.add(0, bogie);;
        }    
    }

    public void removeEngine() {
        bogies.removeIf(e -> e.getName().equals(Constants.engineName));  
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(name);
        for (int i = 0; i < bogies.size(); i++) {
            result.append(" " + bogies.get(i));
        }
        return result.toString();
    }
}
