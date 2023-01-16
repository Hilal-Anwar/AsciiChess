package org.ascii.chess.util;

/**
 * @author hilal on 16-01-2023
 * @project AsciiChess
 */
public enum Directions {
    UP(new Point(0, 1)),
    DOWN(new Point(0, -1)),
    RIGHT(new Point(1, 0)),
    LEFT(new Point(-1, 0)),
    UP_RIGHT(new Point(1, -1)),
    UP_LEFT(new Point(-1, -1)),
    DOWN_RIGHT(new Point(1, 1)),
    DOWN_LEFT(new Point(-1, 1));
    final Point point;

    public Point getPoint() {
        return point;
    }

    Directions(Point point) {
        this.point = point;
    }

}
