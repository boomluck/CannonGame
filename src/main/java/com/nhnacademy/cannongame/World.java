package com.nhnacademy.cannongame;

import javafx.scene.canvas.GraphicsContext;

public class World {
    double width;
    double height;
    Ball ball;
    CannonBody cannonBody;

    public World(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void update(GraphicsContext gc) {
//        ball.translate();
//        ball.accelerate();
//        ball.draw(gc);
        cannonBody.draw(gc);
    }
}
