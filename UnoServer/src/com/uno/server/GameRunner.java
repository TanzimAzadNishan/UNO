
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.server;

import com.uno.common.commands.MoveCommand;
import com.uno.common.game.Card;
import com.uno.common.game.MoveDirection;
import com.uno.common.game.UnoDeck;
import com.uno.common.game.UnoUtils;
import com.uno.common.requests.PenaltyConsumeRequest;
import com.uno.common.requests.MoveRequest;
import com.uno.common.requests.PassRequest;
import com.uno.common.requests.DrawCardRequest;
import com.uno.common.requests.RelinquishRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class GameRunner implements Runnable {

    private final Game game;
    private final UnoDeck deck;

    private int currentPlayerIndex = 0;
    private MoveDirection currentDirection = MoveDirection.NEXT;
    private final DataDispatcher<MoveCommand> moveCommandDispatcher = new DataDispatcher<>();
    private boolean isLastCardActive = true;
    boolean flag = true;

    public GameRunner(Game game, UnoDeck deck) {
        this.game = game;
        this.deck = deck;
    }

    @Override
    public void run() {
        System.out.println("Game runner started");
        game.started();

        while (true) {

            Player player = getCurrentPlayer();
            handleMoveSession(player, true);
            //handleMoveSession(player, isLastCardActive);

            Optional<Player> optPlayer = getWinningPlayer();
            if (optPlayer.isPresent()) {
                game.finished(player);
                break;
            }

            Card topCard = deck.getTopCard();

            /*if (topCard.isPlusFour() && flag == false) {
                goToNextPlayer();
            } else if (topCard.isPlusTwo() && flag == false) {
                goToNextPlayer();
            } else if (topCard.isReverseCard() && flag == false) {
                goToNextPlayer();
            } else if (topCard.isStopCard() && flag == false) {
                goToNextPlayer();
            } else*/ if (topCard.isPlusFour()) {
                goToNextPlayer();
            } else if (topCard.isPlusTwo()) {
                goToNextPlayer();
            } else if (topCard.isReverseCard()) {
                List<String> moves = game.getMoveRecords();
                if (moves.get(moves.size() - 1).equals("D") || moves.get(moves.size() - 1).equals("P")) {
                    if (moves.get(moves.size() - 2).contains("Rev")) {
                        goToNextPlayer();
                    }
                } else if (game.totalSlots() == 2) {
                    List<String> moves1 = game.getMoveRecords();
                    if (moves1.get(moves1.size() - 1).equals("D") || moves.get(moves.size() - 1).equals("P")) {
                        if (moves1.get(moves1.size() - 2).contains("Rev")) {
                            goToNextPlayer();
                        }
                    } else {
                        goToNextPlayer();
                        goToNextPlayer();
                    }
                } else {
                    reverseDirection();
                    goToNextPlayer();
                }
            } else if (topCard.isStopCard()) {
                List<String> moves = game.getMoveRecords();
                if (moves.get(moves.size() - 1).equals("D") || moves.get(moves.size() - 1).equals("P")) {
                    if (moves.get(moves.size() - 2).contains("Stop")) {
                        goToNextPlayer();
                    }
                } else {
                    goToNextPlayer();
                    goToNextPlayer();
                }
            } else if (topCard.isWildCard() || topCard.isNormalCard()) {
                goToNextPlayer();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Optional<Player> getWinningPlayer() {
        return game.getPlayers().stream().filter(player -> player.getHand().size() == 0).findFirst();
    }

    private Player getCurrentPlayer() {
        return game.getPlayers().get(currentPlayerIndex);
    }

    private void handleMoveSession(Player player, boolean drawAllowed) {

        askPlayerToMove(player, drawAllowed);

        try {
            Object obj = player.getIn().readObject();
            if (obj instanceof MoveRequest) {
                /* player placed a move */
                MoveRequest move = (MoveRequest) obj;
                System.out.println(String.format("Player %s (%s) sent a move: %s", player.getName(), player.getId(), move.getPlacedCard()));

                if (UnoUtils.isValidMove(deck.getTopCard(), move.getPlacedCard(), player.getHand(), isLastCardActive)) {
                    deck.playedCard(player.getId(), move.getPlacedCard());
                    game.moveMade(player, deck.getTopCard());
                } else {
                    handleMoveSession(player, drawAllowed);
                }
                isLastCardActive = true;
                flag = true;

            } else if (obj instanceof DrawCardRequest) {

                /* Player wants to draw a card from deck */
                if (drawAllowed) {
                    System.out.println(String.format("Player %s (%s) wants to draw a card", player.getName(), player.getId()));
                    deck.drawCardForPlayer(player.getId());
                    game.drawn(player);

                    /*if (UnoUtils.hasPossibleValidMove(deck.getTopCard(), player.getHand())) {
                        //isLastCardActive=true;
                        handleMoveSession(player, false);
                    } else {
                        isLastCardActive = false;
                    }*/
                    if (UnoUtils.hasPossibleValidMove(deck.getTopCard(), player.getHand(), false)) {
                        //isLastCardActive = false;
                        handleMoveSession(player, false);
                    } else {
                        //isLastCardActive = false;
                    }
//                    
                }

            } else if (obj instanceof PassRequest) {

                System.out.println(String.format("Player %s (%s) wants to pass", player.getName(), player.getId()));

                if (!UnoUtils.hasPossibleValidMove(deck.getTopCard(), player.getHand())) {
                    handleMoveSession(player, drawAllowed);
                } else {
                    game.passed(player);
                }

            } else if (obj instanceof PenaltyConsumeRequest) {

                System.out.println(String.format("Player %s (%s) has to consume penalty", player.getName(), player.getId()));
                List<Card> cards = deck.drawForPenalty(deck.getTopCard(), player.getId(), game.getMoveRecords());
                game.acceptedPenalty(player);
                isLastCardActive = false;
                flag = false;

            } else if (obj instanceof RelinquishRequest) {
                System.out.println(String.format("Player %s (%s) had to let it go", player.getName(), player.getId()));
            }
        } catch (IOException ex) {
            /* TODO: player left broadcast */
            Logger.getLogger(GameRunner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GameRunner.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void askPlayerToMove(Player player, boolean drawAllowed) {
        MoveCommand command = new MoveCommand();
        command.setCurrentCard(deck.getTopCard());
        command.setCurrentHand(player.getHand());
        command.setIsTopCardActive(isLastCardActive);
        System.out.println("is top card avtive ########### " + isLastCardActive);
        command.setIsDrawAllowed(drawAllowed);
        moveCommandDispatcher.send(command, player.getOut());
    }

    private void goToNextPlayer() {
        switch (currentDirection) {
            case NEXT:
                currentPlayerIndex++;
                if (currentPlayerIndex >= game.getPlayers().size()) {
                    currentPlayerIndex = 0;
                }
                break;
            case PREVIOUS:
                currentPlayerIndex--;
                if (currentPlayerIndex < 0) {
                    currentPlayerIndex = game.getPlayers().size() - 1;
                }
                break;
        }
    }

    private void reverseDirection() {
        if (currentDirection == MoveDirection.NEXT) {
            currentDirection = MoveDirection.PREVIOUS;
        } else {
            currentDirection = MoveDirection.NEXT;
        }
    }

}
