package com.game;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    Scanner myObj;
    private final int num_player=2;

    private ArrayList<Player> players;
    private Player current_player;
    private Player winner;
    private Field field;
    private static int turn_counter;

    public Game(){
        myObj = new Scanner(System.in);
        field = Field.getInstance();
        players = new ArrayList<>();
        turn_counter=0;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getCurrent_player() {
        return current_player;
    }

    public void setCurrent_player(Player current_player) {
        this.current_player = current_player;
    }

    public void changeCurrentPlayer(){
        turn_counter++;
        int i = turn_counter % num_player;
        current_player = players.get(i);
    }

    public void start() {

        showInfo();
        registerPlayers();
        current_player = players.get(0);

        for(Player p : players){
            createDeck(p);
            p.getDeck().shuffleDeck();
            p.getNewHand();
        }

        while(true){

            // show cards
            System.out.println("TURNO DI " + current_player.getName().toUpperCase());
            current_player.showHand();

            while(current_player.getStatus().getRemainingActions()>0
                    && current_player.getHand().hasActionCardsInHand()) {
                System.out.println("FASE AZIONE:");
                System.out.println(current_player.getStatus().toString());

                IndexedCard ic = chooseActionCard();
                if(ic==null)
                    break;
                ActionCard ac = (ActionCard)ic.getCard();
                int index = ic.getIndex();
                current_player.showHand();
                IndexedAction ia = ac.execute(index);
                current_player.discard(ia.getIndex());

                if(ia.getActions()!=null){ //ci sono azioni sull'avversario
                    boolean react = checkReaction();
                    if(!react)
                        effectOnOpponent(ia.getActions());
                }

                current_player.getStatus().decRemainingActions();
                int extraCards = current_player.getStatus().getExtraCards();

                current_player.getStatus().resetExtraCards();
                current_player.drawMany(extraCards);

                current_player.showHand();
            }

            int totCoin = current_player.getHand().getTotalCoinHand()+current_player.getStatus().getBonusCoin();
            while(current_player.getStatus().getRemainingBuy()>0) {

                System.out.println(current_player.getStatus().toString());
                System.out.println("FASE ACQUISTO:");
                System.out.println("Hai " + totCoin +" monete");
                Card c = field.chooseFieldCard(totCoin, Target.ANY);
                if(c==null)
                    break;
                c.setOwner(current_player);
                if(field.getEndGameFlag()){
                    allInDeck();
                    getCurrentWinner();
                    System.exit(1);
                }

                totCoin -= c.getCost();
                current_player.getDiscardPile().discard(c);
                current_player.getStatus().decRemainingBuy();
            }

            current_player.endTurn();
            changeCurrentPlayer();

        }
    }

    private void showInfo(){
        System.out.println("Benvenuto a Dominum!");
    }

    private void registerPlayers(){

        for(int i =0; i<num_player; i++){
            System.out.println("Inserisci nome giocatore " + i + " : ");
            String playerName = myObj.next();
            Player p = new Player(playerName);
            players.add(p);
        }
    }

    private Player getCurrentWinner(){

        int maxVictoryPoint=-1;
        Player currentWinner=null;

        for(int i=0; i<num_player; i++){
            Player p = players.get(i);
            int points = p.getDeck().getVictoryPointsInDeck();
            System.out.println(p.getName() + " ha totalizzato " + points + " punti");
            if(points>=maxVictoryPoint) {
                maxVictoryPoint = points;
                currentWinner = p;
            }
        }
        return currentWinner;
    }

    private void createDeck(Player p){
        for(int i=0; i<7; i++) {
            CoinCard c = (CoinCard) field.getPiles().get(0).getCard(); // rame
            //ActionCard c = (ActionCard) field.getPiles().get(11).getCard(); //  // to simulate actioncards
            c.setOwner(p);
            p.getDeck().addCard(c);
        }
        for(int i=0; i<3; i++) {
            VictoryCard v = (VictoryCard) field.getPiles().get(3).getCard(); // tenuta
            //ActionCard v = (ActionCard) field.getPiles().get(7).getCard(); // to simulate actioncards
            v.setOwner(p);
            p.getDeck().addCard(v);
        }

    }

    private IndexedCard chooseActionCard(){

        boolean found=false;
        while(!found) {
            System.out.println("Giocatore " + current_player.getName() + " scegli carta azione (digita -1 per saltare fase): ");
            int index = myObj.nextInt();
            if(index==-1)
                return null;
            if(index>current_player.getHand().getHandSize()) {
                System.out.println("Numero non valido");
                continue;
            }
            Card c = current_player.chooseCard(index);
            if (c instanceof ActionCard) {
                IndexedCard ic = new IndexedCard(index,c);
                return ic;
            }
            else
                System.out.println("Non è una carta azione");
        }
        return null;
    }

    private void allInDeck(){
        for(Player p: players){
            p.insertAllInDeck();
        }
    }

    private void effectOnOpponent(JSONArray ja){
        for(Player p: players){
            if(p!=current_player){
                p.showHand();
                for(int i=0; i<ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);
                    ActionType at = ActionType.fromString(jo.getString("type"));
                    int qty = jo.getInt("qty");
                    for(int j=0; j<qty; j++){
                        switch (at){
                            case GET:
                                // TO_IMPLEMENT in case
                                break;
                            case DELETE:
                                // TO_IMPLEMENT in case
                                break;
                            case DISCARD:
                                p.discard(Target.ANY, -1);
                                break;
                        }
                    }
                }
            }
        }
    }

    private boolean checkReaction(){
        boolean b = false;
        for(Player p: players){
            if(p!=current_player){
                if(p.getHand().containsReaction()){
                    System.out.println(p.getName() + " vuoi usare la tua carta reazione? (Y/n)");
                    String ans = myObj.next();
                    if(ans.equalsIgnoreCase("Y"))
                        b=true;
                }
            }
        }
        return b;
    }
}
