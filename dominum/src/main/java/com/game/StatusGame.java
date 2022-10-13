package com.game;

public class StatusGame {

    private int remainingActions;
    private int remainingBuy;
    private int bonusCoin;
    private int extraCards;

    public StatusGame(){
        reset();
    }

    public int getBonusCoin() {
        return bonusCoin;
    }
    public void setBonusCoin(int bonusCoin) {
        this.bonusCoin = bonusCoin;
    }

    public int getRemainingActions() {
        return remainingActions;
    }
    public void setRemainingActions(int remainingActions) {
        this.remainingActions = remainingActions;
    }

    public int getRemainingBuy() {
        return remainingBuy;
    }
    public void setRemainingBuy(int remainingBuy) {
        this.remainingBuy = remainingBuy;
    }

    public int getExtraCards() { return extraCards;}
    public void setExtraCards(int extraCards) {this.extraCards = extraCards;}

    public void addBonusCoin(int n){bonusCoin+=n;}

    public void addRemainingActions(int n){remainingActions+=n;}
    public void addRemainingBuy(int n){remainingBuy+=n;}
    public void addExtraCards(int n){extraCards+=n;}

    public void decRemainingBuy(){remainingBuy--;}
    public void decRemainingActions(){remainingActions--;}

    public void resetExtraCards(){extraCards=0;}

    public void reset(){
        remainingActions=1;
        remainingBuy=1;
        bonusCoin=0;
        extraCards=0;
    }

    @Override
    // don't consider bonusCoin and extraCards for the method usage
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("STATUS:\n");
        str.append("Azioni disponibili: " + remainingActions +" (se possibili)\n");
        str.append("Acquisti disponibili: " + remainingBuy+"\n");

        return str.toString();
    }

}


