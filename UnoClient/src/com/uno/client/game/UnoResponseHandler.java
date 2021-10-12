/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.client.game;

import com.uno.common.commands.MoveCommand;
import com.uno.common.game.Card;
import com.uno.common.game.GameAction;
import com.uno.common.game.GameState;
import com.uno.common.game.GameStatus;
import com.uno.common.game.PlayerStatus;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class UnoResponseHandler implements Runnable {

    private ObjectInputStream in;
    private GameStatus lastStatus = new GameStatus();
    
    public UnoResponseHandler(ObjectInputStream in) {
        this.in = in;
        lastStatus.setState(GameState.NONE);
    }
    
    @Override
    public void run() {
        
        while (true) {
            try {
                
                Object obj = in.readObject();
                
                if (obj instanceof GameStatus) {
                    final GameStatus newStatus = (GameStatus)obj;
                    
                    if (newStatus.getVersion() > lastStatus.getVersion() ) {
                        /* events (incremental) that depends on previous states */
                        if (newStatus.getState() != lastStatus.getState()) {
                            switch(newStatus.getState()) {
                                case WAITING_FOR_PLAYER:
                                    GameEngine.getInstance().getSubscribers().forEach(subscriber -> subscriber.onWaitingForPlayers(newStatus.getPlayers()));            
                                    break;
                            }
                        } else {
                            switch(newStatus.getState()) {
                                case WAITING_FOR_PLAYER:
                                    List<PlayerStatus> newPlayers = GameUtils.findNewPlayers(lastStatus.getPlayers(), newStatus.getPlayers());
                                    if (newPlayers.size() > 0)
                                        GameEngine.getInstance().getSubscribers().forEach(subscriber -> subscriber.onNewPlayersJoin(newPlayers));            
                                    break;
                            }
                        }
                        
                        /* events that doesn't depend on previous states (well actually does but full data is sent, so we do have to */
                        /* handle it on client side */
                        switch (newStatus.getState()) {
                            case DEALING_CARDS:
                                GameEngine.getInstance().getSubscribers().forEach(subscriber -> subscriber.onDealingCards());            
                                break;
                            case GAME_STARTED:
                                handlePlayerActions(newStatus);
                                GameEngine.getInstance().getSubscribers().forEach(subscriber -> subscriber.onGameStatusUpdated(newStatus));            
                                break;
                            case GAME_FINISHED:
                                GameEngine.getInstance().getSubscribers().forEach(subscriber -> subscriber.onGameFinished(newStatus.getWinner()));            
                                break;
                        }
                        
                        lastStatus = newStatus;
                    } else {
                        System.out.println("Discarding outdated message, version " + newStatus.getVersion() + " old id : " + lastStatus.getVersion());
                    }
                } else if (obj instanceof MoveCommand) {
                    GameEngine.getInstance().getSubscribers().forEach(subscriber -> subscriber.onMoveCommand((MoveCommand)obj));
                }
                
                /*TODO: java.io.EOFException */
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(UnoResponseHandler.class.getName()).log(Level.SEVERE, null, ex);
               
                break;
            }
        }
    }
    
    private void handlePlayerActions(GameStatus newStatus) {
        
        if (newStatus.getLastAction() == GameAction.MOVE && newStatus.getTopCard() != null) {
            
            if (newStatus.getLastPlayer() != null && newStatus.getLastPlayer().getHand().size() > 0) {
                boolean isMe = !newStatus.getLastPlayer().getHand().get(0).equals(Card.unknownCard());
                GameEngine.getInstance().getSubscribers().forEach(subscriber -> subscriber.onCardPlayed(newStatus.getLastPlayer(), newStatus.getTopCard(), isMe));
                System.out.println(String.format("MOVE %s (%s) %s", newStatus.getLastPlayer().getName(), newStatus.getLastPlayer().getId(), newStatus.getTopCard().toString()));
            } else {
                System.out.println("Last Player Not Found");
            }
            
        } else if (newStatus.getLastAction() == GameAction.DRAW) {
            
            if (newStatus.getLastPlayer() != null && newStatus.getLastPlayer().getHand().size() > 0) {
                boolean isMe = !newStatus.getLastPlayer().getHand().get(0).equals(Card.unknownCard());
                GameEngine.getInstance().getSubscribers().forEach(subscriber -> subscriber.onCardDrawn(newStatus.getLastPlayer(), isMe));
                System.out.println(String.format("DRAW %s (%s)", newStatus.getLastPlayer().getName(), newStatus.getLastPlayer().getId()));
            } else {
                System.out.println("Last Player Not Found");
            }
                        
        } else if (newStatus.getLastAction() ==  GameAction.PASS) {
            if (newStatus.getLastPlayer() != null && newStatus.getLastPlayer().getHand().size() > 0) {
                boolean isMe = !newStatus.getLastPlayer().getHand().get(0).equals(Card.unknownCard());
                GameEngine.getInstance().getSubscribers().forEach(subscriber -> subscriber.onPassed(newStatus.getLastPlayer(), isMe));
            } else {
                System.out.println("Last Player Not Found");
            }
        } else if (newStatus.getLastAction() == GameAction.ACCEPT_PENALTY) {
            for (int i = 0 ; i < newStatus.getPlayers().size(); i++) {
                PlayerStatus player = newStatus.getPlayers().get(i);
                if (player.getHand().size() > lastStatus.getPlayers().get(i).getHand().size()) {
                    int diff = player.getHand().size() - lastStatus.getPlayers().get(i).getHand().size();
                    GameEngine.getInstance().getSubscribers().forEach(subscriber -> subscriber.onAcceptPenalty(player, diff, !player.getHand().get(0).equals(Card.unknownCard())));
                    return;
                }
            }
        }
    }
    
}
