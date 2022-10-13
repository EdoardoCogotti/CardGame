package com.game;

public class VictoryCard extends Card {

    private int value;

    public VictoryCard(String n, int v, int c){
        super(n,c);
        value = v;
    }

    public int getValue(){
        return value;
    }
    public void setValue(int v) {
        value = v;
    }
}