/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.client.ui;

import com.uno.client.game.GameEngine;
import com.uno.client.game.UnoClientSubscriber;
import com.uno.common.commands.MoveCommand;
import com.uno.common.game.Card;
import com.uno.common.game.GameStatus;
import com.uno.common.game.PlayerStatus;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author sayeedm
 */
public class SplashScreenController extends UnoGameBaseViewController implements Initializable, UnoClientSubscriber {

    private static final String KEY = "splash_screen";
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GameEngine.getInstance().subscribe(KEY, this);
        GameEngine.getInstance().connectServer();
    }    

    @Override
    public void onConnectedToServer() {
        Platform.runLater(new Runnable() {
            public void run() {
                ScreenSwitcher.getInstance().switchScreen("MainMenuScreen.fxml");        
            }
        });
        
    }

    @Override
    public void onConnectionFailure() {
        /* TODO: show a message that connection failed, and exit */
    }

    @Override
    public void onDisconnectedFromServer() {
        
    }

    @Override
    public void onGameStatusUpdated(GameStatus status) {
        
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public void onWaitingForPlayers(List<PlayerStatus> currentPlayers) {
        
    }

    @Override
    public void onNewPlayersJoin(List<PlayerStatus> newPlayers) {
        
    }

    @Override
    public void onDealingCards() {
        
    }

    @Override
    public void onMoveCommand(MoveCommand command) {
        
    }
    
    @Override
    public void onGameFinished(PlayerStatus player) {
        
    }

    @Override
    public void onCardPlayed(PlayerStatus player, Card card, boolean isMe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onCardDrawn(PlayerStatus player, boolean isMe) {
        
    }

    @Override
    public void onPassed(PlayerStatus player, boolean isMe) {
        
    }

    @Override
    public void onAcceptPenalty(PlayerStatus player, int count, boolean isMe) {
        
    }
    
}
