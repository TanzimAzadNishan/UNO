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
public class GameStatus implements Serializable {
    
    private long version = 0;
    
    private GameState state = GameState.WAITING_FOR_PLAYER;
    private List<PlayerStatus> players = new ArrayList<>();
    private Card topCard = null;
    private PlayerStatus winner;
    
    private GameAction lastAction;
    private PlayerStatus lastPlayer;
    
    public void setState(GameState state) {
        this.state = state;
    }
    
    public GameState getState() {
        return state;
    }
    
    public void setPlayers(List<PlayerStatus> players) {
        this.players = players;
    }
    
    public List<PlayerStatus> getPlayers() {
        return players;
    }
    
    public void setVersion(long version) {
        this.version = version;
    }
    
    public long getVersion() {
        return version;
    }

    public Card getTopCard() {
        return topCard;
    }

    public void setTopCard(Card topCard) {
        this.topCard = topCard;
    }

    public PlayerStatus getWinner() {
        return winner;
    }

    public void setWinner(PlayerStatus winner) {
        this.winner = winner;
    }

    public PlayerStatus getLastPlayer() {
        return lastPlayer;
    }

    public void setLastPlayer(PlayerStatus lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    public GameAction getLastAction() {
        return lastAction;
    }

    public void setLastAction(GameAction lastAction) {
        this.lastAction = lastAction;
    }
    
    
    
    public GameStatus copy() {
        GameStatus status = new GameStatus();
        status.state = state;
        status.players = new ArrayList<>();
        
        if (topCard != null)
            status.topCard = topCard.copy();
        
        status.version = version;
        status.lastAction = lastAction;
        
        if (lastPlayer != null)
            status.lastPlayer = lastPlayer.copy();
        
        players.forEach(p -> status.players.add(p.copy()));
        
        if (winner != null)
            status.winner = winner.copy();
        return status;
    }
    
    public GameStatus maskAllBut(int id) {
        GameStatus status = copy();
        
        for(PlayerStatus p:status.players){
            System.out.println("attempt masking for " + p.getId());
            if (p.getId() != id){
                System.out.println("masking success for " + p.getId());
                List<Card> maskedHand = new ArrayList<>();
                for(Card card:p.getHand()){
                    maskedHand.add(Card.unknownCard());
                }
                p.setHand(maskedHand);
            }
        }
        
        /*status.players.forEach(p -> { 
            System.out.println("attempt masking for " + p.getId());
            if (p.getId() != id) {
                System.out.println("masking success for " + p.getId());    
                List<Card> maskedHand = new ArrayList<>();
                p.getHand().forEach(card -> maskedHand.add(Card.unknownCard()));

                p.setHand(maskedHand);
            }
        });*/
        
        if (status.lastPlayer != null && status.lastPlayer.getId() != id) {
            System.out.println("masking success for last player " + status.lastPlayer.getId() + " -- " + id);    
            List<Card> maskedHand = new ArrayList<>();
            for(Card card:status.lastPlayer.getHand()){
                maskedHand.add(Card.unknownCard());
            }
            //status.lastPlayer.getHand().forEach(card -> maskedHand.add(Card.unknownCard()));
            status.lastPlayer.setHand(maskedHand);
        }
        
        return status;
    }
    
}
