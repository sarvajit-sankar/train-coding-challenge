package com.example.geektrust.Services;

import com.example.geektrust.Entities.Train;

public class TrainReporter {
    public void reportTrainStatus(Train train, String state) {
        System.out.println(state + "  " + train);
    }

    public void reportJourneyEnd() {
        System.out.println("JOURNEY_ENDED");
    }
}
