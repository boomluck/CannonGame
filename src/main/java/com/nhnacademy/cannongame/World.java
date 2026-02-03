package com.nhnacademy.cannongame;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    double width;
    double height;
    double GRAVITY = 0.04;
    double[] ground;
    Random random = new Random();

    Ball ball = null;
    List<Cannon> cannons = new ArrayList<>();

    int player = 0;
    boolean busy = false;

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

    public void drawGround(GraphicsContext gc) {
        gc.setFill(Color.DARKSLATEGRAY);
        for(int i = 0; i < width; i++) {
            gc.fillRect(i, ground[i], 1, height - ground[i]);
        }
    }

    public void turnOver() {
        refillFuel();

        cannons.get(player).myTurn = false;
        player = (player + 1) % cannons.size();
        cannons.get(player).myTurn = true;
    }

    void refillFuel() {
        cannons.get(player).currentFuel = cannons.get(player).maxFuel;
    }

    public void setKeyEvent(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (!busy) {
                switch (event.getCode()) {
                    case UP -> {
                        cannons.get(player).angle -= 1;
                    }
                    case DOWN -> {
                        cannons.get(player).angle += 1;
                    }
                    case LEFT -> {
                        cannons.get(player).facing = Direction.LEFT;
                        if (cannons.get(player).currentFuel > 0) {
                            cannons.get(player).x -= 1;
                            cannons.get(player).currentFuel -= 1;
                        }
                    }
                    case RIGHT -> {
                        cannons.get(player).facing = Direction.RIGHT;
                        if (cannons.get(player).currentFuel > 0) {
                            cannons.get(player).x += 1;
                            cannons.get(player).currentFuel -= 1;
                        }
                    }
                    case SPACE -> {
                        if (cannons.get(player).myTurn && ball == null) {
                            cannons.get(player).charging = true;
                            if (cannons.get(player).power > 40) {
                                cannons.get(player).power = 40;
                                ball = cannons.get(player).createCannonBall();
                            }
                            cannons.get(player).power += 1;
                        }
                    }
                }
            }
        });

        scene.setOnKeyReleased(event -> {
            if (!busy) {
                switch (event.getCode()) {
                    case SPACE -> {
                        cannons.get(player).charging = false;
                        if (ball == null) {
                            ball = cannons.get(player).createCannonBall();
                        }
                    }
                }
            }
        });
    }

    public void update(GraphicsContext gc, long now) {
        // 월드
        drawGround(gc);

        // 캐논
        for (Cannon cannon : cannons) {
            if (!cannon.isTouchingGround(this)) {
                cannon.translate();
                cannon.applyGravity(GRAVITY);
            }
            if (cannon.myTurn && ball == null) {
                cannon.drawPointer(gc);
                cannon.drawTimer(gc, now, this);
            }
            cannon.climbUp(this);
            cannon.draw(gc, this);
        }

        // 포탄
        if (ball != null) {
            busy = true;
            ball.translate();
            ball.applyGravity(GRAVITY);
            ball.draw(gc);

            if (ball.isTouchingGround(this) || ball.isOutOfBounds(this)) {
                if (ball.isTouchingGround(this)) {
                    ball.causeExplosion(this);
                }
                ball = null;
                busy = false;
                turnOver();
            }
        }
    }
}
