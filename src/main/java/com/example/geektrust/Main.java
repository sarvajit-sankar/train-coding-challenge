package com.example.geektrust;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.example.geektrust.Services.TrainService;

public class Main {

    private String combinedSourceStation;
    private String combinedDestinationStation;
    private TrainService trainService;

    Main(String combinedSourceStation, String combinedDestinationStation) {
        this.combinedDestinationStation = combinedDestinationStation;
        this.combinedSourceStation = combinedSourceStation;
        this.trainService = new TrainService();
    }
    
    public static void main(String[] args)  {
        if (args.length != 1){
            throw new RuntimeException("Input file not given");
        }
        try {
            // the file to be opened for reading
            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis); // file to be scanned
            String trainA = sc.nextLine();
            String trainB = sc.nextLine();
            new Main("HYB", "BPL").run(trainA, trainB);
            sc.close(); // closes the scanner
        } 
        catch (IOException e) {
            System.out.println("Error: " + e);
        }
        // new Main().run(args[0]);
	}

    void run(String trainA, String trainB) {
        trainService.run(new ArrayList<>(Arrays.asList(trainA.split(" "))), 
        new ArrayList<>(Arrays.asList(trainB.split(" "))), 
        combinedSourceStation, combinedDestinationStation);
    }
}
