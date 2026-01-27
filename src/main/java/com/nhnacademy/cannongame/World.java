package com.nhnacademy.cannongame;

import javafx.scene.canvas.GraphicsContext;

public class World {
    double width;
    double height;
    Ball ball = null;
    CannonBody cannonBody;
    boolean myTurn = true;

    public World(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void update(GraphicsContext gc) {
        if (ball != null) {
            ball.translate();
            ball.accelerate();
            ball.draw(gc);
            if (ball.isOutOfBounds(this)) {
                ball = null;
                myTurn = true;
            }
        }
        cannonBody.draw(gc);
    }
}
