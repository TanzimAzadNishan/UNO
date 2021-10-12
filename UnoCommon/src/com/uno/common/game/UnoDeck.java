/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.common.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author
 */
public class UnoDeck {

    private List<Card> allCards = new ArrayList<>();

    private List<List<Card>> hands = new ArrayList<>();

    private List<Card> onBoard = new ArrayList<>();
    int count = 0;
    int flag = 0;

    public UnoDeck() {
        for (Color color : Color.values()) {
            for (int i = 0; i <= 9; i++) {
                allCards.add(Card.normalCard(color, i));
                allCards.add(Card.normalCard(color, i));
            }

            allCards.add(Card.powerCardPlusTwo(color));
            allCards.add(Card.powerCardPlusTwo(color));

            allCards.add(Card.powerCardStop(color));
            allCards.add(Card.powerCardStop(color));

            allCards.add(Card.powerCardReverse(color));
            allCards.add(Card.powerCardReverse(color));
        }

        allCards.add(Card.specialCardPlusFour());
        allCards.add(Card.specialCardPlusFour());
        allCards.add(Card.specialCardPlusFour());
        allCards.add(Card.specialCardPlusFour());

        allCards.add(Card.specialCardWild());
        allCards.add(Card.specialCardWild());
        allCards.add(Card.specialCardWild());
        allCards.add(Card.specialCardWild());

        Collections.shuffle(allCards, new Random());

    }

    public void distribute(int playerCount) {
        for (int i = 0; i < playerCount; i++) {
            hands.add(new ArrayList<>());
        }

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < playerCount; j++) {
                hands.get(j).add(allCards.get(0));
                allCards.remove(0);
            }
        }

        for (int i = 0; i < playerCount; i++) {
            System.out.println("Player " + i + " hands: [" + hands.get(i).stream().map(Card::toString).collect(Collectors.joining(", ")) + " ]");
        }

        for (int i = 0; i < allCards.size(); i++) {
            Card card = allCards.get(i);
            if (card.isNormalCard()) {
                onBoard.add(allCards.get(i));
                allCards.remove(i);
                break;
            }
        }

        System.out.println("Top Card On Board :: " + onBoard.get(onBoard.size() - 1));
        System.out.println("Remaining cards " + allCards.size());

    }

    public List<List<Card>> getDistributions() {
        return hands;
    }

    public Card getTopCard() {
        if (onBoard.size() > 0) {
            return onBoard.get(onBoard.size() - 1);
        }
        return null;
    }

    public Card drawCardForPlayer(int playerId) {
        System.out.println("Player drawing card :: " + playerId);
        playerId = playerId - 1;
        Card card;
        if (allCards.isEmpty()) {
            for (int i = onBoard.size() - 1; i > 0; i--) {
                allCards.add(onBoard.get(i));
                onBoard.remove(i);
            }

            Collections.shuffle(allCards, new Random());
        }

        hands.get(playerId).add(allCards.get(0));

        return allCards.remove(0);

    }

    private int getConsecutiveOnBoardCardCount(Card cardOnTop) {
        count = 0;
        //System.out.println("getConsecutiveOnBoardCardCount..........  "+isLastCardActive+" "+count);
        for (int i = 0; i < onBoard.size(); i++) {
            //System.out.println("getConsecutiveOnBoardCardCount..........  inside if"+" "+count);
            if (cardOnTop.equals(onBoard.get(i))) {
                count++;
            }
        }
        return count;
    }

    private int getConsecutiveOnBoardCardCount(Card cardOnTop, List<String> moves) {
//        count = 0;
//        System.out.println("getConsecutiveOnBoardCardCount..........  " + onBoard.size());
//        for (int i = 0; i < onBoard.size(); i++) {
//            if (!isLastCardActive) {
//                count = 1;
//                break;
//            }
//            else if (cardOnTop.equals(onBoard.get(i))) {
//                count++;
//            }
//            /*else if (cardOnTop.equals(onBoard.get(i))) {
//                count++;
//                flag = 1;
//            } else {
//                break;
//            }*/
//        }
//        return count;
        int count = 0;
        for (int i = moves.size() - 1; i >= 0; i--) {
            if (cardOnTop.isPlusTwo()) {
                if (moves.get(i).contains("+2")) {
                    count++;
                } else {
                    break;
                }
            } else if (cardOnTop.isPlusFour()) {
                if (moves.get(i).equals(cardOnTop.toString())) {
                    count++;
                } else {
                    break;
                }
            }

        }
        return count;

    }

    public List<Card> drawForPenalty(Card cardOnTop, int playerId, List<String> moves) {
        int penalty = 0;

        if (cardOnTop.isPlusTwo()) {
            //penalty = 2 * getConsecutiveOnBoardCardCount(cardOnTop);
            penalty = 2 * getConsecutiveOnBoardCardCount(cardOnTop, moves);
            count = 0;
        } else if (cardOnTop.isPlusFour()) {
            // penalty = 4 * getConsecutiveOnBoardCardCount(cardOnTop);
            penalty = 4 * getConsecutiveOnBoardCardCount(cardOnTop, moves);
            count = 0;
        }

        List<Card> penaltyCards = new ArrayList<>();
        for (int i = 0; i < penalty; i++) {
            penaltyCards.add(drawCardForPlayer(playerId));
        }

        return penaltyCards;
    }

    public void playedCard(int playerId, Card card) {
        playerId = playerId - 1;
        for (int i = 0; i < hands.get(playerId).size(); i++) {
            if (hands.get(playerId).get(i).equals(card)) {
                hands.get(playerId).remove(i);
                break;
            }
        }
        onBoard.add(card);
    }

}
