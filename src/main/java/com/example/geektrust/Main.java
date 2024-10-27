package com.example.geektrust;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.example.geektrust.Entities.Train;
import com.example.geektrust.Services.JourneyService;
import com.example.geektrust.Services.TrainService;

public class Main {
    private final String MERGE_STATION = "HYB";
    private String SPLIT_STATION = "BPL";
    private JourneyService journeyService;
    private TrainService trainService;

    public static void main(String[] args)  {
        try {
            // the file to be opened for reading
            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis); // file to be scanned
            String trainA = sc.nextLine();
            String trainB = sc.nextLine();
            new Main().run(trainA, trainB);
            sc.close(); // closes the scanner
        } 
        catch (IOException e) {
            System.out.println("Error: " + e);
        }
        // new Main().run(args[0]);
	}

    void run(String trainA, String trainB) {
        trainService = new TrainService();
        Train A = trainService.createTrain(new ArrayList<>(Arrays.asList(trainA.split(" "))));
        Train B = trainService.createTrain(new ArrayList<>(Arrays.asList(trainB.split(" "))));
        journeyService = new JourneyService(A, B, MERGE_STATION, SPLIT_STATION);
        journeyService.run();
    }
}
