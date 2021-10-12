/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.common.requests;

import com.uno.common.game.Card;
import java.io.Serializable;

/**
 *
 * @author
 */
public class MoveRequest implements Serializable {
    
    private Card placedCard;
    
    public MoveRequest(Card card) {
        this.placedCard = card;
    }

    public Card getPlacedCard() {
        return placedCard;
    }

    public void setPlacedCard(Card placedCard) {
        this.placedCard = placedCard;
    }
    
}
