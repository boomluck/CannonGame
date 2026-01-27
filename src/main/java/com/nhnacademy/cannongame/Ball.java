package com.nhnacademy.cannongame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball {
    Point point;
    double width;
    double height;
    Vector velocity;

    public Ball(Point point, double width, double height, Vector velocity) {
        this.point = point;
        this.width = width;
        this.height = height;
        this.velocity = velocity;
    }

    public void translate() {
        point.x = point.x + velocity.dx;
        point.y = point.y + velocity.dy;
    }

    public void accelerate() {
        velocity.dy = velocity.dy + (0.04);
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(point.x, point.y, width, height);
    }

    public boolean isOutOfBounds(World world) {
        return point.x - width <= 0
            || point.x + width >= world.width
            || point.y - height <= 0
            || point.y + height >= world.height;
    }
}
