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
        canvas = new Canvas(world.width, world.height);
        gc = canvas.getGraphicsContext2D();

        //world.ball = new Ball(new Point(100, 500), 18, 18, new Vector(2, -4));
        world.cannonBody = new CannonBody(100, 500, 50, 20, 0);


        loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                world.update(gc);
            }
        };

        //world.cannonBody.draw(gc);

        pane = new Pane(canvas);
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> {
                    world.cannonBody.angleGoUp(gc);
                }
                case DOWN -> {
                    world.cannonBody.angleGoDown(gc);
                }
                //case LEFT -> ;
                //case RIGHT -> ;
                case SPACE -> {
                    double w = world.cannonBody.height - 2;
                    double h = world.cannonBody.height - 2;
                    double x = world.cannonBody.x + world.cannonBody.width - w;
                    double y = world.cannonBody.y + world.cannonBody.height - w;
                    double dx = Math.cos(world.cannonBody.angle);
                    double dy = Math.sin(world.cannonBody.angle);

                    world.ball = new Ball(new Point(x, y), w, h, new Vector(dx, dy));
                    world.ball.fire(gc, world.ball);
                }
            }
        });

        //loop.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
