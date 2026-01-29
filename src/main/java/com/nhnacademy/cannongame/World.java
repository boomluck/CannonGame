package com.nhnacademy.cannongame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class World {
    double width;
    double height;
    double GRAVITY = 0.04;
    double[] ground;
    Random random = new Random();

    Ball ball = null;
    CannonBody cannonBody;
    boolean myTurn = true;

    public World(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void createGround() {
        ground[0] = 550;
        for(int i = 1; i < width; i++) {
            ground[i] = ground[i-1] + random.nextInt(-1, 2);
        }
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.DARKSLATEGRAY);
        for(int i = 0; i < width; i++) {
            gc.fillRect(i, ground[i], 1, height - ground[i]);
        }
    }

    public void update(GraphicsContext gc) {
        // 월드
        draw(gc);

        // 캐논
        if (!cannonBody.isTouchingGround(this)) {
            cannonBody.translate();
            cannonBody.applyGravity(GRAVITY);
        }
        cannonBody.climbUp(this);
        cannonBody.draw(gc);

        // 포탄
        if (ball != null) {
            ball.translate();
            ball.applyGravity(GRAVITY);
            ball.draw(gc);

            if (ball.isTouchingGround(this) || ball.isOutOfBounds(this)) {
                if (ball.isTouchingGround(this)) {
                    ball.causeExplosion(this);
                }
                ball = null;
                myTurn = true;
            }
        }
    }
}
