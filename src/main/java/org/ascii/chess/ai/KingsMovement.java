package org.ascii.chess.ai;

import org.ascii.chess.pieces.ChessToken;
import org.ascii.chess.util.Movements;
import org.ascii.chess.util.Players;
import org.ascii.chess.util.Point;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author hilal on 28-01-2023
 * @project AsciiChess
 */
public class KingsMovement implements Movements {
    @Override
    public ArrayList<Point> rook_movement(int x, int y, Players color) {
        return null;
    }

    @Override
    public ArrayList<Point> bishop_movement(int x, int y, Players color) {
        return null;
    }

    @Override
    public ArrayList<Point> knight_movement(int x, int y, Players color) {
        return null;
    }

    @Override
    public ArrayList<Point> queen_movement(int x, int y, Players color) {
        return null;
    }

    @Override
    public ArrayList<Point> king_movement(int x, int y, Players color) {
        return null;
    }

    @Override
    public ArrayList<Point> pawn_movement(int x, int y, ChessToken chessToken, Players color) {
        return null;
    }
}
