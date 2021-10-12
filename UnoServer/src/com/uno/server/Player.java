/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.server;

import com.uno.common.game.Card;
import com.uno.common.game.PlayerStatus;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 
 */
public class Player {
    
    private final String name;
    private final int id;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private List<Card> hand = new ArrayList<>();
    
    public Player(int id, String name, ObjectInputStream in, ObjectOutputStream out) {
        this.id = id;
        this.name = name;
        this.in = in;
        this.out = out;
    }
    
    public String getName() {
        return name;
    }
    
    public int getId() {
        return id;
    }
    
    public ObjectInputStream getIn() {
        return in;
    }

    public ObjectOutputStream getOut() {
        return out;
    }
    
    public void setHand(List<Card> hand) {
        this.hand = hand;
    }
    
    public List<Card> getHand() {
        return hand;
    }
    
    public void playedCard(Card card) {
        for (int i = 0 ; i < hand.size(); i++) {
            if (hand.get(i).equals(card)) {
                hand.remove(i);
                break;
            }
        }
    }
    
    public void drawCard(Card card) {
        hand.add(card);
    }
    
    public void drawPenalty(List<Card> cards) {
        hand.addAll(cards);
    }
    
    public PlayerStatus toPlayerStatus() {
        PlayerStatus status = new PlayerStatus();
        status.setId(id);
        status.setName(name);
        status.setHand(hand);
        return status;
    }
    
}
