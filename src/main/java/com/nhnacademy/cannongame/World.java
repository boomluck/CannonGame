package com.nhnacademy.cannongame;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
    int timeLeft = 30;

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
        cannons.get(player).myTurn = false;
        player = (player + 1) % cannons.size();
        cannons.get(player).myTurn = true;
    }

    public void Timer(long now) {
        long startTime = System.nanoTime();
        long duration = 30_000_000_000L;

        long elapsed = now - startTime;

        if (elapsed >= duration) {
            turnOver();
        }

        double secondsLeft = (duration - elapsed) / 1_000_000_000.0;
    }

    public void setKeyEvent(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> {
                    cannons.get(player).angle -= 1;
                }
                case DOWN -> {
                    cannons.get(player).angle += 1;
                }
                case LEFT -> {
                    cannons.get(player).facing = Direction.LEFT;
                    cannons.get(player).x -= 1;
                }
                case RIGHT -> {
                    cannons.get(player).facing = Direction.RIGHT;
                    cannons.get(player).x += 1;
                }
                case SPACE -> {
                    if (cannons.get(player).myTurn) {
                        cannons.get(player).charging = true;
                        if (cannons.get(player).power > 40) {
                            cannons.get(player).power = 40;
                        }
                        cannons.get(player).power += 1;
                    }
                }
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case SPACE -> {
                    cannons.get(player).charging = false;
                    ball = cannons.get(player).createCannonBall();
                }
            }
        });
    }

    public void drawTimer(GraphicsContext gc) {
        String text = "30"; //Integer.toString(secondsLeft);
        Text textNode = new Text(text);
        textNode.setFont(Font.font(50));

        double textWidth = textNode.getLayoutBounds().getWidth();

        gc.setFill(Color.BLACK);
        gc.fillText(text, width / 2 - textWidth / 2, 100);
    }

    public void update(GraphicsContext gc) {
        // 월드
        drawGround(gc);
        new Timer(30);
        drawTimer(gc);

        // 캐논
        for (Cannon cannon : cannons) {
            if (!cannon.isTouchingGround(this)) {
                cannon.translate();
                cannon.applyGravity(GRAVITY);
            }
            if (cannon.myTurn) {
                cannon.drawPointer(gc);
            }
            cannon.climbUp(this);
            cannon.draw(gc);
        }

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
                turnOver();
            }
        }
    }
}
