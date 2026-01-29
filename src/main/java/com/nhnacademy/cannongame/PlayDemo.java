package com.nhnacademy.cannongame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class PlayDemo extends Application {
    World world;
    Canvas canvas;
    GraphicsContext gc;
    AnimationTimer loop;
    Pane pane;
    Scene scene;
    Stage stage;

    @Override
    public void start(Stage stage) {
        world = new World(800, 600);
        world.ground = new double[(int) world.width];
        world.createGround();
        canvas = new Canvas(world.width, world.height);
        gc = canvas.getGraphicsContext2D();

        world.cannonBody = new CannonBody(100, 500, 50, 20, new Vector(0, world.GRAVITY), 0, 0);

        loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                clearCanvas();
                world.update(gc);
            }
        };

        world.cannonBody.draw(gc);

        pane = new Pane(canvas);
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> {
                    world.cannonBody.angle -= 1;
                }
                case DOWN -> {
                    world.cannonBody.angle += 1;
                }
                case LEFT -> {
                    world.cannonBody.facing = Direction.LEFT;
                    world.cannonBody.x -= 1;
                }
                case RIGHT -> {
                    world.cannonBody.facing = Direction.RIGHT;
                    world.cannonBody.x += 1;
                }
                case SPACE -> {
                    if (world.myTurn) {
                        world.cannonBody.charging = true;
                        if (world.cannonBody.power > 40) {
                            world.cannonBody.power = 40;
                            world.myTurn = false;
                        }
                        world.cannonBody.power += 1;
                    }
                }
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case SPACE -> {
                    world.cannonBody.charging = false;
                    world.ball = world.cannonBody.createCannonBall();
                }
            }
        });

        loop.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void clearCanvas() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
