/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.client.game;

import com.uno.common.commands.MoveCommand;
import com.uno.common.game.Card;
import com.uno.common.game.GameStatus;
import com.uno.common.game.PlayerStatus;
import java.util.List;

/**
 *
 * @author
 */
public interface UnoClientSubscriber {
    
    void onConnectedToServer();
    void onConnectionFailure();
    void onDisconnectedFromServer();
    void onWaitingForPlayers(List<PlayerStatus> currentPlayers);
    void onNewPlayersJoin(List<PlayerStatus> newPlayers);
    void onDealingCards();
    void onGameStatusUpdated(GameStatus status);
    void onMoveCommand(MoveCommand command);
    void onGameFinished(PlayerStatus player);
    void onCardPlayed(PlayerStatus player, Card card, boolean isMe);
    void onCardDrawn(PlayerStatus player, boolean isMe);
    void onPassed(PlayerStatus player, boolean isMe);
    void onAcceptPenalty(PlayerStatus player, int count, boolean isMe);
    
}
