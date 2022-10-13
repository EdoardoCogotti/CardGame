package com.game;

public class IndexedCard {

    private int index;
    private Card card;

    public IndexedCard(int i, Card c){
        index=i;
        card=c;
    }

    public Card getCard() {
        return card;
    }
    public int getIndex() {
        return index;
    }
}
