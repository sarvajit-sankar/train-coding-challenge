package com.example.geektrust.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.example.geektrust.Configs.TrainConfiguration;
import com.example.geektrust.Entities.Bogie;
import com.example.geektrust.Entities.Train;

public class TrainService {

    public void run(List<String> trainA, List<String> trainB, String combinedSourceStation, 
    String combinedDestinationStation) {
        Train A = new Train(trainA.get(0));
        trainA.remove(0);
        Train B = new Train(trainB.get(0));
        trainB.remove(0);
        // attach bogies before the train begins travelling
        attachBogies(A, trainA);
        attachBogies(B, trainB);
        // travel from respective sources to combinedSourceStation
        travel(A, combinedSourceStation);
        printTrain(A, "ARRIVAL");
        travel(B, combinedSourceStation);
        printTrain(B, "ARRIVAL");
        // merge at combinedSourceStation
        Train AB = merge(A, B, combinedSourceStation);
        printTrain(AB, "DEPARTURE");

        // travel from combinedSourceStation till combinedDestinationStation
        travel(AB, combinedDestinationStation);
        // printTrain(AB, "ARRIVAL");
        // split at combinedDestinationStation
        split(A, B, AB);
        // printTrain(A, "DEPARTURE");
        // printTrain(B, "DEPARTURE");
    }

    private void removeEnginesFromAB(Train A, Train B, Train AB) {
        A.addBogie(AB.getBogies().get(0));
        AB.getBogies().remove(0);
        B.addBogie(AB.getBogies().get(0));
        AB.getBogies().remove(0);
    }

    private void split(Train A, Train B, Train AB) {
        removeEnginesFromAB(A, B, AB);
        List<Bogie> bogiesAB = AB.getBogies();
        for (int i = 0; i < bogiesAB.size(); i++) {
            Bogie bogie = bogiesAB.get(i);
            if (A.getBogieDistanceFromSource(bogie.getName()).isPresent()) {
                A.addBogie(bogie);
            }
            else {
                B.addBogie(bogie);
            }
        }
    }

    private void printTrain(Train train, String state) {
        List<Bogie> bogies = train.getBogies();
        // if the combined train is empty i.e both the trains are empty
        if (bogies.size() == 2 && bogies.get(1).getName().equals("ENGINE")) {
            System.out.println("JOURNEY_ENDED");
        }
        System.out.println(state + "  " + train.toString());
    }

    private void attachBogies(Train T, List<String> train) {
        for (int i = 0; i < train.size(); i++) {
            Bogie bogie = new Bogie(train.get(i));
            T.addBogie(bogie);
        }
    }

    private void travel(Train train, String destination) {
        int destinationDistance = train.getBogieDistanceFromSource(destination).get();
        List<Bogie> bogies = train.getBogies();
        Iterator<Bogie> iterator = bogies.iterator();
        while (iterator.hasNext()) {
            Bogie bogie = iterator.next();
            // engines are to be ignored
            if (bogie.getName().equals("ENGINE")) {
                continue;
            }
            // train may have bogies which aren't part of its route, which will get merged at destination
            if (train.getBogieDistanceFromSource(bogie.getName()).isPresent()) {
                if (train.getBogieDistanceFromSource(bogie.getName()).get() <= destinationDistance) {
                    iterator.remove();
                }   
            }
        }
    }

    private Train merge(Train A, Train B, String source) {
        Train AB = new Train("TRAIN_AB");
        addEnginesToAB(A, B, AB);

        List<Bogie> bogies = new ArrayList<>();
        bogies.addAll(A.getBogies());
        bogies.addAll(B.getBogies());
        sortBogies(bogies, source);
        // add sorted bogies to AB which has engines
        AB.getBogies().addAll(bogies);
        // clear A and B train bogies
        A.clearBogies();
        B.clearBogies();
        return AB;
    }

    private void sortBogies(List<Bogie> bogies, String source) {
        Collections.sort(bogies, Comparator.comparing(bogie -> 
        TrainConfiguration.getStationDistanceFromGivenSource(bogie.getName(), source), Comparator.reverseOrder()));
    }

    private void addEnginesToAB(Train A, Train B, Train AB) {
        List<Bogie> bogiesA = A.getBogies();
        AB.addBogie(bogiesA.get(0));
        bogiesA.remove(0);
        List<Bogie> bogiesB = B.getBogies();
        AB.addBogie(bogiesB.get(0));
        bogiesB.remove(0);
    }

}
