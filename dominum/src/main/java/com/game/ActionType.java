package com.game;

public enum ActionType {
    GET("get"),
    DELETE("delete"),
    DISCARD("discard");

    private String text;

    private ActionType(String text){
        this.text=text;
    }

    public static ActionType fromString(String text) {
        for (ActionType at : ActionType.values()) {
            if (at.text.equalsIgnoreCase(text)) {
                return at;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
