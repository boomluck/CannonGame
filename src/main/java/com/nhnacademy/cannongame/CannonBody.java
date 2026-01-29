package com.nhnacademy.cannongame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CannonBody {
    double x;
    double y;
    double width;
    double height;
    Vector velocity;
    double angle;
    double power;
    boolean charging = false;
    Direction facing = Direction.RIGHT;

    public CannonBody(double x, double y, double width, double height, Vector velocity, double angle, double power){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = velocity;
        this.angle = angle;
        this.power = power;
    }

    public void translate() {
        x = x + velocity.dx;
        y = y + velocity.dy;
    }

    public void applyGravity(double GRAVITY) {
        velocity.dy = velocity.dy + GRAVITY;
    }

    public boolean isTouchingGround(World world) {
        return y + height * 3 / 2 >= world.ground[(int) x];
    }

    public void climbUp(World world) {
        double diff = (y + height * 3 / 2) - world.ground[(int) x];
        if (isTouchingGround(world) && diff >= 0) {
            y = y - diff;
        }
    }

    public void draw(GraphicsContext gc) {
        gc.save();

        gc.translate(x, y + height);

        if(facing == Direction.LEFT) {
            gc.scale(-1, 1);
        }

        gc.rotate(angle);

        gc.setFill(Color.GRAY);
        gc.fillRect(0, -height, width, height);

        gc.setFill(Color.BLACK);
        gc.fillOval(- height / 2, - height / 2, height, height);

        gc.restore();

        if (charging) {
            drawCharging(gc);
        }
    }

    public Ball createCannonBall() {
        double k = 0.1;
        double w = height - 2;
        double h = height - 2;
        double rad = Math.toRadians(angle);
        double ballX = x + width * Math.cos(rad);
        double ballY = y + height / 2 + width * Math.sin(rad);

        if (facing == Direction.LEFT) {
            rad = Math.toRadians(180 - angle);
            ballX = x - width * Math.cos(rad);
            ballY = y + height / 2 + width * Math.sin(rad);
        }

        double dx = power * k * Math.cos(rad);
        double dy = power * k * Math.sin(rad);

        power = 0;

        return new Ball(ballX, ballY, w, h, new Vector(dx, dy));
    }

    public void drawCharging(GraphicsContext gc) {
        gc.setFill(Color.LIME);
        gc.fillRect(x, y + 25, power, 10);
    }
}
