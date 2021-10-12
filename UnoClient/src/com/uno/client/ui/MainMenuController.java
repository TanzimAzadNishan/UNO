package com.uno.client.ui;

import com.uno.client.game.GameEngine;
import com.uno.client.game.UnoClientSubscriber;
import com.uno.common.commands.MoveCommand;
import com.uno.common.game.Card;
import com.uno.common.game.GameStatus;
import com.uno.common.game.PlayerStatus;
import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class MainMenuController extends UnoGameBaseViewController implements Initializable, UnoClientSubscriber {

    private static final String KEY = "main_menu_key";
    
    ObservableList<String> playertype = FXCollections.observableArrayList("2 Players","3 Players", "4 Players", "5 Players");
    @FXML
    private Font x1;
    @FXML
    private TextField playernameField;
    @FXML
    private ChoiceBox<String> playernumber;
    @FXML
    private Button startbtn;
    @FXML
    private Label connectinglab;
    @FXML
    private Label namelab;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playernumber.setItems(playertype);
        playernumber.setValue("3 Players");
        GameEngine.getInstance().subscribe(KEY, this);
    }

    @FXML
    private void afterstartbtn(ActionEvent event) {
        GameEngine.getInstance().connectServer();
        String username = playernameField.getText();
        if (username.equals("")) {
            namelab.setText("Give your username");
        } else {
            connectinglab.setText("Connecting");
            String playerCount = playernumber.getValue().replaceAll("Players", "").trim();
            //TODO: get value from fields
            GameEngine.getInstance().findAndJoinGame(playernameField.getText(), Integer.parseInt(playerCount));
        }
    }

    @Override
    public void onConnectedToServer() {
        
    }

    @Override
    public void onConnectionFailure() {
        
    }

    @Override
    public void onDisconnectedFromServer() {
        
    }

    @Override
    public void onGameStatusUpdated(GameStatus status) {
        System.out.println("game status " + status.getState().toString());
        System.out.println("players : " + status.getPlayers().size());
    }

    @Override
    public void onWaitingForPlayers(List<PlayerStatus> currentPlayers) {
        System.out.println("joined a game and waiting for players");
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                String playerCount = playernumber.getValue().replaceAll("Players", "").trim();
                ScreenSwitcher.getInstance().goToGameScreen(currentPlayers, Integer.parseInt(playerCount));
            }
        });
    }

    @Override
    public void onNewPlayersJoin(List<PlayerStatus> newPlayers) {
        
    }

    @Override
    public void onDealingCards() {
        
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public void onMoveCommand(MoveCommand command) {
        
    }
    
    @Override
    public void onGameFinished(PlayerStatus player) {
        
    }

    @Override
    public void onCardPlayed(PlayerStatus player, Card card, boolean isMe) {
        
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
