package com.nhnacademy.cannongame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Cannon {
    double x;
    double y;
    double width;
    double height;
    Vector velocity;
    double angle;
    double power;
    Direction facing = Direction.RIGHT;
    boolean myTurn = false;
    double maxFuel;
    double currentFuel;

    boolean charging = false;
    Timer timer = null;

    public Cannon(double x, double y, double width, double height, Vector velocity, double angle, double power, double maxFuel, double currentFuel, Direction facing, boolean myTurn){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = velocity;
        this.angle = angle;
        this.power = power;
        this.maxFuel = maxFuel;
        this.currentFuel = currentFuel;
        this.facing = facing;
        this.myTurn = myTurn;
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

    public void draw(GraphicsContext gc, World world) {
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

        if (charging && world.ball == null) {
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
            ballX = x + width * Math.cos(rad);
            ballY = y + height / 2 + width * Math.sin(rad);
        }

        double dx = power * k * Math.cos(rad);
        double dy = power * k * Math.sin(rad);

        power = 0;

        return new Ball(ballX, ballY, w, h, new Vector(dx, dy));
    }

    public void drawCharging(GraphicsContext gc) {
        double printX = x;
        double printY = y + 25;
        if (facing == Direction.LEFT) {
            printX = x - width;
        }

        gc.setFill(Color.LIME);
        gc.fillRect(printX, printY, power, 10);
    }

    public void drawPointer(GraphicsContext gc) {
        double printX = x;
        double printY = y - 35;
        if (facing == Direction.LEFT) {
            printX = x - width;
        }

        gc.setFont(Font.font("Apple Color Emoji", 32));
        gc.fillText("👇", printX, printY);
    }

    public void drawTimer(GraphicsContext gc, long now, World world) {
        if (timer == null) {
            timer = new Timer(30);
        }

        double remain = timer.remainingSeconds(now);
        String text = Integer.toString((int) Math.ceil(remain));

        double printX = x;
        double printY = y - 100;
        if (facing == Direction.LEFT) {
            printX = x - width;
        }

        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(24));
        gc.fillText(text, printX, printY);

        if (remain < 0) {
            timer = null;
            world.turnOver();
        }
    }
}
