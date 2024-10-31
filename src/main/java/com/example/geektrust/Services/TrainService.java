package com.example.geektrust.Services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.geektrust.Configs.Route;
import com.example.geektrust.Constants.Constants;
import com.example.geektrust.Entities.Bogie;
import com.example.geektrust.Entities.BogieType;
import com.example.geektrust.Entities.Train;

public class TrainService {

    public Train createTrain(List<String> trainList) {
        Train train = new Train(trainList.get(0), Constants.SINGLE_TRAIN_MIN_BOGIES);
        trainList.remove(0);
        createBogies(train, trainList);
        return train;
    }

    private void createBogies(Train T, List<String> train) {
        T.addEngine();
        for (int i = T.getMinValidBogieCount() - 1; i < train.size(); i++) {
            BogieType bogieType = BogieType.PASSENGER_CLASS;
            String bogieName = train.get(i);
            Bogie bogie = new Bogie(bogieName, bogieType);
            T.addBogie(bogie);
        }
    }

    public void removeBogiesOfStation(Train train, String station) {
        List<Bogie> bogies = train.getBogies();
        bogies.removeIf(e -> e.getName().equals(station));
    }

    public void sortBogiesInDescendingDistancesFromStation(Train train, String station) {
        Route route = Route.getInstance();
        List<Bogie> bogies = train.getBogies();
        Collections.sort(bogies, Comparator.comparing(bogie -> 
        route.getStationDistanceFromGivenSource(bogie.getName(), station), Comparator.reverseOrder()));
    }

    public void reportTrainStatus(Train train, String state) {
        System.out.println(state + "  " + train);
    }

    public void reportJourneyEnd() {
        System.out.println("JOURNEY_ENDED");
    }
}
