package com.nhnacademy.cannongame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CannonBody {
    double x;
    double y;
    double width;
    double height;
    double angle;

    public CannonBody(double x, double y, double width, double height, double angle){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(x, y, width, height);
    }

    public void angleGoUp(GraphicsContext gc) {
        angle--;

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 800, 600);

        gc.translate(x, y + height / 2); // canvas 원점(0, 0)을 (x, y + height / 2) 만큼 이동
        gc.rotate(-1); // 양수 : 시계방향, 음수 : 반시계방향
        gc.translate(-x, -(y + height / 2));

        gc.setFill(Color.BLACK);
        gc.fillRect(x, y, width, height);
    }

    public void angleGoDown(GraphicsContext gc) {
        angle++;

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 800, 600);

        gc.translate(x, y + height / 2);
        gc.rotate(1);
        gc.translate(-x, -(y + height / 2));

        gc.setFill(Color.BLACK);
        gc.fillRect(x, y, width, height);
    }
}
