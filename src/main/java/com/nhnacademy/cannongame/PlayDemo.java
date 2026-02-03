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

        world.cannons.add(new Cannon(100, 500, 50, 20, new Vector(0, world.GRAVITY), 0, 0, 50, 50, Direction.RIGHT, true));
        world.cannons.add(new Cannon(700, 500, 50, 20, new Vector(0, world.GRAVITY), 0, 0, 50, 50, Direction.LEFT, false));

        loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                clearCanvas();
                world.setKeyEvent(scene);
                world.update(gc, now);
            }
        };

        pane = new Pane(canvas);
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

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
