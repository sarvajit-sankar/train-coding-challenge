package com.example.geektrust.Services;

import com.example.geektrust.Constants.Constants;
import com.example.geektrust.Entities.Bogie;
import com.example.geektrust.Entities.Train;

public class JourneyService {
    private final Train trainA;
    private final Train trainB;
    private final String mergePoint;
    private final String splitPoint;
    private TrainReporter trainReporter = new TrainReporter();

    public JourneyService(Train trainA, Train trainB, String mergePoint, String splitPoint) {
        this.trainA = trainA;
        this.trainB = trainB;
        this.mergePoint = mergePoint;
        this.splitPoint = splitPoint;
    }

    public void run() {
        arriveAtMergePoint();
        Train mergedTrain = performTrainMerge();
        if (mergedTrain.getTotalBogiesCount() < mergedTrain.getMinValidBogieCount()) {
            trainReporter.reportJourneyEnd();
            return;
        }
        trainReporter.reportTrainStatus(mergedTrain, Constants.DEPARTURE);
        completeMergedJourney(mergedTrain);
    }

    private void arriveAtMergePoint() {
        trainA.travelTo(mergePoint);
        trainReporter.reportTrainStatus(trainA, Constants.ARRIVAL);
        trainB.travelTo(mergePoint);
        trainReporter.reportTrainStatus(trainB, Constants.ARRIVAL);
    }

    private Train performTrainMerge() {
        Train mergedTrain = new Train("TRAIN_AB", Constants.MERGED_TRAIN_MIN_BOGIES);
        attachBogiesToMergedTrain(mergedTrain);
        prepareMergedTrain(mergedTrain);
        return mergedTrain;
    }

    private void attachBogiesToMergedTrain(Train mergedTrain) {
        mergedTrain.addBogies(trainA.getBogies());
        mergedTrain.addBogies(trainB.getBogies());
        trainA.removeAllBogies();
        trainB.removeAllBogies();
    }

    private void prepareMergedTrain(Train mergedTrain) {
        // remove the arrived station(source) from the merged list, as travellers have gotten down here
        mergedTrain.removeBogiesOfStation(mergePoint);
        // remove engine
        mergedTrain.removeEngine();
        mergedTrain.sortBogiesInDescendingDistancesFromStation(mergePoint);
        mergedTrain.addEngine();
    }

    private void completeMergedJourney(Train mergedTrain) {
        mergedTrain.travelTo(splitPoint);
        splitTrain(mergedTrain);

    }

    private void splitTrain(Train mergedTrain) {
        mergedTrain.removeEngine();
        trainA.addEngine();
        trainB.addEngine();
        splitBogiesOfMergedTrain(mergedTrain);
    }

    private void splitBogiesOfMergedTrain(Train mergedTrain) {
        // remove the arrived station(source) from the merged list, as travellers have gotten down here
        mergedTrain.removeBogiesOfStation(splitPoint);
        for (int i = 0; i < mergedTrain.getTotalBogiesCount(); i++) {
            Bogie bogie = mergedTrain.getBogie(i);
            if (trainA.getBogieDistanceFromSource(bogie.getName()).isPresent()) {
                trainA.addBogie(bogie);
            }
            else {
                trainB.addBogie(bogie);
            }
        }
    }
}
