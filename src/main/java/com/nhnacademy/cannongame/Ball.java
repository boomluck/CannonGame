package com.nhnacademy.cannongame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball {
    double x;
    double y;
    double width;
    double height;
    Vector velocity;

    public Ball(double x, double y, double width, double height, Vector velocity) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = velocity;
    }

    public void translate() {
        x = x + velocity.dx;
        y = y + velocity.dy;
    }

    public void applyGravity(double GRAVITY) {
        velocity.dy = velocity.dy + GRAVITY;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(x, y, width, height);
    }

    public boolean isOutOfBounds(World world) {
        return x - width <= 0
            || x + width >= world.width
            || y - height <= 0
            || y + height >= world.height;
    }

    public boolean isTouchingGround(World world) {
        return y >= world.ground[(int) x];
    }

    public void causeExplosion(World world) {
        for (int i = (int) (x - width); i < (int) (x + width); i++) {
            double h = Math.sqrt(width * width - (i - x) * (i - x));
            if (Double.isNaN(h)) {
                h = 0;
            }
            world.ground[(int) i] += h;
        }
    }
}
