package com.game;

import org.json.JSONArray;

public class IndexedAction {

    private int index;
    private JSONArray actions;

    IndexedAction(int i, JSONArray a){
        index=i;
        actions=a;
    }

    public int getIndex() {return index;}
    public JSONArray getActions() {return actions;}

}
