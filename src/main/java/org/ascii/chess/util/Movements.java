package org.ascii.chess.util;

import org.ascii.chess.ChessToken;
import org.ascii.chess.PieceColor;

import java.util.ArrayList;

public interface Movements {
    ArrayList<int[]> rook_movement(int x, int y, PieceColor color);

    ArrayList<int[]> bishop_movement(int x, int y,PieceColor color);

    ArrayList<int[]> knight_movement(int x, int y,PieceColor color);

    ArrayList<int[]> queen_movement(int x, int y,PieceColor color);

    ArrayList<int[]> king_movement(int x, int y,PieceColor color);

    ArrayList<int[]>  pawn_movement(int x, int y, ChessToken chessToken,PieceColor color);

}
