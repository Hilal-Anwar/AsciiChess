package org.ascii.chess.util;

import org.ascii.chess.pieces.ChessToken;

import java.util.ArrayList;

public interface Movements {
    ArrayList<int[]> rook_movement(int x, int y, Players color);

    ArrayList<int[]> bishop_movement(int x, int y, Players color);

    ArrayList<int[]> knight_movement(int x, int y, Players color);

    ArrayList<int[]> queen_movement(int x, int y, Players color);

    ArrayList<int[]> king_movement(int x, int y, Players color);

    ArrayList<int[]>  pawn_movement(int x, int y, ChessToken chessToken, Players color);

}
