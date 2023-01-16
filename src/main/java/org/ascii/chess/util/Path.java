package org.ascii.chess.util;

import java.util.ArrayList;

/**
 * @author hilal on 16-01-2023
 * @project AsciiChess
 */
public record Path(Directions directions, ArrayList<Point> points) {
}
