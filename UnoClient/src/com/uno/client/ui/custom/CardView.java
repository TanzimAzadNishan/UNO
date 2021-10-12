package com.uno.client.ui.custom;

import javafx.animation.TranslateTransition;
import javafx.geometry.Point3D;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 *
 * @author
 */
public class CardView extends ImageView {

    final static Reflection reflection = new Reflection();
    final static Point3D rotationAxis = new Point3D(0, 90, 1);

    static {
        reflection.setFraction(0.5);
    }

    int index;
    final Rotate rotate = new Rotate(0, rotationAxis);
    final TranslateTransition transition = new TranslateTransition(Duration.millis(300), this);

    public CardView(String imageUrl, int index) {
        super(imageUrl);
        setEffect(reflection);
        setUserData(index);

        this.index = index;
        getTransforms().add(rotate);

    }

    public void update(int currentIndex, double width, double height) {

//        this.setFitWidth(120);
//        this.setFitHeight(200);
        int ef = index - currentIndex;
        double middle = width / 2;
        boolean b = ef < 0;

        setTranslateY(height / 2 - getImage().getHeight() / 2);
        double x, z, theta, pivot;

        if (ef == 0) {
            z = -200;
            x = middle;
            theta = 0;
            pivot = b ? 200 : 0;
        } else {
            // x = middle + ef * 82; // + (b ? -147 : 147);
            x = middle + ef * 82; // + (b ? -147 : 147);
            z = -78.588;
            pivot = b ? 100 : 0;
            theta = b ? 0 : 0;
        }
        rotate.setPivotX(pivot);
        rotate.setAngle(theta);

        this.setLayoutX(x);

    }

    public void updateOpponents(int currentIndex, double width, double height) {

//        this.setFitWidth(120);
//        this.setFitHeight(200);
        int ef = index - currentIndex;
        double middle = height / 2;
        boolean b = ef < 0;

        setTranslateX(width / 2 - getImage().getWidth() / 2);
        double x,y, z, theta, pivot;

        if (ef == 0) {
            z = -200;
            y = middle;
            theta = 0;
            pivot = b ? 200 : 0;
        } else {
            // x = middle + ef * 82; // + (b ? -147 : 147);
            y = middle + ef * 50; // + (b ? -147 : 147);
            z = -78.588;
            pivot = b ? 100 : 0;
            theta = b ? 0 : 0;
        }
        rotate.setPivotX(pivot);
        rotate.setAngle(theta);

        this.setLayoutY(y);

    }

}
