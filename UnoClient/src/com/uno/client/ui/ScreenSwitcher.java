/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uno.client.ui;

import com.uno.common.game.PlayerStatus;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author
 */
public class ScreenSwitcher {

    public static final int WINDOW_WIDTH = 1472;
    public static final int WINDOW_HEIGHT = 1000;

    private static ScreenSwitcher instance = null;
    Group group;
    double origW = 600;
    double origH = 400;

    private ScreenSwitcher() {

    }

    public static ScreenSwitcher getInstance() {
        if (instance == null) {
            instance = new ScreenSwitcher();
        }
        return instance;
    }

    private Stage stage = null;

    public void initialize(Stage stage) {
        this.stage = stage;

    }

    public void goToGameScreen(List<PlayerStatus> existingPlayers, int count) {

        try {
            /*FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScreen.fxml"));
            Parent root = loader.load();
            GameScreenController controller = loader.getController();
            controller.setInitialState(existingPlayers, count);
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
            scene.setUserData(loader);*/

            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScreen.fxml"));
            Region contentRootRegion = loader.load();
            GameScreenController controller = loader.getController();
            controller.setInitialState(existingPlayers, count);
            //Set a default "standard" or "100%" resolution
            //If the Region containing the GUI does not already have a preferred width and height, set it.
            //But, if it does, we can use that setting as the "standard" resolution.
            if (contentRootRegion.getPrefWidth() == Region.USE_COMPUTED_SIZE) {
                contentRootRegion.setPrefWidth(origW);
            } else {
                origW = contentRootRegion.getPrefWidth();
            }
            if (contentRootRegion.getPrefHeight() == Region.USE_COMPUTED_SIZE) {
                contentRootRegion.setPrefHeight(origH);
            } else {
                origH = contentRootRegion.getPrefHeight();
            }
            //Wrap the resizable content in a non-resizable container (Group)
            group = new Group(contentRootRegion);
            //Place the Group in a StackPane, which will keep it centered
            StackPane rootPane = new StackPane();
            rootPane.getChildren().add(group);
            //stage.setTitle( "My Slide" );
            //Create the scene initally at the "100%" size
            Scene scene = new Scene(rootPane, origW, origH);
            scene.setUserData(loader);

            switchScreen(scene);
        } catch (IOException ex) {
            Logger.getLogger(ScreenSwitcher.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void switchScreen(String fxml) {
        try {
            if (stage != null) {
                unsubscribePrevious();
                /*FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                scene.setUserData(loader);
                stage.setScene(scene);
                stage.getScene().setCamera(new PerspectiveCamera());
                stage.show(); 
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);*/

                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                Region contentRootRegion = loader.load();
                
                //Set a default "standard" or "100%" resolution
                origW = 600;
                origH = 400;
                //If the Region containing the GUI does not already have a preferred width and height, set it.
                //But, if it does, we can use that setting as the "standard" resolution.
                if (contentRootRegion.getPrefWidth() == Region.USE_COMPUTED_SIZE) {
                    contentRootRegion.setPrefWidth(origW);
                } else {
                    origW = contentRootRegion.getPrefWidth();
                }
                if (contentRootRegion.getPrefHeight() == Region.USE_COMPUTED_SIZE) {
                    contentRootRegion.setPrefHeight(origH);
                } else {
                    origH = contentRootRegion.getPrefHeight();
                }
                //Wrap the resizable content in a non-resizable container (Group)
                Group group1 = new Group(contentRootRegion);
                //Place the Group in a StackPane, which will keep it centered
                StackPane rootPane = new StackPane();
                rootPane.getChildren().add(group1);
                //primaryStage.setTitle("My Slide");
                //Create the scene initally at the "100%" size
                Scene scene = new Scene(rootPane, origW, origH);
                scene.setUserData(loader);
                //Bind the scene's width and height to the scaling parameters on the group
                group1.scaleXProperty().bind(scene.widthProperty().divide(origW));
                group1.scaleYProperty().bind(scene.heightProperty().divide(origH));
                //Set the scene to the window (stage) and show it
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void switchScreen(Scene scene) {
        try {
            if (stage != null) {
                unsubscribePrevious();

                //Bind the scene's width and height to the scaling parameters on the group
                group.scaleXProperty().bind(scene.widthProperty().divide(origW));
                group.scaleYProperty().bind(scene.heightProperty().divide(origH));
                scene.getStylesheets().add("com/uno/client/ui/Background.css");

                stage.setScene(scene);
                stage.show();
                /*Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);*/
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void unsubscribePrevious() {
        if (stage.getScene() != null) {
            Object obj = stage.getScene().getUserData();

            if (obj != null && obj instanceof FXMLLoader) {
                Object controller = ((FXMLLoader) obj).getController();
                if (controller != null && controller instanceof UnoGameBaseViewController) {
                    ((UnoGameBaseViewController) controller).unsubscribe();
                }
            }
        }
    }

    public void setTitle(String title) {
        if (stage != null) {
            stage.setTitle(title);
        }
    }

}
