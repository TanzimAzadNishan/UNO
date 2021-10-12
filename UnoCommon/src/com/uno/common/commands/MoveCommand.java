/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.common.commands;

import com.uno.common.game.Card;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author
 */
public class MoveCommand implements Serializable {

    private Card card;
    private List<Card> currentHand = new ArrayList<Card>();
    private boolean isTopCardActive = true;
    private boolean isDrawAllowed = true;
    public static boolean topCard = true;

    public Card getCurrentCard() {
        return card;
    }

    public void setCurrentCard(Card card) {
        this.card = card;
    }

    public List<Card> getCurrentHand() {
        return currentHand;
    }

    public void setCurrentHand(List<Card> currentHand) {
        this.currentHand = currentHand;
    }

    public boolean isIsTopCardActive() {
        return isTopCardActive;
    }

    public void setIsTopCardActive(boolean isTopCardActive) {
        this.isTopCardActive = isTopCardActive;
    }

    public void setTopCard(boolean isTopCardActive) {
        topCard = isTopCardActive;
        setIsTopCardActive(topCard);
    }

    public boolean isIsDrawAllowed() {
        return isDrawAllowed;
    }

    public void setIsDrawAllowed(boolean isDrawAllowed) {
        this.isDrawAllowed = isDrawAllowed;
    }

}
