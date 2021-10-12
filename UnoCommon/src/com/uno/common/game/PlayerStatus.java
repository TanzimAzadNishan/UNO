/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.common.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author
 */
public class PlayerStatus implements Serializable {
    
    private int id;
    private String name;
    private List<Card> hand = new ArrayList<>();
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }
    
    public PlayerStatus copy() {
        PlayerStatus ps = new PlayerStatus();
        ps.id = id;
        ps.name = name;
        ps.hand = new ArrayList<>();
        //hand.forEach(card -> ps.hand.add(card.copy()));
        
        for(Card c:hand){
            ps.hand.add(c.copy());
        }
        
        return ps;
    }
    
}
