package com.example.geektrust;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.geektrust.Services.TrainService;

public class MainTest {
    private TrainService trainService;
    ByteArrayOutputStream outputStream;
    PrintStream originalOut;

    @BeforeEach
    void setup(){
        trainService = new TrainService();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    @DisplayName("2 trains each having bogies till end, meeting at HYB and splitting at BPL")
    void testBaseTrainRun(){
        //Arrange
        String trainA = "TRAIN_A ENGINE KRN SLM BLR BLR HYB NGP ITJ BPL BPL NDL NDL";
        String trainB = "TRAIN_B ENGINE NJP GHY PNE MAQ NGP NGP BPL PTA";
        String expectedArrivalOfTrainA = "ARRIVAL  TRAIN_A ENGINE NGP ITJ BPL BPL NDL NDL";
        String expectedArrivalOfTrainB = "ARRIVAL  TRAIN_B ENGINE NJP GHY NGP NGP BPL PTA";
        String expectedDepartureofTrainAB = "DEPARTURE  TRAIN_AB ENGINE ENGINE GHY NJP PTA NDL NDL BPL BPL BPL ITJ NGP NGP NGP";

        //Act
        testFunctionality(trainA.split(" "), trainB.split(" "), expectedArrivalOfTrainA,
        expectedArrivalOfTrainB, expectedDepartureofTrainAB);
    }

    @Test
    @DisplayName("Train A has bogies for stations of Train B and vice versa, meeting at HYB and splitting at BPL")
    void testCrossTrainRun(){
        //Arrange
        String trainA = "TRAIN_A ENGINE NDL NDL KRN GHY SLM NJP NGP BLR";
        String trainB = "TRAIN_B ENGINE NJP GHY AGA PNE MAO BPL PTA";
        String expectedArrivalOfTrainA = "ARRIVAL  TRAIN_A ENGINE NDL NDL GHY NJP NGP";
        String expectedArrivalOfTrainB = "ARRIVAL  TRAIN_B ENGINE NJP GHY AGA BPL PTA";
        String expectedDepartureofTrainAB = "DEPARTURE  TRAIN_AB ENGINE ENGINE GHY GHY NJP NJP PTA NDL NDL AGA BPL NGP";
        
        //Act
        testFunctionality(trainA.split(" "), trainB.split(" "), expectedArrivalOfTrainA,
        expectedArrivalOfTrainB, expectedDepartureofTrainAB);
    }

    @Test
    @DisplayName("Train B travels till end, train A doesn't have bogies after Hyd")
    void testTrainBFullRun(){
        //Arrange
        String trainA = "TRAIN_A ENGINE KRN KRN SLM BLR BLR HYB";
        String trainB = "TRAIN_B ENGINE NJP GHY PNE MAQ NGP BPL PTA";
        String expectedArrivalOfTrainA = "ARRIVAL  TRAIN_A ENGINE";
        String expectedArrivalOfTrainB = "ARRIVAL  TRAIN_B ENGINE NJP GHY NGP BPL PTA";
        String expectedDepartureofTrainAB = "DEPARTURE  TRAIN_AB ENGINE ENGINE GHY NJP PTA BPL NGP";

        //Act
        testFunctionality(trainA.split(" "), trainB.split(" "), expectedArrivalOfTrainA,
        expectedArrivalOfTrainB, expectedDepartureofTrainAB);
    }

    @Test
    @DisplayName("Train A travels till end, train B doesn't have bogies till Hyd")
    void testTrainAFullRun(){
        //Arrange
        String trainA = "TRAIN_A ENGINE NDL NDL ITJ AGA NGP BPL KRN SLM BLR";
        String trainB = "TRAIN_B ENGINE SRR PNE PNE MAO MAQ";
        String expectedArrivalOfTrainA = "ARRIVAL  TRAIN_A ENGINE NDL NDL ITJ AGA NGP BPL";
        String expectedArrivalOfTrainB = "ARRIVAL  TRAIN_B ENGINE";
        String expectedDepartureofTrainAB = "DEPARTURE  TRAIN_AB ENGINE ENGINE NDL NDL AGA BPL ITJ NGP";
        
        //Act
        testFunctionality(trainA.split(" "), trainB.split(" "), expectedArrivalOfTrainA,
        expectedArrivalOfTrainB, expectedDepartureofTrainAB);
    }

    @Test
    @DisplayName("Train B travels till end, but has bogies of train A, train A doesn't travell till Hyd")
    void testTrainBCrossFullRun(){
        //Arrange
        String trainA = "TRAIN_A ENGINE KRN KRN SLM BLR BLR HYB";
        String trainB = "TRAIN_B ENGINE NJP GHY PNE SRR MAQ MAO NGP BPL PTA AGA NDL NDL";
        String expectedArrivalOfTrainA = "ARRIVAL  TRAIN_A ENGINE";
        String expectedArrivalOfTrainB = "ARRIVAL  TRAIN_B ENGINE NJP GHY NGP BPL PTA AGA NDL NDL";
        String expectedDepartureofTrainAB = "DEPARTURE  TRAIN_AB ENGINE ENGINE GHY NJP PTA NDL NDL AGA BPL NGP";

        //Act
        testFunctionality(trainA.split(" "), trainB.split(" "), expectedArrivalOfTrainA,
        expectedArrivalOfTrainB, expectedDepartureofTrainAB);
    }

    @Test
    @DisplayName("Train A travels till end, but has bogies of train B, train B doesn't travell till Hyd")
    void testTrainACrossFullRun(){
        //Arrange
        String trainA = "TRAIN_A ENGINE NDL NDL AGA BPL KRN GHY SLM NJP NGP BLR";
        String trainB = "TRAIN_B ENGINE SRR SRR PNE PNE MAO MAQ";
        String expectedArrivalOfTrainA = "ARRIVAL  TRAIN_A ENGINE NDL NDL AGA BPL GHY NJP NGP";
        String expectedArrivalOfTrainB = "ARRIVAL  TRAIN_B ENGINE";
        String expectedDepartureofTrainAB = "DEPARTURE  TRAIN_AB ENGINE ENGINE GHY NJP NDL NDL AGA BPL NGP";
        
        //Act
        testFunctionality(trainA.split(" "), trainB.split(" "), expectedArrivalOfTrainA,
        expectedArrivalOfTrainB, expectedDepartureofTrainAB);
    }

    @Test
    @DisplayName("Both trains don't travel after Hyd")
    void testTrainsHaltInBetweenRun(){
        //Arrange
        String trainA = "TRAIN_A ENGINE KRN SLM SLM BLR BLR";
        String trainB = "TRAIN_B ENGINE SRR PNE PNE MAQ MAO MAO";
        String expectedArrivalOfTrainA = "ARRIVAL  TRAIN_A ENGINE";
        String expectedArrivalOfTrainB = "ARRIVAL  TRAIN_B ENGINE";
        String expectedDepartureofTrainAB = "JOURNEY_ENDED";
        
        //Act
        testFunctionality(trainA.split(" "), trainB.split(" "), expectedArrivalOfTrainA,
        expectedArrivalOfTrainB, expectedDepartureofTrainAB);
    }

    private void testFunctionality(String[] trainA, String[] trainB, String expectedArrivalOfTrainA,
            String expectedArrivalOfTrainB, String expectedDepartureofTrainAB) {
                try {
                    // Act
                    trainService.run(
                        new ArrayList<>(Arrays.asList(trainA)),
                        new ArrayList<>(Arrays.asList(trainB)),
                        "HYB",
                        "BPL"
                    );
                    
                    // Assert
                    String[] actualOutput = outputStream.toString().trim().split("\n");
                    assertEquals(expectedArrivalOfTrainA, actualOutput[0].trim());
                    assertEquals(expectedArrivalOfTrainB, actualOutput[1].trim());
                    assertEquals(expectedDepartureofTrainAB, actualOutput[2].trim());
                } finally {
                    System.setOut(originalOut);  // Important: restore original System.out
                }
    }
}