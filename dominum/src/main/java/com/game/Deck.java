package com.game;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> cardsInDeck;
    private String playerDeck;

    public Deck(Player p){
        cardsInDeck = new ArrayList<Card>();
        playerDeck = p.getName();
    }

    public ArrayList<Card> getDeck(){
        return cardsInDeck;
    }
    public int getDeckSize(){return cardsInDeck.size();}

    public void shuffleDeck(){
        Collections.shuffle(cardsInDeck);
    }

    public void addCard(Card c){
        cardsInDeck.add(c);
    }

    public void addList(ArrayList<Card> cardList){
        for(Card c: cardList)
            addCard(c);
    }

    // return null when deck is empty
    // it'll inform to move from discard pile to deck
    public Card drawOneCard(){
        Card c=null;
        if(!cardsInDeck.isEmpty()){
            c = cardsInDeck.remove(0);
        }
        return c;
    }

    public ArrayList<Card> drawManyCards(int n){
        ArrayList<Card> list = new ArrayList<>();
        for(int i=0; i<n; i++) {
            Card aux = drawOneCard();
            list.add(aux);
        }
        return list;
    }

    // consider only card in deck, move all cards from hand and
    // discard pile to deck before call it
    public int getVictoryPointsInDeck(){
        int sum = 0;
        for(Card c: cardsInDeck){
            if(c instanceof VictoryCard)
                sum += ((VictoryCard) c).getValue();
        }
        return sum;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        System.out.println("Hai " + cardsInDeck.size() + " carte nel mazzo");
        for(int i=0; i<cardsInDeck.size(); i++) {
            Card c = cardsInDeck.get(i);
            str.append(i + " - " + c.getName() + "\n");
        }
        return str.toString();
    }
}
