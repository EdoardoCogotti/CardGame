package com.game;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> cardsInHand;
    private String playerHand;

    public Hand(Player p){
        cardsInHand = new ArrayList<>();
        playerHand = p.getName();
    }

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }
    public Card getCardInHand(int i){return cardsInHand.get(i);}
    public int getHandSize(){return cardsInHand.size();}

    public void addCardInHand(Card c){
        cardsInHand.add(c);
    }

    public void removeCardInHand(int index){
        if(index<getHandSize())
            cardsInHand.remove(index);
    }

    public void clear(){
        cardsInHand.clear();
    }

    public ArrayList<Card>discardAll(){
        ArrayList<Card> aux = cardsInHand;
        cardsInHand.clear();
        return aux;
    }

    public boolean hasActionCardsInHand(){
        for(int i=0; i<cardsInHand.size(); i++)
            if(cardsInHand.get(i) instanceof ActionCard)
                return true;
        return false;
    }

    public int getTotalCoinHand(){
        int sum=0;
        for(int i=0; i<cardsInHand.size(); i++)
            if(cardsInHand.get(i) instanceof CoinCard)
                sum += ((CoinCard) cardsInHand.get(i)).getValue();
        return sum;
    }

    public boolean containsReaction(){
        boolean found=false;
        for(Card c: cardsInHand){
            if(c instanceof ActionCard && ((ActionCard) c).getSecondary_category().equals("reazione"))
                found=true;
        }
        return found;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("Hai " + cardsInHand.size() + " carte in mano\n");
        for(int i=0; i<cardsInHand.size(); i++) {
            Card c = cardsInHand.get(i);
            str.append(i + " - " + c.getName() + "\n");
        }
        return str.toString();
    }
}
