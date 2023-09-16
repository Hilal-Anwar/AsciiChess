package org.ascii.chess.util;

import org.ascii.chess.pieces.ChessToken;

import java.util.ArrayList;
import java.util.LinkedList;

public interface Movements {

    ArrayList<Point> rook_movement(int x, int y, Players color);

    ArrayList<Point> bishop_movement(int x, int y, Players color);

    ArrayList<Point> knight_movement(int x, int y, Players color);

    ArrayList<Point> queen_movement(int x, int y, Players color);

    ArrayList<Point> king_movement(int x, int y, Players color);

    ArrayList<Point>  pawn_movement(int x, int y, ChessToken chessToken, Players color);

}
