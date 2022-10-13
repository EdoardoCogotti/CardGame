package com.game;

import java.util.Iterator;
import java.util.Scanner;

import org.json.*;

public class ActionCard extends Card {

    private Scanner myObj;

    private String secondary_category;
    private JSONObject bonus;
    private JSONArray actions;

    private int deletedCardValue;
    private int discardedCounter;
    private int newIndex;

    public ActionCard(String n, int c, JSONObject b, JSONArray a, String s){
        super(n,c);
        bonus = b;
        actions = a;
        secondary_category = s;

        myObj = new Scanner(System.in);
        deletedCardValue = -1;
        discardedCounter = -1;
        newIndex=-1; //
    }

    public JSONArray getActions() {
        return actions;
    }
    public JSONObject getBonus() {
        return bonus;
    }
    public String getSecondary_category() {return secondary_category;}

    public IndexedAction execute(int index){

        JSONArray effectsOnOpponents=new JSONArray();

        // bonus
        if(bonus!=null){
            Iterator<String> keys = bonus.keys();
            while(keys.hasNext()) {
                String key = keys.next();
                int v = bonus.getInt(key);
                addStatus(key, v);
            }
        }

        // effects
        if(actions!=null){
            for(int i=0; i<actions.length(); i++){
                JSONObject jo = actions.getJSONObject(i);

                ActionType actionType = ActionType.fromString(jo.getString("type"));
                Target target = Target.fromString(jo.getString("target"));
                if(target!=Target.OPPONENTS) {
                    activateEffect(actionType, target, jo, index);
                }
                else if(secondary_category.equals("attacco")){
                    JSONObject aux = new JSONObject();
                    aux.put("type", jo.getString("type"));
                    aux.put("qty", jo.getInt("qty"));
                    effectsOnOpponents.put(aux);
                }
                if(newIndex!=-1)
                    index=newIndex;
            }
            resetCard();
        }

        return new IndexedAction(index, effectsOnOpponents);
    }

    // Enum ???
    private void addStatus(String name, int value){
        switch (name){
            case "coin":
                getOwner().getStatus().addBonusCoin(value);
                break;
            case "buy":
                getOwner().getStatus().addRemainingBuy(value);
                break;
            case "action":
                getOwner().getStatus().addRemainingActions(value);
                break;
            case "card":
                System.out.println("own: " + getOwner());
                System.out.println("getstatus: " + getOwner().getStatus());
                System.out.println("value extra card: " + getOwner().getStatus().getExtraCards());
                getOwner().getStatus().addExtraCards(value);
                break;
        }
    }

    private void activateEffect(ActionType type, Target t, JSONObject jo, int index){
        int qty;
        switch(type){
            case GET:
                qty = jo.getInt("qty");
                if(qty==-1)
                    qty = discardedCounter;
                for(int i=0; i<qty; i++) {
                    Card c=null;
                    if(jo.getString("initial_location").equals("deck")) {
                        getOwner().drawMany(1);
                    }

                    if(jo.getString("initial_location").equals("field")){

                        Field f = Field.getInstance();
                        if (jo.has("delta")) {
                            int delta = jo.getInt(("delta"));
                            c = f.chooseFieldCard(deletedCardValue +delta, t);
                            c.setOwner(getOwner());
                        }
                        if (jo.has("max_value")) {
                            int maxValue = jo.getInt("max_value");
                            c = f.chooseFieldCard(maxValue, t);
                            c.setOwner(getOwner());
                        }
                        String location = jo.getString("final_location");
                        if(location.equals("hand"))
                            getOwner().getHand().addCardInHand(c);
                        if(location.equals("discardPile"))
                            getOwner().getDiscardPile().discard(c);
                    }
                }
                break;
            case DELETE:
                qty = jo.getInt("qty");
                for(int i=0; i<qty; i++){
                    deletedCardValue = getOwner().delete(t, index);
                }
                break;
            case DISCARD:
                qty = jo.getInt("qty");
                if(qty==-1){
                    System.out.println("Scegli quante carte scartare (digita -1 per saltare fase): ");
                    qty = myObj.nextInt();
                    discardedCounter = qty;
                }
                for(int i=0; i<qty; i++){
                    index = getOwner().discard(t, index);
                }
                newIndex=index;
                break;
        }
    }

    private void resetCard(){
        deletedCardValue = -1;
        discardedCounter = -1;
        newIndex = -1;
    }
}