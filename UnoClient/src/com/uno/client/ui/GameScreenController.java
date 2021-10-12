/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.client.ui;

import com.uno.client.game.GameEngine;
import com.uno.client.game.GameRequestDispatcher;
import com.uno.client.game.UnoClientSubscriber;

import com.uno.common.commands.MoveCommand;
import com.uno.common.game.Card;
import com.uno.common.game.GameStatus;
import com.uno.common.game.PlayerStatus;
import com.uno.common.game.UnoUtils;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import com.uno.client.ui.custom.HandView;
import java.util.Optional;
import javafx.scene.control.Slider;
import javafx.scene.layout.Region;
import com.uno.client.ui.custom.CardPickListener;
import com.uno.client.ui.custom.CardView;
import com.uno.common.game.Color;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

/**
 * FXML Controller class
 *
 * @author
 */
public class GameScreenController extends UnoGameBaseViewController implements Initializable, UnoClientSubscriber, CardPickListener {

    private static final String KEY = "game_screen";

    @FXML
    private Label lblWaiting;

    @FXML
    private Pane cardPane;

    @FXML
    private Slider slider;

    @FXML
    private ImageView imgMoveZone;

    @FXML
    private Button btnPass;

    @FXML
    private Button btnDraw;

    @FXML
    private Button btnNoMove;

    @FXML
    private Button btnPenalty;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblTotal;

    private HandView handView;
    //private HandView handView1;
    //private HandView handView2;
    //private HandView opponhandviws[] = new HandView[5];

    private boolean hasGameStarted = false;
    private boolean isMyTurn = false;
    private MoveCommand moveCommand;
    String s = null;
    String addallstrings = null;
    Optional<PlayerStatus> optMyself = null;
    @FXML
    private Font x1;
    @FXML
    private ImageView pileimage;
    @FXML
    private ImageView oponimage1;
    @FXML
    private ImageView oponimage2;
    @FXML
    private ImageView oponimage3;
    @FXML
    private ImageView oponimage4;
    @FXML
    private ImageView oponimage5;
    @FXML
    private ImageView oponimage6;
    @FXML
    private ImageView oponimage7;
    @FXML
    private ImageView oponimage8;
    @FXML
    private ImageView oponimage9;
    @FXML
    private ImageView oponimage10;
    @FXML
    private ImageView oponimage11;
    @FXML
    private ImageView oponimage12;
    @FXML
    private ImageView oponimage13;
    @FXML
    private ImageView oponimage14;
    @FXML
    private ImageView oponimage15;
    @FXML
    private ImageView oponimage16;
    @FXML
    private ImageView oponimage17;
    @FXML
    private ImageView oponimage18;
    @FXML
    private ImageView oponimage19;
    @FXML
    private ImageView oponimage20;
    @FXML
    private ImageView oponimage21;
    @FXML
    private ImageView oponimage22;
    @FXML
    private ImageView oponimage23;
    @FXML
    private ImageView oponimage24;
    @FXML
    private ImageView oponimage25;
    @FXML
    private ImageView oponimage26;
    @FXML
    private ImageView oponimage27;
    @FXML
    private ImageView oponimage28;
    @FXML
    private ImageView oponimage29;
    @FXML
    private ImageView oponimage30;

    ImageView[] images = new ImageView[80];
    @FXML
    private Label labelname2;
    @FXML
    private Font x2;
    @FXML
    private Label labelname1;
    @FXML
    private Label labname0;
    @FXML
    private Label labelname3;
    @FXML
    private ImageView oponimage31;
    @FXML
    private ImageView oponimage32;
    @FXML
    private ImageView oponimage33;
    @FXML
    private ImageView oponimage34;
    @FXML
    private ImageView oponimage35;
    @FXML
    private ImageView oponimage36;
    @FXML
    private ImageView oponimage37;
    @FXML
    private ImageView oponimage38;
    @FXML
    private ImageView oponimage39;
    @FXML
    private ImageView oponimage40;
    @FXML
    private ImageView oponimage41;
    @FXML
    private ImageView oponimage42;
    @FXML
    private ImageView oponimage43;
    @FXML
    private ImageView oponimage44;
    @FXML
    private ImageView oponimage45;
    @FXML
    private ImageView oponimage46;
    @FXML
    private ImageView oponimage47;
    @FXML
    private ImageView oponimage48;
    @FXML
    private ImageView oponimage49;
    @FXML
    private ImageView oponimage50;
    @FXML
    private ImageView oponimage51;
    @FXML
    private ImageView oponimage52;
    @FXML
    private ImageView oponimage53;
    @FXML
    private ImageView oponimage54;
    @FXML
    private ImageView oponimage55;
    @FXML
    private ImageView oponimage56;
    @FXML
    private ImageView oponimage57;
    @FXML
    private ImageView oponimage58;
    @FXML
    private ImageView oponimage59;
    @FXML
    private ImageView oponimage60;
    @FXML
    private Label labelname4;

    List<Card> validCards = new ArrayList<Card>();
    //private Pane opponentpane1;
    //private Pane opponentpane2;
    @FXML
    private ProgressIndicator progressbar;
    double progress = 30;
    int unoflag = 0;
    String playerWhoHasCalledUno;
    int playerWhoHasCalledUnoIndex;
    String unoString;
    String mouseClick = "src\\com\\uno\\client\\ui\\sounds\\Mouse Click.mp3";
    String unoSound = "src\\com\\uno\\client\\ui\\sounds\\uno.mp3";
    String musicFile = "src\\com\\uno\\client\\ui\\sounds\\uno.mp3";
    Color pickedColor;
    Color manage;
    @FXML
    AnchorPane anchorpane;
    @FXML
    private Button sayuno;
    @FXML
    private Label colorLabel;
    @FXML
    private Label unoLabel;
    @FXML
    private Label waitinglabel;
    @FXML
    private ImageView oponimage61;
    @FXML
    private ImageView oponimage62;
    @FXML
    private ImageView oponimage63;
    @FXML
    private ImageView oponimage64;
    @FXML
    private ImageView oponimage65;
    @FXML
    private ImageView oponimage66;
    @FXML
    private ImageView oponimage67;
    @FXML
    private ImageView oponimage68;
    @FXML
    private ImageView oponimage69;
    @FXML
    private ImageView oponimage70;
    @FXML
    private ImageView oponimage71;
    @FXML
    private ImageView oponimage72;
    @FXML
    private ImageView oponimage73;
    @FXML
    private ImageView oponimage74;
    @FXML
    private ImageView oponimage75;
    @FXML
    private ImageView oponimage76;
    @FXML
    private ImageView oponimage77;
    @FXML
    private ImageView oponimage78;
    @FXML
    private ImageView oponimage79;
    @FXML
    private ImageView oponimage80;
    @FXML
    private Font x4;
    @FXML
    private Font x3;
    @FXML
    private javafx.scene.paint.Color x5;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        images[0] = oponimage1;
        images[1] = oponimage2;
        images[2] = oponimage3;
        images[3] = oponimage4;
        images[4] = oponimage5;
        images[5] = oponimage6;
        images[6] = oponimage7;
        images[7] = oponimage8;
        images[8] = oponimage9;
        images[9] = oponimage10;
        images[10] = oponimage11;
        images[11] = oponimage12;
        images[12] = oponimage13;
        images[13] = oponimage14;
        images[14] = oponimage15;
        images[15] = oponimage16;
        images[16] = oponimage17;
        images[17] = oponimage18;
        images[18] = oponimage19;
        images[19] = oponimage20;
        images[20] = oponimage21;
        images[21] = oponimage22;
        images[22] = oponimage23;
        images[23] = oponimage24;
        images[24] = oponimage25;
        images[25] = oponimage26;
        images[26] = oponimage27;
        images[27] = oponimage28;
        images[28] = oponimage29;
        images[29] = oponimage30;
        images[30] = oponimage31;
        images[31] = oponimage32;
        images[32] = oponimage33;
        images[33] = oponimage34;
        images[34] = oponimage35;
        images[35] = oponimage36;
        images[36] = oponimage37;
        images[37] = oponimage38;
        images[38] = oponimage39;
        images[39] = oponimage40;
        images[40] = oponimage41;
        images[41] = oponimage42;
        images[42] = oponimage43;
        images[43] = oponimage44;
        images[44] = oponimage45;
        images[45] = oponimage46;
        images[46] = oponimage47;
        images[47] = oponimage48;
        images[48] = oponimage49;
        images[49] = oponimage50;
        images[50] = oponimage51;
        images[51] = oponimage52;
        images[52] = oponimage53;
        images[53] = oponimage54;
        images[54] = oponimage55;
        images[55] = oponimage56;
        images[56] = oponimage57;
        images[57] = oponimage58;
        images[58] = oponimage59;
        images[59] = oponimage60;
        images[60] = oponimage61;
        images[61] = oponimage62;
        images[62] = oponimage63;
        images[63] = oponimage64;
        images[64] = oponimage65;
        images[65] = oponimage66;
        images[66] = oponimage67;
        images[67] = oponimage68;
        images[68] = oponimage69;
        images[69] = oponimage70;
        images[70] = oponimage71;
        images[71] = oponimage72;
        images[72] = oponimage73;
        images[73] = oponimage74;
        images[74] = oponimage75;
        images[75] = oponimage76;
        images[76] = oponimage77;
        images[77] = oponimage78;
        images[78] = oponimage79;
        images[79] = oponimage80;

        for (int i = 0; i < 7; i++) {
            images[i].setVisible(false);
        }
        for (int i = 7; i < 15; i++) {
            images[i].setVisible(false);
        }
        for (int i = 15; i < 22; i++) {
            images[i].setVisible(false);
        }
        for (int i = 22; i < 30; i++) {
            images[i].setVisible(false);
        }
        for (int i = 30; i < 37; i++) {
            images[i].setVisible(false);
        }
        for (int i = 37; i < 80; i++) {
            images[i].setVisible(false);
        }
        sayuno.setVisible(false);
        unoLabel.setVisible(false);
        pileimage.setVisible(false);
        GameEngine.getInstance().subscribe(KEY, this);
        handView = new HandView(cardPane, slider, this);
        //opponhandviws[0] = new HandView(opponentpane1, this);
        //opponhandviws[1] = new HandView(opponentpane2, this);

        //handView1 = new HandView(opponentpane1, this);
        //handView2 = new HandView(opponentpane2, this);
        lblWaiting.setLayoutX(0);
        lblWaiting.setLayoutY(0);

        lblWaiting.setMinWidth(ScreenSwitcher.WINDOW_WIDTH);
        lblWaiting.setMinHeight(ScreenSwitcher.WINDOW_HEIGHT);

        slider.setVisible(false);
        imgMoveZone.setVisible(false);
        // imgMoveZone.setLayoutX(ScreenSwitcher.WINDOW_WIDTH / 2 - imgMoveZone.getFitWidth() / 2);
        // imgMoveZone.setLayoutY(ScreenSwitcher.WINDOW_HEIGHT / 2 - imgMoveZone.getFitHeight() / 2);

        //lblStatus.setMinWidth(ScreenSwitcher.WINDOW_WIDTH);
        //lblStatus.setLayoutX(ScreenSwitcher.WINDOW_WIDTH / 2 - lblStatus.getWidth() / 2);
        lblStatus.setText("");

        //lblTotal.setLayoutX(ScreenSwitcher.WINDOW_WIDTH / 2 - lblTotal.getWidth() / 2 - 20);
        //lblTotal.setLayoutY(lblStatus.getLayoutY() + lblStatus.getHeight() + 20);
        hideAllButtons();

        btnPass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameEngine.getInstance().sendPass();
                hideAllButtons();
                isMyTurn = false;
                lblStatus.setText("I passed my turn !!");
            }
        });

        pileimage.setOnMouseClicked((event) -> {
            GameEngine.getInstance().sendDrawCard();
            hideAllButtons();
            isMyTurn = false;
        });

        pileimage.setOnMouseEntered((event) -> {
            pileimage.setScaleX(1.5);
            pileimage.setScaleY(1.5);
        });

        pileimage.setOnMouseExited((event) -> {
            pileimage.setScaleX(1);
            pileimage.setScaleY(1);
        });

        btnDraw.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameEngine.getInstance().sendDrawCard();
                hideAllButtons();
                isMyTurn = false;
            }
        });

        btnNoMove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameEngine.getInstance().sendRelinquish();
                hideAllButtons();
                isMyTurn = false;
            }
        });

        btnPenalty.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameEngine.getInstance().sendAcceptPenalty();
                hideAllButtons();
                isMyTurn = false;
            }
        });

//        slider.setStyle("-fx-background-color: #808080");
//        lblTotal.setStyle("-fx-background-color: #808080");
    }

    private void hideAllButtons() {
        btnPass.setVisible(false);
        btnDraw.setVisible(false);
        btnNoMove.setVisible(false);
        btnPenalty.setVisible(false);
        //colorLabel.setVisible(false);
        //pileimage.setVisible(true);
    }

    @Override
    public String getKey() {
        return KEY;
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

    public void setInitialState(List<PlayerStatus> currentPlayers, int total) {
        progress = 0;
        addallstrings = "";
        s = "";
        for (PlayerStatus p : currentPlayers) {
            s = String.format("%s (%s) joined the table", p.getName(), p.getId());
            addallstrings = addallstrings + s;
            addallstrings = addallstrings + "\n";
            progress++;
        }
        //final String s1 = currentPlayers.stream().map(p -> String.format("%s (%s) joined the table", p.getName(), p.getId())).collect(Collectors.joining("\n"));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //lblWaiting.setText(addallstrings);
                progressbar.setVisible(true);
            }
        });
    }

    @Override
    public void onGameStatusUpdated(GameStatus status) {
        if (!hasGameStarted) {
            for (PlayerStatus p : status.getPlayers()) {
                if (!p.getHand().get(0).equals(Card.unknownCard())) {
                    optMyself = Optional.of(p);
                    break;
                }
            }

            //Optional<PlayerStatus> optMyself = status.getPlayers().stream().filter(p -> !p.getHand().get(0).equals(Card.unknownCard())).findFirst();
            if (optMyself.isPresent()) {
                s = "";
                for (Card c : optMyself.get().getHand()) {
                    s = s + c.toString();
                    s = s + ",";
                }
                System.out.println(s);

                //System.out.println(optMyself.get().getHand().stream().map(Card::toString).collect(Collectors.joining(",")));
                Platform.runLater(new Runnable() {
                    public void run() {
                        lblWaiting.setVisible(false);
                        progressbar.setVisible(false);
                        imgMoveZone.setImage(new Image("/com/uno/client/ui/images/" + status.getTopCard().toString() + ".png"));
                        imgMoveZone.setVisible(true);
                        handView.populate(optMyself.get().getHand(), true);

                        //btnPass.setLayoutY(cardPane.getLayoutY() - btnPass.getHeight() - 80);
                        btnDraw.setLayoutY(cardPane.getLayoutY() - btnDraw.getHeight() - 80);
                        btnNoMove.setLayoutY(cardPane.getLayoutY() - btnPass.getHeight() - 80);
                        btnPenalty.setLayoutY(cardPane.getLayoutY() - btnPass.getHeight() - 80);

                        //btnPass.setLayoutX(ScreenSwitcher.WINDOW_WIDTH / 2 - btnPass.getWidth() - 20);
                        btnDraw.setLayoutX(btnPass.getLayoutX() - btnDraw.getWidth() - 20);
                        btnNoMove.setLayoutX(ScreenSwitcher.WINDOW_WIDTH / 2 + btnPass.getWidth() + 20);
                        btnPenalty.setLayoutX(btnNoMove.getLayoutX() + btnNoMove.getWidth() + 20);
                        ScreenSwitcher.getInstance().setTitle("Playing as : " + optMyself.get().getName());
                        slider.setLayoutX(0);
                        slider.setLayoutY(cardPane.getLayoutY() - slider.getHeight() - 60);
                        slider.setVisible(true);

                    }
                });

            }
            hasGameStarted = true;
            waitinglabel.setVisible(false);
        }
        //manage = pickedColor;
        StringBuilder sb = new StringBuilder();

        int handsize[] = new int[5];
        String username[] = new String[5];
        int userid[] = new int[5];
        int i = 0;

        System.out.println("==========================================");
        unoString = "";
        unoflag = 0;
        //sayuno.setVisible(false);

        for (PlayerStatus p : status.getPlayers()) {
            String hand = null;
            sb.append(String.format("%s (%s)    %2d", p.getName(), p.getId(), p.getHand().size())).append("\n");
            handsize[i] = p.getHand().size();
            username[i] = p.getName();
            userid[i] = p.getId();
            if (handsize[i] == 1) {
                unoflag = 1;
                playerWhoHasCalledUno = username[i];
                playerWhoHasCalledUnoIndex = userid[i];
                unoString = unoString + username[i];
                unoString = unoString + "(";
                unoString = unoString + userid[i];
                unoString = unoString + ")";
                unoString = unoString + " has called uno !!\n";
            }
            if (i == 0) {
                // opponhandviws[i] = new HandView(opponentpane1, this);
                //opponhandviws[i].populateOpponents(p.getHand(), false);
            }
            if (i == 1) {
                // opponhandviws[i] = new HandView(opponentpane2, this);
                // opponhandviws[i].populateOpponents(p.getHand(), false);
            }

            /*System.out.println("handsize[%d] is "+i+handsize[i]);
            System.out.println("username[%d] is "+i+username[i]);
            System.out.println("userid[%d] is "+i+userid[i]);*/
            i++;

            for (Card c : p.getHand()) {
                hand = hand + c.toString();
                hand = hand + ",";
            }
            System.out.println(String.format("Player %s (%s) hands : [ %s ]", p.getName(), p.getId(), hand));
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (unoflag == 1) {
                    unoLabel.setVisible(true);
                    unoLabel.setText(unoString);
                    //unoflag = 0;
                }
            }
        });

        //i = 0;
        int totalplayers = i;
        if (totalplayers == 2) {
            for (int k = 0; k < totalplayers; k++) {
                if (optMyself.get().getId() != userid[k]) {
                    final int index = k;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            labelname3.setText(username[index]);
                            /*if (unoflag == 1) {
                                unoLabel.setVisible(true);
                                unoLabel.setText(unoString);
                                unoflag = 0;
                            }*/
                        }
                    });

                    for (int j = handsize[userid[k] - 1] + 60; j < 80; j++) {
                        images[j].setVisible(false);
                    }
                    for (int j = 60; j < handsize[userid[k] - 1] + 60; j++) {
                        if (j >= 80) {
                            break;
                        }
                        images[j].setVisible(true);
                    }
                } else {
                    final int index = k;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            labname0.setText(username[index]);
                        }
                    });
                    System.out.println("it is you.....................");
                }
            }
        } else if (totalplayers < 5) {
            int p = 0;
            for (int k = 0; k < totalplayers; k++) {
                if (optMyself.get().getId() != userid[k]) {
                    if (p == 0) {
                        final int index = k;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                labelname1.setText(username[index]);
                                /*if (unoflag == 1) {
                                    unoLabel.setVisible(true);
                                    unoLabel.setText(unoString);
                                    unoflag = 0;
                                }*/
                            }
                        });

                        for (int j = handsize[userid[k] - 1]; j < 20; j++) {
                            images[j].setVisible(false);
                        }
                        for (int j = 0; j < handsize[userid[k] - 1]; j++) {
                            if (j >= 20) {
                                break;
                            }
                            images[j].setVisible(true);
                        }
                        p++;
                    } else if (p == 2) {
                        final int index = k;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                labelname2.setText(username[index]);
                            }
                        });
                        for (int j = handsize[userid[k] - 1] + 20; j < 40; j++) {
                            images[j].setVisible(false);
                        }
                        for (int j = 20; j < handsize[userid[k] - 1] + 20; j++) {
                            if (j >= 40) {
                                break;
                            }
                            images[j].setVisible(true);
                        }
                        p++;
                    } else if (p == 1) {
                        final int index = k;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                labelname3.setText(username[index]);
                            }
                        });
                        for (int j = handsize[userid[k] - 1] + 60; j < 80; j++) {
                            images[j].setVisible(false);
                        }
                        for (int j = 60; j < handsize[userid[k] - 1] + 60; j++) {
                            if (j >= 80) {
                                break;
                            }
                            images[j].setVisible(true);
                        }
                        p++;
                    }
                } else {
                    if (handsize[k] == 2) {
                        //if (UnoUtils.hasPossibleValidMove(moveCommand.getCurrentCard(), moveCommand.getCurrentHand())) {
                        //sayuno.setVisible(true);
                        System.out.println("+++++++++++ uno");
                        //}
                    }
                    final int index = k;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            labname0.setText(username[index]);
                        }
                    });
                    System.out.println("it is you.....................");
                }
            }
        } else {
            int p = 0;
            for (int k = 0; k < totalplayers; k++) {
                if (optMyself.get().getId() != userid[k]) {
                    if (p == 0) {
                        final int index = k;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //labelname1.setLayoutX(10);
                                // labelname1.setLayoutY(870);
                                labelname1.setText(username[index]);
                            }
                        });

                        for (int j = handsize[userid[k] - 1]; j < 20; j++) {
                            //setDimension(p + 1);
                            images[j].setVisible(false);
                        }
                        for (int j = 0; j < handsize[userid[k] - 1]; j++) {
                            //setDimension(p + 1);
                            if (j >= 20) {
                                break;
                            }
                            images[j].setVisible(true);
                        }
                        p++;
                    } else if (p == 3) {
                        final int index = k;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //labelname4.setLayoutX(1280);
                                //labelname4.setLayoutY(870);
                                labelname2.setText(username[index]);
                            }
                        });
                        for (int j = handsize[userid[k] - 1] + 20; j < 40; j++) {
                            //setDimension(p + 1);
                            images[j].setVisible(false);
                        }
                        for (int j = 20; j < handsize[userid[k] - 1] + 20; j++) {
                            // setDimension(p + 1);
                            if (j >= 40) {
                                break;
                            }
                            images[j].setVisible(true);
                        }
                        p++;
                    } else if (p == 1) {
                        final int index = k;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //labelname2.setLayoutX(100);
                                //labelname2.setLayoutY(30);
                                labelname4.setText(username[index]);
                            }
                        });
                        for (int j = handsize[userid[k] - 1] + 40; j < 60; j++) {
                            //setDimension(p + 1);
                            images[j].setVisible(false);
                        }
                        for (int j = 40; j < handsize[userid[k] - 1] + 40; j++) {
                            // setDimension(p + 1);
                            if (j >= 60) {
                                break;
                            }
                            images[j].setVisible(true);
                        }
                        p++;
                    } else if (p == 2) {
                        final int index = k;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //labelname3.setLayoutX(1200);
                                //labelname3.setLayoutY(30);
                                labelname3.setText(username[index]);
                            }
                        });
                        for (int j = handsize[userid[k] - 1] + 60; j < 80; j++) {
                            //setDimension(p + 1);
                            images[j].setVisible(false);
                        }
                        for (int j = 60; j < handsize[userid[k] - 1] + 60; j++) {
                            //setDimension(p + 1);
                            if (j >= 80) {
                                break;
                            }
                            images[j].setVisible(true);
                        }
                        p++;
                    }
                } else {
                    final int index = k;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            labname0.setText(username[index]);
                        }
                    });
                    System.out.println("it is you.....................");
                }
            }
        }

        /*status.getPlayers().forEach(p -> {
            sb.append(String.format("%s (%s)    %2d", p.getName(), p.getId(), p.getHand().size())).append("\n");
            String hand = p.getHand().stream().map(c -> c.toString()).collect(Collectors.joining(","));
            System.out.println(String.format("Player %s (%s) hands : [ %s ]", p.getName(), p.getId(), hand));
        });*/
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                //lblTotal.setText(sb.toString());
            }
        });
        System.out.println("==========================================");

    }

    /*public void setDimensionfor234(int k, int players) {
        if (players == 3) {
            if (k == 1) {
                int x = 0;
                int y = 350;
                for (int i = 0; i < 15; i++) {
                    images[i].setLayoutX(x);
                    images[i].setLayoutY(y);
                    //x += 30;
                    y += 30;
                }
            } else if (k == 2) {
                int x = 0;
                int y = 350;
                for (int i = 0; i < 15; i++) {
                    images[i].setLayoutX(x);
                    images[i].setLayoutY(y);
                    //x += 30;
                    y += 30;
                }
            }
        } else if (players == 4) {
            if (k == 1) {
                int x = 0;
                int y = 350;
                for (int i = 0; i < 15; i++) {
                    images[i].setLayoutX(x);
                    images[i].setLayoutY(y);
                    //x += 30;
                    y += 30;
                }
            } else if (k == 2) {
                int x = 0;
                int y = 350;
                for (int i = 0; i < 15; i++) {
                    images[i].setLayoutX(x);
                    images[i].setLayoutY(y);
                    //x += 30;
                    y += 30;
                }
            } else if (k == 3) {
                int x = 0;
                int y = 350;
                for (int i = 0; i < 15; i++) {
                    images[i].setLayoutX(x);
                    images[i].setLayoutY(y);
                    //x += 30;
                    y += 30;
                }
            }
        }

    }*/
    public void setDimension(int k) {
        if (k == 1) {
            int x = 0;
            int y = 350;
            for (int i = 0; i < 15; i++) {
                images[i].setLayoutX(x);
                images[i].setLayoutY(y);
                //x += 30;
                y += 30;
            }
        } else if (k == 2) {
            int x = 200;
            int y = 200;
            for (int i = 30; i < 45; i++) {
                images[i].setRotate(330);
                images[i].setLayoutX(x);
                images[i].setLayoutY(y);
                x += 20;
                y -= 20;
            }
        } else if (k == 3) {
            int x = 900;
            int y = 0;
            for (int i = 45; i < 60; i++) {
                images[i].setRotate(240);
                images[i].setLayoutX(x);
                images[i].setLayoutY(y);
                x += 20;
                y += 20;
            }
        } else if (k == 4) {
            int x = 1300;
            int y = 350;
            for (int i = 15; i < 30; i++) {
                images[i].setLayoutX(x);
                images[i].setLayoutY(y);
                //x += 30;
                y += 30;
            }
        }
    }

    @Override
    public void onWaitingForPlayers(List<PlayerStatus> currentPlayers) {
        addallstrings = "";
        s = "";
        for (PlayerStatus p : currentPlayers) {
            s = String.format("%s (%s) joined the table", p.getName(), p.getId());
            addallstrings = addallstrings + s;
            addallstrings = addallstrings + "\n";
        }

        //final String s1 = currentPlayers.stream().map(p -> String.format("%s (%s) joined the table", p.getName(), p.getId())).collect(Collectors.joining("\n"));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lblWaiting.setText(addallstrings);
                progressbar.setVisible(true);
            }
        });

    }

    @Override
    public void onNewPlayersJoin(List<PlayerStatus> newPlayers) {
        addallstrings = "";
        s = "";
        for (PlayerStatus p : newPlayers) {
            s = String.format("%s (%s) joined the table", p.getName(), p.getId());
            addallstrings = addallstrings + s;
            addallstrings = addallstrings + "\n";
        }
        //final String s1 = newPlayers.stream().map(p -> String.format("%s (%s) joined the table", p.getName(), p.getId())).collect(Collectors.joining("\n"));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lblWaiting.setText(lblWaiting.getText() + "\n" + addallstrings);
                progressbar.setVisible(true);

            }
        });

        /* TODO: update ui add a new place for a player,  add his name etc etc */
    }

    @Override
    public void onDealingCards() {
        /* TODO: show indicator on screen that cards are being distributed to the players */
        System.out.println("Server is shuffling cards and distributing among players");
        Platform.runLater(new Runnable() {
            public void run() {
                lblWaiting.setText("Shuffling and dealing cards");
            }
        });
    }

    @Override
    public void onMoveCommand(MoveCommand command) {
        //List<Card> validCards = new ArrayList<Card>();
        isMyTurn = true;
        moveCommand = command;
        pileimage.setVisible(true);
        System.out.println("top card active is???......?? " + moveCommand.isIsTopCardActive());
        /*int validCount = 0;
        if (UnoUtils.hasPossibleValidMove(moveCommand.getCurrentCard(), moveCommand.getCurrentHand(), pickedColor, moveCommand.isIsTopCardActive())) {
            validCards = UnoUtils.validCardsList();
            validCount = UnoUtils.validCardsCount();
        }

        System.out.println("valid cards in gamescreencontroller is  " + validCards.size());*/

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                int validCount = 0;
                if (UnoUtils.hasPossibleValidMove(moveCommand.getCurrentCard(), moveCommand.getCurrentHand(), moveCommand.getCurrentCard().getColor(), moveCommand.isIsTopCardActive())) {
                    validCards = UnoUtils.validCardsList();
                    validCount = UnoUtils.validCardsCount();
                }
                lblStatus.setText("Hey !! Its your turn");
                s = "";
                for (Card c : moveCommand.getCurrentHand()) {
                    s = s + c.toString();
                    s = s + ",";
                }
                System.out.println("updating with: " + s);
                System.out.println("valid cards in gamescreencontroller before passing is  " + validCards.size());
                //System.out.println("updating with: " + moveCommand.getCurrentHand().stream().map(Card::toString).collect(Collectors.joining(",")));
                handView.populateWithValidity(moveCommand.getCurrentHand(), validCards, false);
            }
        });

        /*Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lblStatus.setText("Hey !! Its your turn");
            }
        });*/
        boolean hasValidMove = command.isIsTopCardActive()
                ? UnoUtils.hasPossibleValidMove(command.getCurrentCard(), command.getCurrentHand())
                : UnoUtils.hasPossibleValidMove(command.getCurrentCard(), command.getCurrentHand(), command.getCurrentCard().getColor(), command.isIsTopCardActive());

        //if (!UnoUtils.hasPossibleValidMove(command.getCurrentCard(), command.getCurrentHand())) {
        if (!hasValidMove) {
            if (command.isIsTopCardActive() && (command.getCurrentCard().isPlusFour() || command.getCurrentCard().isPlusTwo())) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        GameEngine.getInstance().sendAcceptPenalty();
                        isMyTurn = false;
                        hideAllButtons();
                    }
                });
            } else {

                if (command.isIsDrawAllowed()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //btnDraw.setVisible(true);
                            pileimage.setVisible(true);
                        }
                    });
                } else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            GameEngine.getInstance().sendRelinquish();

                        }
                    });
                }

            }

        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if(command.getCurrentCard().isPlusFour() || command.getCurrentCard().isPlusTwo())
                    {
                        if(command.isIsTopCardActive())
                        {
                            btnPass.setVisible(false);
                        }
                    }
                    
                    else
                    {
                        btnPass.setVisible(true);
                    }
                    
                }
            });
        }
    }

    @Override
    public void onGameFinished(PlayerStatus player) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Game Ended");
                alert.getDialogPane().setContentText("Winner is " + player.getName() + " (" + player.getId() + ")");
                alert.showAndWait();
            }

        });
    }

    @Override
    public void onCardPlayed(PlayerStatus player, Card card, boolean isMe) {
        Media sound = new Media(new File(mouseClick).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        colorLabel.setVisible(false);
        unoLabel.setVisible(false);
        if (!isMe) {
            System.out.println(String.format("Player %s (%s) played card : %s (%s)", player.getName(), player.getId(), card.toString(), card.getColor()));
            Platform.runLater(new Runnable() {
                public void run() {
                    /* if (unoflag == 1) {
                        unoLabel.setVisible(true);
                        unoLabel.setText(unoString);
                        //unoflag = 0;
                    }*/
                    if (card.isPlusFour() || card.isWildCard()) {
                        colorLabel.setVisible(true);
                        colorLabel.setText(player.getName() + "(" + player.getId() + ")" + " has set color " + card.getColor());
                    }
                    lblStatus.setText(String.format("Player %s (%s) played card : %s (%s)", player.getName(), player.getId(), card.toString(), card.getColor()));
                    imgMoveZone.setImage(new Image("/com/uno/client/ui/images/" + card.toString() + ".png"));
                }
            });
        } else {
            System.out.println("I played " + card);
        }

    }

    @Override
    public void onCardDrawn(PlayerStatus player, boolean isMe) {
        Media sound = new Media(new File(mouseClick).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        String hand = "";
        for (Card c : player.getHand()) {
            hand = hand + c.toString();
            hand = hand + ",";
        }

        //String hand = player.getHand().stream().map(c -> c.toString()).collect(Collectors.joining(","));
        System.out.println(String.format("Player %s (%s) hands : [ %s ]", player.getName(), player.getId(), hand));

        if (!isMe) {
            System.out.println(String.format("Player %s (%s) drew a card", player.getName(), player.getId()));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    //handView1.populateOpponents(player.getHand(), false);
                    lblStatus.setText(String.format("Player %s (%s) drew a card", player.getName(), player.getId()));
                }
            });
        } else {
            System.out.println("I drew a card");
            Platform.runLater(new Runnable() {
                public void run() {
                    System.out.println("New Hand of Player : " + player.getHand().size());
                    handView.populate(player.getHand(), false);
                    lblStatus.setText("I drew a card");
                }
            });

        }
    }

    @Override
    public void onPassed(PlayerStatus player, boolean isMe) {
        if (!isMe) {
            System.out.println(String.format("Player %s (%s) passed", player.getName(), player.getId()));
            Platform.runLater(new Runnable() {
                public void run() {
                    lblStatus.setText(String.format("Player %s (%s) passed", player.getName(), player.getId()));
                }
            });
        } else {
            System.out.println("I passed");
            Platform.runLater(new Runnable() {
                public void run() {
                    lblStatus.setText("I passed");
                }
            });
        }
    }

    @Override
    public void onAcceptPenalty(PlayerStatus player, int count, boolean isMe) {
        if (!isMe) {
            System.out.println(String.format("Player %s (%s) drew a card", player.getName(), player.getId()));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    //handView.populateOpponents(player.getHand(), false);
                    lblStatus.setText(String.format("Player %s (%s) drew %d cards due to penalty", player.getName(), player.getId(), count));
                }
            });
        } else {
            System.out.println("I drew " + count + " cards due to penalty");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    handView.populate(player.getHand(), false);
                    lblStatus.setText("I drew " + count + " cards due to penalty");
                }

            });

        }
    }

    @Override
    public void onCardPicked(Card card, CardView view, int index) {
        if (!isMyTurn) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No No No !!!");
            alert.getDialogPane().setContentText("Not My Turn");
            alert.showAndWait();
        } else {

            System.out.println("==========================================");

            System.out.println("I picked " + card.toString() + " (" + index + ")");
            String suffix = "Top card is : " + (moveCommand.isIsTopCardActive() ? "active" : "inactive");
            if (moveCommand.getCurrentCard().isPlusFour() || moveCommand.getCurrentCard().isWildCard()) {
                suffix = "(" + moveCommand.getCurrentCard().getColor().name().substring(0, 1) + ") " + suffix;
            }

            System.out.println("I am supposed to make a move over " + moveCommand.getCurrentCard() + " " + suffix);

            s = "";
            for (Card c : moveCommand.getCurrentHand()) {
                s = s + c.toString();
                s = s + ", ";
            }
            System.out.println("My Hand : [ " + s + " ]");
            //System.out.println("My Hand : [ " + moveCommand.getCurrentHand().stream().map(c -> c.toString()).collect(Collectors.joining(", ")) + " ]");
            System.out.println("I want to play " + card);

            /*int validCount = 0;
            if (UnoUtils.hasPossibleValidMove(moveCommand.getCurrentCard(), moveCommand.getCurrentHand(), moveCommand.isIsTopCardActive())) {
                validCards = UnoUtils.validCardsList();
                validCount = UnoUtils.validCardsCount();
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    s = "";
                    for (Card c : moveCommand.getCurrentHand()) {
                        s = s + c.toString();
                        s = s + ",";
                    }
                    System.out.println("updating with: " + s);
                    //System.out.println("updating with: " + moveCommand.getCurrentHand().stream().map(Card::toString).collect(Collectors.joining(",")));
                    handView.populateWithValidity(moveCommand.getCurrentHand(), validCards, false);
                }
            });*/
            if (UnoUtils.isValidMove(moveCommand.getCurrentCard(), card, moveCommand.getCurrentHand(), moveCommand.isIsTopCardActive())) {
                isMyTurn = false;
                if (card.isPlusFour() || card.isWildCard()) {
                    card.setColor(UnoUtils.getMostFrequentColor(moveCommand.getCurrentHand()));
                    /* TODO: enable player to call color */
                    System.out.println("I am calling color : " + card.getColor().name());

                    List<String> choices = new ArrayList<>();
                    choices.add("Red");
                    choices.add("Blue");
                    choices.add("Green");
                    choices.add("Yellow");

                    ChoiceDialog<String> dialog = new ChoiceDialog<>("Red", choices);
                    dialog.setTitle("Call Color");
                    dialog.setHeaderText("I can call any color I want");
                    dialog.setContentText("I want to call:");

                    // Traditional way to get the response value.
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        System.out.println("Your choice: " + result.get());
                        pickedColor = Color.valueOf(result.get().toUpperCase());
                        System.out.println("picked color after result is ..... " + pickedColor);
                        card.setColor(pickedColor);
                        manage = pickedColor;
                    } else {
                        return;
                    }
                }

                System.out.println("Card " + card + " is valid, sending move");

                GameEngine.getInstance().sendMove(card);

                imgMoveZone.setImage(view.getImage());

                moveCommand.getCurrentHand().remove(index);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        s = "";
                        for (Card c : moveCommand.getCurrentHand()) {
                            s = s + c.toString();
                            s = s + ",";
                        }
                        System.out.println("updating with: " + s);
                        //System.out.println("updating with: " + moveCommand.getCurrentHand().stream().map(Card::toString).collect(Collectors.joining(",")));
                        handView.populate(moveCommand.getCurrentHand(), false);
                    }
                });

                btnPenalty.setVisible(false);
                btnPass.setVisible(false);
                btnDraw.setVisible(false);
                btnNoMove.setVisible(false);
                pileimage.setVisible(true);
                lblStatus.setText("");

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No No No !!!");
                alert.getDialogPane().setContentText("Not a valid move");
                alert.showAndWait();
            }

//        
//        /* we are here means no valid move, have to request server for drawing a card or pay the penalty*/
//        if ((command.getCurrentCard().isPlusTwo() || command.getCurrentCard().isPlusFour()) && command.isIsTopCardActive())  {
//            System.out.println("Oopps, no valid card here, accepting penalty");
//            GameEngine.getInstance().sendAcceptPenalty();
//        } else {
//            if (command.isIsDrawAllowed()) {
//                System.out.println("Oopps, no valid card here, need to draw");
//                GameEngine.getInstance().sendDrawCard();
//            } else {
//                System.out.println("No much even after draw, let it go");
//                GameEngine.getInstance().sendRelinquish();
//            }
//        }
//        System.out.println("===========================================");
        }
    }

    @FXML
    private void aftersayuno(ActionEvent event) {
        Media sound = new Media(new File(unoSound).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

}
