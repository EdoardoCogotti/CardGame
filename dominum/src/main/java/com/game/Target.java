package com.game;

public enum Target {
    COIN("coin"),
    HAND("hand"),
    OPPONENTS("opponents"),
    ANY("any");

    private String text;

    Target(String text){
        this.text=text;
    }

    public String getText() {
        return text;
    }

    public static Target fromString(String text) {
        for (Target t : Target.values()) {
            if (t.text.equalsIgnoreCase(text)) {
                return t;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }

    // TO_EXTEND in case of new cards
    public static boolean match(Card c, Target t){
        if(t == ANY)
            return true;
        if(t == COIN) {
            return (c instanceof CoinCard);
        }
        return false;
    }
}
