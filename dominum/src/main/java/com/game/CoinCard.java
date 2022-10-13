package com.game;

public class CoinCard extends Card {

    private int value;

    public CoinCard(String n, int v, int c){
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