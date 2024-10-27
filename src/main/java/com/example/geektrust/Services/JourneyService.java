package com.example.geektrust.Services;

import com.example.geektrust.Constants.Constants;
import com.example.geektrust.Entities.Bogie;
import com.example.geektrust.Entities.Train;

public class JourneyService {
    private final Train trainA;
    private final Train trainB;
    private final String mergePointStationCode;
    private final String splitPointStationCode;
    private TrainReporter trainReporter = new TrainReporter();

    public JourneyService(Train trainA, Train trainB, String mergePoint, String splitPoint) {
        this.trainA = trainA;
        this.trainB = trainB;
        this.mergePointStationCode = mergePoint;
        this.splitPointStationCode = splitPoint;
    }

    public void run() {
        trainA.travelTo(mergePointStationCode);
        trainReporter.reportTrainStatus(trainA, "ARRIVAL");
        trainB.travelTo(mergePointStationCode);
        trainReporter.reportTrainStatus(trainB, "ARRIVAL");
        // merge at combinedSourceStation
        Train trainAB = mergeTrains();
        if (trainAB.getTotalBogiesCount() < trainAB.getMinValidBogieCount()) {
            System.out.println("JOURNEY_ENDED");
        }
        else {
            trainReporter.reportTrainStatus(trainAB, "DEPARTURE");
            // travel from combinedSourceStation till combinedDestinationStation
            trainAB.travelTo(splitPointStationCode);
            // trainReporter.reportTrainStatus(trainAB, "ARRIVAL");
            // split at combinedDestinationStation
            splitTrain(trainAB);
            // trainReporter.reportTrainStatus(trainA, "DEPARTURE");
            // trainReporter.reportTrainStatus(trainB, "DEPARTURE");
        }
    }

    private Train mergeTrains() {
        Train AB = new Train("TRAIN_AB", Constants.MERGED_TRAIN_MIN_BOGIES);
        AB.addBogies(trainA.getBogies());
        AB.addBogies(trainB.getBogies());
        // remove the arrived station(source) from the merged list, as travellers have gotten down here
        AB.removeBogiesOfStation(mergePointStationCode);
        // remove engine
        AB.removeEngine();
        AB.sortBogiesInDescendingDistancesFromStation(mergePointStationCode);
        AB.addEngine();
        // clear A and B train bogies
        trainA.removeAllBogies();
        trainB.removeAllBogies();
        return AB;
    }

    private void splitTrain(Train AB) {
        AB.removeEngine();
        trainA.addEngine();
        trainB.addEngine();
        // remove the arrived station(source) from the merged list, as travellers have gotten down here
        AB.removeBogiesOfStation(splitPointStationCode);
        for (int i = 0; i < AB.getTotalBogiesCount(); i++) {
            Bogie bogie = AB.getBogie(i);
            if (trainA.getBogieDistanceFromSource(bogie.getName()).isPresent()) {
                trainA.addBogie(bogie);
            }
            else {
                trainB.addBogie(bogie);
            }
        }
    }
}
