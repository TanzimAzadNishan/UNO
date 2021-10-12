/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.common.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author
 */
public class UnoUtils {

    static int count = 0;
    static List<Card> validCards = new ArrayList<Card>();

    public static boolean isValidMove(Card cardOnTop, Card cardToBeMoved, List<Card> hand, boolean topCardActive) {

        if (cardOnTop.isNormalCard()) {
            return isValidAgainstNormalCard(cardOnTop, cardToBeMoved, hand, topCardActive);
        } else if (cardOnTop.isPlusTwo()) {
            return isValidAgainstPlusTwoCard(cardOnTop, cardToBeMoved, hand, topCardActive);
        } else if (cardOnTop.isReverseCard()) {
            return isValidAgainstReverseCard(cardOnTop, cardToBeMoved, hand, topCardActive);
        } else if (cardOnTop.isWildCard()) {
            return isValidAgainstWildCard(cardOnTop, cardToBeMoved);
        } else if (cardOnTop.isStopCard()) {
            return isValidAgainstStopCard(cardOnTop, cardToBeMoved, hand);
        } else if (cardOnTop.isPlusFour()) {
            return isValidAgainstPlusFour(cardOnTop, cardToBeMoved, hand, topCardActive);
        }

        return false;

    }

    public static boolean isValidMove(Card cardOnTop, Card cardToBeMoved, List<Card> hand, Color pickedColor, boolean topCardActive) {

        if (cardOnTop.isNormalCard()) {
            return isValidAgainstNormalCard(cardOnTop, cardToBeMoved, hand, topCardActive);
        } else if (cardOnTop.isPlusTwo()) {
            return isValidAgainstPlusTwoCard(cardOnTop, cardToBeMoved, hand, topCardActive);
        } else if (cardOnTop.isReverseCard()) {
            return isValidAgainstReverseCard(cardOnTop, cardToBeMoved, hand, topCardActive);
        } else if (cardOnTop.isWildCard()) {
            return isValidAgainstWildCard(cardOnTop, cardToBeMoved, pickedColor);
        } else if (cardOnTop.isStopCard()) {
            return isValidAgainstStopCard(cardOnTop, cardToBeMoved, hand);
        } else if (cardOnTop.isPlusFour()) {
            return isValidAgainstPlusFour(cardOnTop, cardToBeMoved, hand, topCardActive, pickedColor);
        }

        return false;

    }

    private static boolean isValidAgainstNormalCard(Card cardOnTop, Card cardToBeMoved, List<Card> hand, boolean topCardActive) {
        if (cardToBeMoved.isPlusFour() || cardToBeMoved.isWildCard()) {
            Optional<Card> optCard = getPossibleValidCard(cardOnTop, hand);
            return !optCard.isPresent();
        }
        if (cardToBeMoved.isWildCard()) {
            return true;
        }

        return ((cardToBeMoved.getColor() == cardOnTop.getColor()) || (cardToBeMoved.getNumber() == cardOnTop.getNumber()));
    }

    private static boolean isValidAgainstPlusTwoCard(Card cardOnTop, Card cardToBeMoved, List<Card> hand, boolean topCardActive) {

        /*if (cardToBeMoved.isPlusTwo()) {
            return true;
        } else if (cardOnTop.getColor() == cardToBeMoved.getColor()) {
            return true;
        }

        if (!topCardActive) {
            return cardOnTop.getColor() == cardToBeMoved.getColor();
        }

        return false;*/
        if (cardToBeMoved.isPlusTwo()) {
            return true;
        }
        if (!topCardActive) {
            if (cardToBeMoved.isPlusFour() || cardToBeMoved.isWildCard()) {
                return true;
            } else if (cardOnTop.getColor() == cardToBeMoved.getColor()) {
                return true;
            }
        }
        return false;

    }

    private static boolean isValidAgainstStopCard(Card cardOnTop, Card cardToBeMoved, List<Card> hand) {

        if (cardToBeMoved.isStopCard() || cardToBeMoved.isPlusFour() || cardToBeMoved.isWildCard()) {
            return true;
        }

        return (cardToBeMoved.getColor() == cardOnTop.getColor());

    }

    private static boolean isValidAgainstReverseCard(Card cardOnTop, Card cardToBeMoved, List<Card> hand, boolean topCardActive) {

        if (cardToBeMoved.isPlusFour() || cardToBeMoved.isWildCard() || cardToBeMoved.isReverseCard()) {
            return true;
        }

        return cardOnTop.getColor() == cardToBeMoved.getColor();

    }

    private static boolean isValidAgainstPlusFour(Card cardOnTop, Card cardToBeMoved, List<Card> hand, boolean topCardActive) {

        if (topCardActive) {
            if (cardToBeMoved.isPlusFour()) {
                return true;
            }

            return false;
        }

        if (cardToBeMoved.isPlusFour() || cardToBeMoved.isWildCard()) {
            return true;
        } else {
            if (cardToBeMoved.getColor() == cardOnTop.getColor()) {
                return true;
            }
            return false;
        }
        /*if (cardToBeMoved.isPlusFour()) {
            return true;
        }
        if (topCardActive==false) {
            if (cardToBeMoved.getColor() == cardOnTop.getColor()) {
                return true;
            } else if (cardToBeMoved.isWildCard()) {
                return true;
            }
        }
        return false;*/
        //return cardOnTop.getColor() == cardToBeMoved.getColor();

    }

    private static boolean isValidAgainstPlusFour(Card cardOnTop, Card cardToBeMoved, List<Card> hand, boolean topCardActive, Color pickedColor) {

        if (topCardActive) {
            if (cardToBeMoved.isPlusFour()) {
                return true;
            }

            return false;
        }

        if (cardToBeMoved.isPlusFour() || cardToBeMoved.isWildCard()) {
            return true;
        } else {
            if (cardToBeMoved.getColor() == pickedColor) {
                return true;
            }
            return false;
        }
    }

    private static boolean isValidAgainstWildCard(Card cardOnTop, Card cardToBeMoved) {
        if (cardToBeMoved.isPlusFour() || cardToBeMoved.isWildCard()) {
            return true;
        }
        return cardOnTop.getColor() == cardToBeMoved.getColor();
    }

    private static boolean isValidAgainstWildCard(Card cardOnTop, Card cardToBeMoved, Color pickedColor) {
        if (cardToBeMoved.isPlusFour() || cardToBeMoved.isWildCard()) {
            return true;
        }
        return cardToBeMoved.getColor() == pickedColor;
    }

    public static Color getMostFrequentColor(List<Card> hand) {
        int blue = 0, red = 0, green = 0, yellow = 0;
        for (Card c : hand) {
            if (c.getColor() == Color.BLUE) {
                blue++;
            } else if (c.getColor() == Color.RED) {
                red++;
            } else if (c.getColor() == Color.GREEN) {
                green++;
            } else if (c.getColor() == Color.YELLOW) {
                yellow++;
            }
        }
        int max = blue;
        if (max < red) {
            max = red;
        }
        if (max < green) {
            max = green;
        }
        if (max < yellow) {
            max = yellow;
        }

        if (max == red) {
            return Color.RED;
        } else if (max == blue) {
            return Color.BLUE;
        } else if (max == green) {
            return Color.GREEN;
        } else {
            return Color.YELLOW;
        }
        /*return hand.stream()
                .filter(c -> c.isNormalCard() || c.isPlusTwo() || c.isReverseCard() || c.isStopCard())
                .map(Card::getColor)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(Color.BLUE);*/
    }

    public static Optional<Card> getPossibleValidCard(Card topCard, List<Card> hand) {
        if (topCard.isNormalCard()) {
            for (Card c : hand) {
                if (c.isNormalCard() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                } else if (c.isPlusTwo() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                } else if (c.isStopCard() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                } else if (c.isReverseCard() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                }
            }
            /*return hand.stream()
                       .filter(c -> 
                                    (c.isNormalCard() && c.getColor() == topCard.getColor()) ||
                                    (c.isPlusTwo()&& c.getColor() == topCard.getColor()) ||
                                    (c.isStopCard() && c.getColor() == topCard.getColor()) ||
                                    (c.isReverseCard() && c.getColor() == topCard.getColor())
                              )
                        .findFirst();*/
        } else if (topCard.isPlusTwo()) {
            for (Card c : hand) {
                if (c.isNormalCard() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                } else if (c.isPlusTwo()) {
                    return Optional.of(c);
                } else if (c.isStopCard() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                } else if (c.isReverseCard() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                }
            }
            /* return hand.stream()
                       .filter(c -> 
                                    (c.isNormalCard() && c.getColor() == topCard.getColor()) ||
                                    (c.isPlusTwo()) ||
                                    (c.isStopCard() && c.getColor() == topCard.getColor()) ||
                                    (c.isReverseCard() && c.getColor() == topCard.getColor())
                              )
                        .findFirst();*/
        } else if (topCard.isStopCard()) {
            for (Card c : hand) {
                if (c.isNormalCard() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                } else if (c.isPlusTwo() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                } else if (c.isStopCard()) {
                    return Optional.of(c);
                } else if (c.isReverseCard() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                }
            }
            /*return hand.stream()
                    .filter(c
                            -> (c.isNormalCard() && c.getColor() == topCard.getColor())
                    || (c.isPlusTwo() && c.getColor() == topCard.getColor())
                    || (c.isStopCard())
                    || (c.isReverseCard() && c.getColor() == topCard.getColor())
                    )
                    .findFirst();*/
        } else if (topCard.isReverseCard()) {
            for (Card c : hand) {
                if (c.isNormalCard() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                } else if (c.isPlusTwo() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                } else if (c.isStopCard() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                } else if (c.isReverseCard()) {
                    return Optional.of(c);
                }
            }
            /*return hand.stream()
                    .filter(c
                            -> (c.isNormalCard() && c.getColor() == topCard.getColor())
                    || (c.isPlusTwo() && c.getColor() == topCard.getColor())
                    || (c.isStopCard() && c.getColor() == topCard.getColor())
                    || (c.isReverseCard())
                    )
                    .findFirst();*/
        } else if (topCard.isWildCard()) {
            for (Card c : hand) {
                if (c.isNormalCard() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                } else if (c.isPlusTwo() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                } else if (c.isStopCard() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                } else if (c.isReverseCard() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                } else if (c.isWildCard()) {
                    return Optional.of(c);
                } else if (c.isPlusFour()) {
                    return Optional.of(c);
                }
            }
            /*return hand.stream()
                    .filter(c
                            -> (c.isNormalCard() && c.getColor() == topCard.getColor())
                    || (c.isPlusTwo() && c.getColor() == topCard.getColor())
                    || (c.isStopCard() && c.getColor() == topCard.getColor())
                    || (c.isReverseCard() && c.getColor() == topCard.getColor())
                    || (c.isWildCard())
                    || (c.isPlusFour())
                    )
                    .findFirst();*/
        } else if (topCard.isPlusFour()) {
            for (Card c : hand) {
                if (c.isNormalCard() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                } else if (c.isPlusTwo() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                } else if (c.isStopCard() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                } else if (c.isReverseCard() && c.getColor() == topCard.getColor()) {
                    return Optional.of(c);
                } else if (c.isWildCard()) {
                    return Optional.of(c);
                } else if (c.isPlusFour()) {
                    return Optional.of(c);
                }
            }
            /* return hand.stream()
                    .filter(c
                            -> (c.isNormalCard() && c.getColor() == topCard.getColor())
                    || (c.isPlusTwo() && c.getColor() == topCard.getColor())
                    || (c.isStopCard() && c.getColor() == topCard.getColor())
                    || (c.isReverseCard() && c.getColor() == topCard.getColor())
                    || (c.isWildCard())
                    || (c.isPlusFour())
                    )
                    .findFirst();*/
        }

        return Optional.empty();
    }

    public static boolean hasPossibleValidMove(Card cardOnTop, List<Card> hand) {
        for (int i = 0; i < hand.size(); i++) {
            if (isValidMove(cardOnTop, hand.get(i), hand, true)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasPossibleValidMove(Card cardOnTop, List<Card> hand , boolean topCardActive) {
        for (int i = 0; i < hand.size(); i++) {
            if (isValidMove(cardOnTop, hand.get(i), hand, topCardActive)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasPossibleValidMove(Card cardOnTop, List<Card> hand, Color pickedColor, boolean topCardActive) {
        count = 0;
        validCards.clear();
        for (int i = 0; i < hand.size(); i++) {
            if (isValidMove(cardOnTop, hand.get(i), hand, pickedColor, topCardActive)) {
                System.out.println("Picked color is *************: " + pickedColor);
                count++;
                validCards.add(hand.get(i));
                //return true;
            }
        }
        if (count > 0) {
            System.out.println("valid cards in utils : " + validCards.size());
            return true;
        }
        return false;
    }

    public static int validCardsCount() {
        System.out.println("valid cards for me :::::::::::::::::::: " + count);
        return count;
    }

    public static List<Card> validCardsList() {
        return validCards;
    }
}
