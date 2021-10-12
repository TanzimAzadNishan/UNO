/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.common.game;

import java.io.Serializable;

/**
 *
 * @author
 */
public class Card implements Serializable {
    
    private CardType type = CardType.NORMAL;
    private Color color = Color.RED;
    private int number = -1;
    
    private Card() {
        
    }

    public CardType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }
    
    public boolean isNormalCard() {
        return this.type == CardType.NORMAL;
    }

    public boolean isPlusFour() {
        return this.type == CardType.SPECIAL_PLUS_FOUR;
    }
    
    public boolean isWildCard() {
        return this.type == CardType.SPECIAL_WILD;
    }
    
    public boolean isPlusTwo() {
        return this.type == CardType.POWER_PLUS_TWO;
    }
    
    public boolean isStopCard() {
        return this.type == CardType.POWER_STOP;
    }
    
    public boolean isReverseCard() {
        return this.type == CardType.POWER_REVERSE;
    }
    
    public void setColor(Color color) {
        if (isPlusFour() || isWildCard())
            this.color = color;
    }
    
    public static Card normalCard(Color color, int number) {
        Card card = new Card();
        card.color = color;
        card.number = number;
        card.type = CardType.NORMAL;
        return card;
    }
    
    public static Card powerCardPlusTwo(Color color) {
        Card card = new Card();
        card.color = color;
        card.type = CardType.POWER_PLUS_TWO;
        return card;
    }
    
    public static Card powerCardStop(Color color) {
        Card card = new Card();
        card.color = color;
        card.type = CardType.POWER_STOP;
        return card;
    }
    
    public static Card powerCardReverse(Color color) {
        Card card = new Card();
        card.color = color;
        card.type = CardType.POWER_REVERSE;
        return card;
    }
    
    public static Card specialCardWild() {
        Card card = new Card();
        card.type = CardType.SPECIAL_WILD;
        return card;
    }
    
    public static Card specialCardPlusFour() {
        Card card = new Card();
        card.type = CardType.SPECIAL_PLUS_FOUR;
        return card;
    }
    
    public static Card unknownCard() {
        Card card = new Card();
        card.type = CardType.UNKNOWN;
        return card;
    }
    
    
    public String toString() {
        if (type == CardType.NORMAL)
            return color.name().substring(0, 1) + number;
        else if (type == CardType.POWER_PLUS_TWO)
            return "+2" + color.name().substring(0, 1);
        else if (type == CardType.POWER_REVERSE)
            return "Rev" + color.name().substring(0, 1);
        else if (type == CardType.POWER_STOP)
            return "Stop" + color.name().substring(0, 1);
        else if (type == CardType.SPECIAL_PLUS_FOUR)
            return "+4";
        else if (type == CardType.SPECIAL_WILD)
            return "W";
        else
            return "U";
                    
    }
    
    public Card copy() {
        Card card = new Card();
        card.color = color;
        card.number = number;
        card.type = type;
        return card;
    }
    
    public boolean equals(Card c) {
        return this.toString().equals(c.toString());
    }
    
    
}
