package com.nhnacademy.cannongame;

public class Vector {
    double dx;
    double dy;

    public Vector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy; // 좌표계의 시각적 편의를 위해 y-축 반전
    }

    public Vector addition(Vector other) {
        return new Vector(dx + other.dx, dy + other.dy);
    }

    public double magnitude() {
        return Math.sqrt(dx * dx + dy * dy);
    }
}
