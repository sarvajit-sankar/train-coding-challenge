package com.example.geektrust.Services;

import java.util.List;

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
}
