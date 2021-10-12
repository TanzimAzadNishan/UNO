package com.uno.client.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UnoGameApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ScreenSwitcher.getInstance().initialize(primaryStage);
        ScreenSwitcher.getInstance().switchScreen("SplashScreen.fxml");

        
    }

    public static void main(String[] args) {
        launch(args);
    }

}
