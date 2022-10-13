package com.game;

public class Pile{

    private Card card;
    private int quantity;

    Pile(Card c, int numCards){
        card = c;
        quantity = numCards;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Card getCard() {
        return card;
    }
    public void setCard(Card card) {
        this.card = card;
    }

    public void decQuantity(){
        quantity--;
    }
}
