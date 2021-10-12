/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.client.ui.custom;

import com.uno.client.ui.GameScreenController;
import com.uno.common.game.Card;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 *
 * @author
 */
public class HandView {

    private Pane pane;
    private Slider slider;
    private List<Card> cards;
    private List<Card> validCards;
    private List<CardView> views = new ArrayList<>();
    private CardPickListener listener;
    int y = 0;

    public HandView(Pane pane, Slider slider, CardPickListener listener) {
        this.pane = pane;
        this.slider = slider;
        this.listener = listener;
    }

    public HandView(Pane pane, CardPickListener listener) {
        this.pane = pane;
        this.listener = listener;
    }

    public void populate(List<Card> cards, boolean willAnimate) {

        pane.getChildren().clear();

        this.cards = new ArrayList<>();
        this.views = new ArrayList<>();

        for (int i = 0; i < cards.size(); i++) {
            Card c = cards.get(i);
            String url = "/com/uno/client/ui/images/" + c.toString() + ".png";
            CardView view = new CardView(url, i);
            this.cards.add(c);
            pane.getChildren().add(view);
            views.add(view);
        }

        slider.setMin(0);
        slider.setMax(cards.size() - 1);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setBlockIncrement(1);
        slider.setSnapToTicks(true);

        slider.translateXProperty().bind(pane.widthProperty().divide(2).subtract(slider.widthProperty().divide(2)));

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                for (CardView u : views) {
                    u.update(newValue.intValue(), pane.getWidth(), pane.getHeight());
                }

                //views.forEach(u -> u.update(newValue.intValue(), pane.getWidth(), pane.getHeight()));        
            }
        });

        slider.setValue(cards.size() % 2 == 0 ? cards.size() / 2 : cards.size() / 2 + 1);
//        if (cards.size() > 6)
//            slider.setValue(cards.size() - 6);
//        else
//            slider.setValue(1);

        for (CardView v : views) {
            v.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                System.out.println(((CardView) e.getTarget()).index + " clicked");

                if (e.getTarget() instanceof CardView) {

                    CardView view = (CardView) e.getTarget();
                    int index = view.index;

                    if (listener != null) {
                        listener.onCardPicked(cards.get(index), view, index);
                        /*ParallelTransition parallelTransition;
                        int msecondTime = 500;

                        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500));
                        translateTransition.setFromX(view.getLayoutX());
                        translateTransition.setFromY(view.getLayoutY());
                        translateTransition.setToX(-200);
                        translateTransition.setToY(-200);
                        translateTransition.setCycleCount(1);

                        RotateTransition rotateTransition = new RotateTransition(Duration.millis(500));
                        rotateTransition.setFromAngle(0);
                        rotateTransition.setToAngle(175);
                        rotateTransition.setCycleCount(1);

                        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500));
                        scaleTransition.setToX(1);
                        scaleTransition.setToY(1);
                        scaleTransition.setCycleCount(1);

                        parallelTransition = new ParallelTransition(view, translateTransition, scaleTransition, rotateTransition);
                        //parallelTransition.setCycleCount(1);
                        parallelTransition.play();*/
                    }
                }
            });
        }

        /*views.forEach(v -> v.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if(e.getTarget() instanceof CardView) {
                
                CardView view = (CardView)e.getTarget();
                int index = view.index;
                
                if (listener != null) {
                    listener.onCardPicked(cards.get(index), view, index);
                }
                
                System.out.println(((CardView)e.getTarget()).index + " clicked");
                
            }    
        }));*/
        for (CardView u : views) {
            u.update(1, pane.getWidth(), pane.getHeight());
        }

        //views.forEach(u -> u.update(1, pane.getWidth(), pane.getHeight()));    
    }

    public void populateOpponents(List<Card> cards, boolean willAnimate) {

        pane.getChildren().clear();

        this.cards = new ArrayList<>();
        this.views = new ArrayList<>();

        for (int i = 0; i < cards.size(); i++) {
            Card c = cards.get(i);
            String url = "/com/uno/client/ui/images/U.png";
            CardView view = new CardView(url, i);
            this.cards.add(c);
            view.setRotate(90);
            pane.getChildren().add(view);
            views.add(view);
        }

        /*slider.setMin(0);
        slider.setMax(cards.size() - 1);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setBlockIncrement(1);
        slider.setSnapToTicks(true);*/
        // slider.translateXProperty().bind(pane.widthProperty().divide(2).subtract(slider.widthProperty().divide(2)));
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                for (CardView u : views) {
                    u.updateOpponents(newValue.intValue(), pane.getWidth(), pane.getHeight());
                }

                //views.forEach(u -> u.update(newValue.intValue(), pane.getWidth(), pane.getHeight()));        
            }
        });

        slider.setValue(cards.size() % 2 == 0 ? cards.size() / 2 : cards.size() / 2 + 1);

        for (CardView v : views) {
            v.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getTarget() instanceof CardView) {

                    CardView view = (CardView) e.getTarget();
                    int index = view.index;

                    if (listener != null) {
                        listener.onCardPicked(cards.get(index), view, index);
                    }

                    System.out.println(((CardView) e.getTarget()).index + " clicked");

                }
            });
        }

        for (CardView u : views) {
            u.updateOpponents(1, pane.getWidth(), pane.getHeight());
        }

        //views.forEach(u -> u.update(1, pane.getWidth(), pane.getHeight()));    
    }

    public void populateWithValidity(List<Card> cards, List<Card> validCards, boolean willAnimate) {

        pane.getChildren().clear();

        this.cards = new ArrayList<>();
        this.validCards = new ArrayList<>();
        this.views = new ArrayList<>();   //list<CardView>

        System.out.println(" total valid cards in populateWithValidity:,.,..,. " + validCards.size());

        for (int i = 0; i < cards.size(); i++) {
            Card c = cards.get(i);
            String url = "/com/uno/client/ui/images/" + c.toString() + ".png";
            CardView view = new CardView(url, i);
            this.cards.add(c);
            if (validCards.contains(c)) {
                //view.setLayoutY(0);
                view.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
                    view.setScaleX(1.5);
                    view.setScaleY(1.5);
                });

                view.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
                    view.setScaleX(1);
                    view.setScaleY(1);
                });

            } else {
                view.setOpacity(0.3);
                //view.setLayoutY(50);
            }

            pane.getChildren().add(view);
            views.add(view);
        }

        slider.setMin(0);
        slider.setMax(cards.size() - 1);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setBlockIncrement(1);
        slider.setSnapToTicks(true);

        slider.translateXProperty().bind(pane.widthProperty().divide(2).subtract(slider.widthProperty().divide(2)));

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                for (CardView u : views) {
                    u.update(newValue.intValue(), pane.getWidth(), pane.getHeight());
                }

                //views.forEach(u -> u.update(newValue.intValue(), pane.getWidth(), pane.getHeight()));        
            }
        });

        slider.setValue(cards.size() % 2 == 0 ? cards.size() / 2 : cards.size() / 2 + 1);
//        if (cards.size() > 6)
//            slider.setValue(cards.size() - 6);
//        else
//            slider.setValue(1);

        for (CardView v : views) {
            v.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getTarget() instanceof CardView) {

                    CardView view = (CardView) e.getTarget();
                    int index = view.index;

                    if (listener != null) {
                        listener.onCardPicked(cards.get(index), view, index);
                    }

                    System.out.println(((CardView) e.getTarget()).index + " clicked");

                }
            });
        }

        /*views.forEach(v -> v.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if(e.getTarget() instanceof CardView) {
                
                CardView view = (CardView)e.getTarget();
                int index = view.index;
                
                if (listener != null) {
                    listener.onCardPicked(cards.get(index), view, index);
                }
                
                System.out.println(((CardView)e.getTarget()).index + " clicked");
                
            }    
        }));*/
        for (CardView u : views) {
            u.update(1, pane.getWidth(), pane.getHeight());
        }

        //views.forEach(u -> u.update(1, pane.getWidth(), pane.getHeight()));    
    }

    public void handleImages(CardView v) throws InterruptedException {
        v.setRotate(60);
        if (v.getLayoutY() <= 250) {
            v.setLayoutX(750);
            v.setLayoutY(y);
            y += 50;
            Thread.sleep(200);
            handleImages(v);
        } else {
            v.setLayoutY(750);
            v.setLayoutY(278);
            return;
        }
    }

}
