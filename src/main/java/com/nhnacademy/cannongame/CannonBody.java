package com.nhnacademy.cannongame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CannonBody {
    double x;
    double y;
    double width;
    double height;
    double angle;
    double power;
    boolean charging = false;


    public CannonBody(double x, double y, double width, double height, double angle, double power){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.power = power;
    }

    public void draw(GraphicsContext gc) {
        gc.save();

        gc.translate(x, y + height / 2);
        gc.rotate(angle);
        gc.translate(-x, -(y + height / 2));

        gc.setFill(Color.GRAY);
        gc.fillRect(x, y, width, height);

        gc.restore();

        gc.setFill(Color.BLACK);
        gc.fillOval(x - height / 2, y + height / 2, height, height);

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
        double ballY = y + height / 2 + width * Math.sin(rad); // 계산은 맞으나 포구가 회전되어 있어서 시각적으로 완벽히 일치하지 않음
        double dx = power * k * Math.cos(rad);
        double dy = power * k * Math.sin(rad);

        power = 0;

        return new Ball(new Point(ballX, ballY), w, h, new Vector(dx, dy));
    }

    public void drawCharging(GraphicsContext gc) {
        gc.setFill(Color.LIME);
        gc.fillRect(x, y + 25, power, 10);
    }
}
