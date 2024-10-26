package com.example.geektrust.Entities;

public class Engine extends Bogie {
    private BogieType bogieType = BogieType.ENGINE;
    
    public Engine(String name) {
        super(name);
        //TODO Auto-generated constructor stub
    }

    public boolean isEngine() {
        return bogieType.equals(BogieType.ENGINE);
    }
}
