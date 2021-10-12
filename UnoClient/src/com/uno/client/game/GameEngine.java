/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.client.game;

import com.uno.common.game.Card;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author
 */
public class GameEngine {
    
    private static GameEngine instance = null;
    private GameEngine() {
        
    }
    
    public synchronized static GameEngine getInstance() {
        if (instance == null)
            instance = new GameEngine();
        
        return instance;
    }
    
    
    private ClientState state = ClientState.DISCONNECTED;
    private final UnoClient client = new UnoClient();
    private final Map<String, UnoClientSubscriber> subscribers = new ConcurrentHashMap<>();
    
    public void connectServer() {
        boolean isConnected = client.connect(subscribers.values());
        if (isConnected) {
            state = ClientState.CONNECTED;
        }
    }
    
    public void findAndJoinGame(String playerName, int desiredPlayerCount) {
        if (state == ClientState.CONNECTED)
            client.join(playerName, desiredPlayerCount);
        else
            System.out.println("Sorry I am not on CONNECTED STATE, my present state is " + state.toString());
    }
    
    public void sendMove(Card card) {
        if (state == ClientState.CONNECTED)
            client.sendMove(card);
        else
            System.out.println("Sorry I am not on CONNECTED STATE, my present state is " + state.toString());
    }
    
    public void sendDrawCard() {
        if (state == ClientState.CONNECTED)
            client.sendDrawCard();
        else
            System.out.println("Sorry I am not on CONNECTED STATE, my present state is " + state.toString());
    }
    
    public void sendAcceptPenalty() {
        if (state == ClientState.CONNECTED)
            client.sendAcceptPenalty();
        else
            System.out.println("Sorry I am not on CONNECTED STATE, my present state is " + state.toString());
    }
    
    public void sendRelinquish() {
        if (state == ClientState.CONNECTED)
            client.sendRelinquish();
        else
            System.out.println("Sorry I am not on CONNECTED STATE, my present state is " + state.toString());
    }
    
    public void sendPass() {
        if (state == ClientState.CONNECTED)
            client.sendPass();
        else
            System.out.println("Sorry I am not on CONNECTED STATE, my present state is " + state.toString());
    }
    
    
    
    public void subscribe(String key, UnoClientSubscriber subscriber) {
        System.out.println("Adding subscriber " + key);
        subscribers.put(key, subscriber);
    }
    
    public Collection<UnoClientSubscriber> getSubscribers() {
        return subscribers.values();
    }
    
    public void unsubscribe(String key) {
        System.out.println("removing subscriber " + key);
        subscribers.remove(key);
    }
    
    
}
