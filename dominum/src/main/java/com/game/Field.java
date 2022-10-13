package com.game;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.*;

public class Field {

    private static Field instance = null;

    private Scanner myObj;
    private final String cardPath = "C:\\Users\\Edo\\IdeaProjects\\dominum\\src\\main\\resources\\cards.json";

    public ArrayList<Pile> piles;
    private boolean endGameFlag;

    private Field(){
        myObj = new Scanner(System.in);
        piles = new ArrayList<>();
        endGameFlag = false;
        initPiles();
    }

    // singleton pattern
    public static Field getInstance(){
        if(instance==null)
            instance = new Field();
        return  instance;
    }

    public ArrayList<Pile> getPiles() {
        return piles;
    }

    public boolean getEndGameFlag(){return endGameFlag;}

    public Card chooseFieldCard(int tot, Target t){

        listField();
        boolean found=false;
        Card c = null;
        while(!found){
            System.out.println("Scegli carta (max " + tot + ") Digita -1 per saltare fase: ");
            int index = myObj.nextInt();
            if(index==-1)
                return null;

            if(piles.get(index).getQuantity()>0){
                if(piles.get(index).getCard().getCost() <= tot) {
                    c = piles.get(index).getCard();
                    if(Target.match(c,t)) {
                        piles.get(index).decQuantity();
                        if (piles.get(index).getQuantity() == 0)
                            endGameFlag = true;
                        found = true;
                    }
                    else
                        System.out.println("Carta del tipo errato. Scegliere un'altra");
                }
                else
                    System.out.println("Carta troppo costosa per te. Scegliere un'altra");
            }
            else
                System.out.println("mazzo vuoto. Sceglierne un altro"); // useless, an empty pile means end game
        }
        return c;
    }

    private void listField(){
        System.out.println("FIELD: ");
        for(int i=0; i<piles.size(); i++){
            String name = piles.get(i).getCard().getName();
            int cost = piles.get(i).getCard().getCost();
            int q = piles.get(i).getQuantity();
            System.out.println(i + " - " + name + " costo: "+ cost + " ( " + q +" rimaste )");
        }
    }

    private void initPiles(){
        JSONObject jo=null;
        try {
            File f = new File(cardPath);
            URI uri = f.toURI();
            JSONTokener tokener = new JSONTokener(uri.toURL().openStream());
            jo = new JSONObject(tokener);
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray ja = jo.getJSONArray("cards");
        for(int i=0; i<ja.length(); i++){
            JSONObject jo_aux = ja.getJSONObject(i);
            String category = jo_aux.getString("category");
            int cost = jo_aux.getInt("cost");
            int numCards = jo_aux.getInt("numCards");
            Pile pile = null;

            if(category.equals("azione")) {
                String name = jo_aux.getString("name");
                String secondary_category = "";
                JSONArray actions = null;
                JSONObject bonus = null;
                if(jo_aux.has("secondary_category"))
                    secondary_category = jo_aux.getString("secondary_category");
                if(jo_aux.has("bonus"))
                    bonus = jo_aux.getJSONObject("bonus");
                if(jo_aux.has("actions"))
                    actions= jo_aux.getJSONArray("actions");
                ActionCard c = new ActionCard(name,cost,bonus,actions,secondary_category);
                pile = new Pile(c, numCards);
            }
            else {
                String name = jo_aux.getString("name");
                int value = jo_aux.getInt("value");
                if (category.equals("tesoro")) {
                    CoinCard c = new CoinCard(name, value, cost);
                    pile = new Pile(c, numCards);
                }
                else if (category.equals("vittoria")) {
                    VictoryCard c = new VictoryCard(name, value, cost);
                    pile = new Pile(c, numCards);
                }
            }
            piles.add(pile);
        }
    }
}
