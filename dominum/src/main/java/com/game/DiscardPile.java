package com.game;

import java.util.ArrayList;
import java.util.Collections;

public class DiscardPile {

    private ArrayList<Card> cardsInPile;
    private String playerDiscardPile;

    public DiscardPile(Player p){
        cardsInPile = new ArrayList<>();
        playerDiscardPile = p.getName();
    }

    public ArrayList<Card> getCardsInPile() {return cardsInPile;}
    public void setCardsInPile(ArrayList<Card> cardsInPile) {
        this.cardsInPile = cardsInPile;
    }

    public String getPlayerDiscardPile() {return playerDiscardPile;}
    public void setPlayerDiscardPile(String playerDiscardPile) {
        this.playerDiscardPile = playerDiscardPile;
    }

    void discard(Card c){
        cardsInPile.add(c);
    }
    public void discardList(ArrayList<Card> cardList){
        for(Card c: cardList)
            discard(c);
    }

    public void clear(){
        cardsInPile.clear();
    }

    public ArrayList<Card> discardAll(){
        ArrayList<Card> aux = cardsInPile;
        cardsInPile.clear();
        return aux;
    }

    ArrayList<Card> recoverPile(){
        Collections.shuffle(cardsInPile);
        ArrayList<Card> aux = cardsInPile;
        cardsInPile.clear();
        return aux;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        System.out.println(playerDiscardPile + " hai " + cardsInPile.size() + " carte nella pila degli scarti");
        for(int i=0; i<cardsInPile.size(); i++) {
            Card c = cardsInPile.get(i);
            str.append(i + " - " + c.getName() + "\n");
        }
        return str.toString();
    }

}
