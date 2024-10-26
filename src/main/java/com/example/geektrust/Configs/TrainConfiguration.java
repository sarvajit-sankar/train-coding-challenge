package com.example.geektrust.Configs;

import java.util.HashMap;
import java.util.Optional;

public class TrainConfiguration {
    // given station code, returns station name
    private static HashMap<String, String> stationCodeToStationNameMap = new HashMap<>();
    static {
        stationCodeToStationNameMap.put("CHN", "CHENNAI");
        stationCodeToStationNameMap.put("SLM", "SALEM");
        stationCodeToStationNameMap.put("BLR", "BANGALORE");
        stationCodeToStationNameMap.put("KRN", "KURNOOL");
        stationCodeToStationNameMap.put("HYB", "HYDERABAD");
        stationCodeToStationNameMap.put("NGP", "NAGPUR");
        stationCodeToStationNameMap.put("ITJ", "ITARSI");
        stationCodeToStationNameMap.put("BPL", "BHOPAL");
        stationCodeToStationNameMap.put("AGA", "AGRA");
        stationCodeToStationNameMap.put("NDL", "NEW DELHI");
        stationCodeToStationNameMap.put("TVC", "TRIVANDRUM");
        stationCodeToStationNameMap.put("SRR", "SHORANUR");
        stationCodeToStationNameMap.put("MAQ", "MANGALORE");
        stationCodeToStationNameMap.put("MAO", "MADGAON");
        stationCodeToStationNameMap.put("PNE", "PUNE");
        stationCodeToStationNameMap.put("PTA", "PATNA");
        stationCodeToStationNameMap.put("NJP", "NEW JALPAIGURI");
        stationCodeToStationNameMap.put("GHY", "GUWAHATI");
    }

    public static Optional<String> getStationName(String stationCode) {
        return Optional.ofNullable(stationCodeToStationNameMap.get(stationCode));
    }

    
    private static HashMap<String, HashMap<String, Integer>> trainToStationDistanceMap = new HashMap<>();    
    // Train A -> given stationCode of a bogie in a train, returns the distance from origin
    static {
        HashMap<String, Integer> trainAStationDistances = new HashMap<>();
        trainAStationDistances.put("CHN", 0);
        trainAStationDistances.put("SLM", 350);
        trainAStationDistances.put("BLR", 550);
        trainAStationDistances.put("KRN", 900);
        trainAStationDistances.put("HYB", 1200);
        trainAStationDistances.put("NGP", 1600);
        trainAStationDistances.put("ITJ", 1900);
        trainAStationDistances.put("BPL", 2000);
        trainAStationDistances.put("AGA", 2500);
        trainAStationDistances.put("NDL", 2700);
        trainToStationDistanceMap.put("TRAIN_A", trainAStationDistances);
    }

    // Train B -> given stationCode of a bogie in a train, returns the distance from origin
    static {
        HashMap<String, Integer> trainBStationDistances = new HashMap<>();
        trainBStationDistances.put("TVC", 0);
        trainBStationDistances.put("SRR", 300);
        trainBStationDistances.put("MAQ", 600);
        trainBStationDistances.put("MAO", 1000);
        trainBStationDistances.put("PNE", 1400);
        trainBStationDistances.put("HYB", 2000);
        trainBStationDistances.put("NGP", 2400);
        trainBStationDistances.put("ITJ", 2700);
        trainBStationDistances.put("BPL", 2800);
        trainBStationDistances.put("PTA", 3800);
        trainBStationDistances.put("NJP", 4200);
        trainBStationDistances.put("GHY", 4700);
        trainToStationDistanceMap.put("TRAIN_B", trainBStationDistances);
    }

    // Train AB -> given stationCode of a bogie in a train, returns the distance from origin
    static {
        HashMap<String, Integer> trainABStationDistances = new HashMap<>();
        trainABStationDistances.put("HYB", 0);
        trainABStationDistances.put("NGP", 400);
        trainABStationDistances.put("ITJ", 700);
        trainABStationDistances.put("BPL", 800);
        trainToStationDistanceMap.put("TRAIN_AB", trainABStationDistances);
    }

    public static HashMap<String, Integer> getStationDistances(String trainName) {
        HashMap<String, Integer> stationDistances = trainToStationDistanceMap.get(trainName);
        return stationDistances;
    }

    // public static void computeStationDistance()
    public static int getStationDistanceFromGivenSource(String stationCode, String source) {
        int distance = -1;
        for (HashMap.Entry<String, HashMap<String, Integer>> set : trainToStationDistanceMap.entrySet()) {
            if (set.getValue().containsKey(stationCode) && set.getValue().containsKey(source)) {
                distance = Math.abs(set.getValue().get(stationCode) - set.getValue().get(source));
                break;
            }
        }
        return distance;
    }
}
