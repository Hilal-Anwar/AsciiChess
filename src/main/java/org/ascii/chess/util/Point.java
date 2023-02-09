package org.ascii.chess.util;

import java.util.Objects;

/**
 * @author hilal on 14-01-2023
 * @project AsciiChess
 */
public class Point {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point add(Point point) {
        this.x = this.x + point.x;
        this.y = this.y + point.y;
        return point;
    }
}
