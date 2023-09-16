package org.ascii.chess.util;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author hilal on 16-01-2023
 * @project AsciiChess
 */
public record Path(boolean isDanger, Point guardPoint, ArrayList<Point> points) {
}
