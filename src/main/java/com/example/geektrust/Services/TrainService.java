package com.example.geektrust.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.geektrust.Configs.TrainConfiguration;
import com.example.geektrust.Entities.Bogie;
import com.example.geektrust.Entities.BogieComparator;
import com.example.geektrust.Entities.BogieType;
import com.example.geektrust.Entities.Train;

public class TrainService {

    public Train createTrain(List<String> trainList, int minValidBogieCount) {
        Train train = new Train(trainList.get(0), minValidBogieCount);
        trainList.remove(0);
        createBogies(train, trainList);
        return train;
    }

    public void printTrain(Train train, String state) {
        System.out.println(state + "  " + train.toString());
    }

    private void createBogies(Train T, List<String> train) {
        for (int i = 0; i < train.size(); i++) {
            BogieType bogieType = BogieType.PASSENGER_CLASS;
            String bogieName = train.get(i);
            if (!TrainConfiguration.getStationName(bogieName).isPresent()) {
                bogieType = BogieType.ENGINE;
            }
            Bogie bogie = new Bogie(bogieName, bogieType);
            T.addBogie(bogie);
        }
    }

    private void removeBogiesOfCurrentStation(List<Bogie> bogies, String station) {
        bogies.removeIf(e -> e.getName().equals(station));
    }

    public Train merge(Train A, Train B, String source) {
        Train AB = new Train("TRAIN_AB", 3);
        addEnginesToMergedTrain(A, B, AB);
        List<Bogie> bogies = new ArrayList<>();
        bogies.addAll(A.getBogies());
        bogies.addAll(B.getBogies());
        // remove the arrived station(source) from the merged list, as travellers have gotten down here
        removeBogiesOfCurrentStation(bogies, source);
        Collections.sort(bogies, new BogieComparator(source));
        // add sorted bogies to AB which has engines
        AB.getBogies().addAll(bogies);
        // clear A and B train bogies
        A.clearBogies();
        B.clearBogies();
        return AB;
    }

    private void addEnginesToMergedTrain(Train A, Train B, Train AB) {
        List<Bogie> bogiesA = A.getBogies();
        AB.addBogie(bogiesA.get(0));
        bogiesA.remove(0);
        List<Bogie> bogiesB = B.getBogies();
        AB.addBogie(bogiesB.get(0));
        bogiesB.remove(0);
    }

    // private void removeEnginesFromMergedTrain(Train A, Train B, Train AB) {
    //     A.addBogie(AB.getBogies().get(0));
    //     AB.getBogies().remove(0);
    //     B.addBogie(AB.getBogies().get(0));
    //     AB.getBogies().remove(0);
    // }

    // public void split(Train A, Train B, Train AB, String source) {
    //     removeEnginesFromMergedTrain(A, B, AB);
    //     List<Bogie> bogiesAB = AB.getBogies();
    //     // remove the arrived station(source) from the merged list, as travellers have gotten down here
    //     removeBogiesOfCurrentStation(bogiesAB, source);
    //     for (int i = 0; i < bogiesAB.size(); i++) {
    //         Bogie bogie = bogiesAB.get(i);
    //         if (A.getBogieDistanceFromSource(bogie.getName()).isPresent()) {
    //             A.addBogie(bogie);
    //         }
    //         else {
    //             B.addBogie(bogie);
    //         }
    //     }
    // }
}
