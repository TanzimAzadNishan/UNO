/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.server;

import com.uno.common.game.Card;
import com.uno.common.game.GameAction;
import com.uno.common.game.GameState;
import com.uno.common.game.GameStatus;
import com.uno.common.game.PlayerStatus;
import com.uno.common.game.UnoDeck;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author
 */
public class Game {

    private final AtomicInteger playerIdGen = new AtomicInteger(0);
    private final AtomicLong statusVersion = new AtomicLong(0);

    private int slots = 0;

    private final List<Player> players = new ArrayList<>();
    List<PlayerStatus> playerstatus = new ArrayList<>();

    private GameState state = GameState.WAITING_FOR_PLAYER;
    private final GameStatus currentStatus = new GameStatus();

    private final DataDispatcher<GameStatus> dispatcher = new DataDispatcher<>();
    
    private List<String> moves = new ArrayList<>();

    private UnoDeck deck;

    public Game(int slots) {
        this.slots = slots;
    }

    public int totalSlots() {
        return slots;
    }

    public synchronized boolean isAvailable() {
        return slots - players.size() > 0;
    }

    public synchronized boolean addPlayer(String name, ObjectInputStream in, ObjectOutputStream out) {
        if (players.size() < slots) {
            Player player = new Player(playerIdGen.incrementAndGet(), name, in, out);
            players.add(player);

            updateStatus();

            /* TODO: start game if slot full */
            if (players.size() == slots) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }

                /* Every seat taken, now we can start the game */
                state = GameState.DEALING_CARDS;
                updateStatus();

                deck = new UnoDeck();
                deck.distribute(slots);

                /* TODO: shuffle cards and send it to players */
 /* TODO: start the game logic */
                new Thread(new GameRunner(this, deck)).start();

            }
            return true;
        }
        /* we are here means some one already joined before we were accepted */
        return false;
    }

    public void started() {
        state = GameState.GAME_STARTED;

        List<List<Card>> distribution = deck.getDistributions();
        for (int i = 0; i < deck.getDistributions().size(); i++) {
            players.get(i).setHand(distribution.get(i));
        }

        currentStatus.setTopCard(deck.getTopCard());

        updateStatus();
    }

    public void moveMade(Player player, Card card) {
        moves.add(card.toString());
        currentStatus.setLastPlayer(player.toPlayerStatus());
        currentStatus.setTopCard(card);
        currentStatus.setLastAction(GameAction.MOVE);
        currentStatus.setVersion(statusVersion.incrementAndGet());
        sendUpdatesToOthers(player.getId());
    }

    public void drawn(Player player) {
        moves.add("D");
        currentStatus.setLastPlayer(player.toPlayerStatus());
        currentStatus.setLastAction(GameAction.DRAW);
        currentStatus.setVersion(statusVersion.incrementAndGet());
        sendUpdatesToAll();
    }

    public void passed(Player player) {
        moves.add("P");
        currentStatus.setLastPlayer(player.toPlayerStatus());
        currentStatus.setLastAction(GameAction.PASS);
        currentStatus.setVersion(statusVersion.incrementAndGet());
        sendUpdatesToOthers(player.getId());
    }

    public void acceptedPenalty(Player player) {
        moves.add("A");
        currentStatus.setLastPlayer(player.toPlayerStatus());
        currentStatus.setLastAction(GameAction.ACCEPT_PENALTY);
        currentStatus.setVersion(statusVersion.incrementAndGet());
        sendUpdatesToAll();
    }

    public void finished(Player winner) {
        state = GameState.GAME_FINISHED;
        currentStatus.setWinner(winner.toPlayerStatus());
        currentStatus.setVersion(statusVersion.incrementAndGet());
        updateStatus();
    }

    private void updateStatus() {
        playerstatus.clear();
        currentStatus.setState(state);
        for(Player p: players){
            playerstatus.add(p.toPlayerStatus());
        }
        currentStatus.setPlayers(playerstatus);
        //playerstatus.removeAll(players);
        
        /*currentStatus.setPlayers(players.stream()
                .sorted(Comparator.comparing(Player::getId))
                .map(Player::toPlayerStatus)
                .collect(Collectors.toList()));*/
        currentStatus.setVersion(statusVersion.incrementAndGet());

        System.out.println("version :: " + currentStatus.getVersion());

        GameStatus copy = currentStatus.copy();

        for (Player p : players) {
            System.out.println("Sending updates version " + currentStatus.getVersion() + " to player " + p.getId());
            GameStatus masked = copy.maskAllBut(p.getId());
            dispatcher.send(masked, p.getOut());
        }

        /*players.forEach(p -> {
            System.out.println("Sending updates version " + currentStatus.getVersion() + " to player " + p.getId());
            GameStatus masked = copy.maskAllBut(p.getId());
            dispatcher.send(masked, p.getOut());
        });*/
    }

    public GameState currentState() {
        return state;
    }

    public List<Player> getPlayers() {
        return players;
    }
    
    public List<String> getMoveRecords() {
        return moves;
    }

    public void sendUpdatesToOthers(int sendingPlayerId) {

        GameStatus copy = currentStatus.copy();

        for (Player p : players) {
            if (p.getId() != sendingPlayerId) {
                System.out.println("Sending updates version " + currentStatus.getVersion() + " to player " + p.getId());
                GameStatus masked = copy.maskAllBut(p.getId());
                dispatcher.send(masked, p.getOut());
            }
        }

        /*players.forEach(p -> {
            if (p.getId() != sendingPlayerId) {
                System.out.println("Sending updates version " + currentStatus.getVersion() + " to player " + p.getId());
                GameStatus masked = copy.maskAllBut(p.getId());
                dispatcher.send(masked, p.getOut());
            }
        });*/
    }

    public void sendUpdatesToAll() {

        GameStatus copy = currentStatus.copy();

        for (Player p : players) {
            System.out.println("Sending updates version " + currentStatus.getVersion() + " to player " + p.getId());
            GameStatus masked = copy.maskAllBut(p.getId());
            dispatcher.send(masked, p.getOut());
        }

        /*players.forEach(p -> {
            System.out.println("Sending updates version " + currentStatus.getVersion() + " to player " + p.getId());
            GameStatus masked = copy.maskAllBut(p.getId());
            dispatcher.send(masked, p.getOut());
        });*/
    }

}
