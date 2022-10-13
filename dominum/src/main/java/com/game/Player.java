package com.game;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    private String name;

    private Hand hand;
    private Deck deck;
    private DiscardPile discardPile;

    private StatusGame status;
    private Scanner myObj;


    public Player(String n){
        name=n;
        status = new StatusGame();
        deck = new Deck(this);
        hand = new Hand(this);
        discardPile = new DiscardPile(this);

        myObj = new Scanner(System.in);
    }

    void setName(String n){ name=n; }
    String getName(){ return name; }

    public Deck getDeck() {
        return deck;
    }

    public Hand getHand() {
        return hand;
    }

    public DiscardPile getDiscardPile() {
        return discardPile;
    }

    public StatusGame getStatus() {
        return status;
    }

    private Card drawCard(){
        Card c = deck.drawOneCard();
        if(c!=null)
            hand.addCardInHand(c);
        return c;
    }

    public void getNewHand(){

        int drawCounter=5;
        int size = deck.getDeckSize();

        for(int i=0; i<Math.min(size,drawCounter); i++){
            Card c = deck.drawOneCard();
            hand.addCardInHand(c);
        }
        if(size<drawCounter) {
            movePileToDeck();
            for(int i=0; i<drawCounter-size; i++){
                Card c = deck.drawOneCard();
                hand.addCardInHand(c);
            }
        }
    }

    public Card chooseCard(int i){
        Card c = getHand().getCardsInHand().get(i);
        return c;
    }

    public void endTurn(){
        moveHandToPile();
        getNewHand();
        getStatus().reset();
    }

    private void movePileToDeck(){

        ArrayList<Card> pileCards = discardPile.getCardsInPile();

        for(Card c: pileCards) {
            deck.addCard(c);
        }
        deck.shuffleDeck();
        discardPile.clear();

    }

    private void moveHandToPile(){

        ArrayList<Card> handCards = hand.getCardsInHand();

        for(Card c: handCards) {
            discardPile.discard(c);
        }

        hand.clear();
    }

    public void insertAllInDeck(){
        moveHandToPile();
        movePileToDeck();
    }

    public void drawMany(int n){
        for(int i=0; i<n; i++){
            Card c = drawCard();
            if (c == null) {
                System.out.println("mazzo finito. Riempio");
                movePileToDeck();
                for(int j=0; j<(n-i); j++)
                    drawCard();
            }
        }
    }

    public void discard(int index){
        Card c = hand.getCardInHand(index);
        hand.removeCardInHand(index);
        discardPile.discard(c);
    }

    //TO CHECK
    public int delete(Target t, int index){
        boolean found = false;
        int value=-1;
        while(!found){
            System.out.println("Scegli carta da eliminare dal gioco: ");
            int i = myObj.nextInt();
            if(i!=index){
                Card c = chooseCard(i);
                if(Target.match(c,t)) {
                    value = c.getCost();
                    found = true;
                }
                else
                    System.out.println("Carta del tipo errato. Scegliere un'altra");
            }
            else
                System.out.println("non puoi cancellare la stessa carta");
        }
        return value;
    }

    // return the new card index
    public int discard(Target t, int index){
        boolean found = false;
        while(!found){
            System.out.println(name + " scegli carta da scartare dal gioco: ");
            int i = myObj.nextInt();
            if(i >= hand.getHandSize()) {
                System.out.println("Indice troppo grande");
                continue;
            }
            if(i!=index){
                found=true;
                Card c = chooseCard(i);
                if(Target.match(c,t)) {
                    hand.removeCardInHand(i);
                    discardPile.discard(c);

                    if(i<index)
                        index--;

                    showHand();
                }
                else
                    System.out.println("Carta del tipo errato. Scegliere un'altra");
            }
            else
                System.out.println("Non puoi scartare la carta che attiva l'effetto");
        }
        return index;
    }

    public void showHand(){
        String current_hand = getHand().toString();
        System.out.println(name +" : " + current_hand);
    }


}
