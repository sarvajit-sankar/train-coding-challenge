package com.example.geektrust;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.example.geektrust.Services.TrainService;

public class Main {

    String combinedSourceStation = "HYB";
    String combinedDestinationStation = "BPL";
    TrainService trainService = new TrainService();
    public static void main(String[] args)  {
        if (args.length != 1){
            throw new RuntimeException("Input file not given");
        }
        new Main().run(args[0]);
	}

    void run(String inputFile) {
        try {
            // the file to be opened for reading
            FileInputStream fis = new FileInputStream(inputFile);
            Scanner sc = new Scanner(fis); // file to be scanned
            String[] trainA = sc.nextLine().split(" ");
            // for (int i = 0; i < trainA.length; i++) {
            //     System.out.println(trainA[i]);
            // }
            String[] trainB = sc.nextLine().split(" ");
            // for (int i = 0; i < trainB.length; i++) {
            //     System.out.println(trainB[i]);
            // }
            trainService.run(new ArrayList<>(Arrays.asList(trainA)), new ArrayList<>(Arrays.asList(trainB)), 
            combinedSourceStation, combinedDestinationStation);
            sc.close(); // closes the scanner
            
        } 
        catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
