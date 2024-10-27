package com.example.geektrust.Services;

import java.util.Iterator;
import java.util.List;

import com.example.geektrust.Entities.Bogie;
import com.example.geektrust.Entities.Train;

public class JourneyService {

    private TrainService trainService;

    public JourneyService(TrainService trainService) {
        this.trainService = trainService;
    }

    public void run(List<String> trainA, List<String> trainB, String combinedSourceStation, 
    String combinedDestinationStation) {
        Train A = trainService.createTrain(trainA, 2);
        Train B = trainService.createTrain(trainB, 2);
        travel(A, combinedSourceStation);
        trainService.printTrain(A, "ARRIVAL");
        travel(B, combinedSourceStation);
        trainService.printTrain(B, "ARRIVAL");
        // merge at combinedSourceStation

        Train AB = trainService.merge(A, B, combinedSourceStation);
        if (AB.getBogies().size() < AB.getMinValidBogieCount()) {
            System.out.println("JOURNEY_ENDED");
        }
        else {
            trainService.printTrain(AB, "DEPARTURE");
            // // travel from combinedSourceStation till combinedDestinationStation
            // travel(AB, combinedDestinationStation);
            // trainService.printTrain(AB, "ARRIVAL");
            // // split at combinedDestinationStation
            // trainService.split(A, B, AB, combinedDestinationStation);
            // trainService.printTrain(A, "DEPARTURE");
            // trainService.printTrain(B, "DEPARTURE");
        }
    }

    public void travel(Train train, String destination) {
        int destinationDistance = train.getBogieDistanceFromSource(destination).get();
        List<Bogie> bogies = train.getBogies();
        Iterator<Bogie> iterator = bogies.iterator();
        while (iterator.hasNext()) {
            Bogie bogie = iterator.next();
            // engines are to be ignored
            if (bogie.isEngine()) {
                continue;
            }
            // train may have bogies which aren't part of its route, which will get merged at destination
            if (train.getBogieDistanceFromSource(bogie.getName()).isPresent()) {
                if (train.getBogieDistanceFromSource(bogie.getName()).get() < destinationDistance) {
                    iterator.remove();
                }   
            }
        }
    }
}
