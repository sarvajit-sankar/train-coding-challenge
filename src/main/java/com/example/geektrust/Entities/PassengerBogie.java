package com.example.geektrust.Entities;

public class PassengerBogie extends Bogie {
    private BogieType bogieType = BogieType.PASSENGER_CLASS;

    public PassengerBogie(String name) {
        super(name);
    }
    
    public boolean isEngine() {
        return bogieType.equals(BogieType.ENGINE);
    }
}
