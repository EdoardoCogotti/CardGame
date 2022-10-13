package com.game;

abstract public class Card {

    private Player owner;
    private String name;
    private int cost;

    public Card(String n, int c){
        name=n;
        cost=c;
        owner=null;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getCost() { return cost;}
    public void setCost(int cost) {
        this.cost = cost;
    }

    public Player getOwner() {return owner;}
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Card{" +
                ", name='" + name + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }
}
