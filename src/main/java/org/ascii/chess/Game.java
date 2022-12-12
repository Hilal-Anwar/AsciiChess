package org.ascii.chess;

import org.ascii.chess.util.*;

public class Game extends Display {
    private final ChessBoard chessBoard = new ChessBoard(new Cursor(4, 4, Colors.MAGENTA));
    private ChessBox [][]board;
    public Game() {
        init();
    }

    private void init() {
        Colors c_w = Colors.CYAN_BRIGHT;
        Colors c_b = Colors.YELLOW;
        board = chessBoard.getChessBoard();
        //all the black pieces
        board[0][0] = new ChessBox(new ChessToken(ChessPieceType.ROOK, c_b, Piece.BLACK), false);
        board[0][1] = new ChessBox(new ChessToken(ChessPieceType.KNIGHT, c_b, Piece.BLACK), false);
        board[0][2] = new ChessBox(new ChessToken(ChessPieceType.BISHOP, c_b, Piece.BLACK), false);
        board[0][3] = new ChessBox(new ChessToken(ChessPieceType.QUEEN, c_b, Piece.BLACK), false);
        board[0][4] = new ChessBox(new ChessToken(ChessPieceType.KING, c_b, Piece.BLACK), false);
        board[0][5] = new ChessBox(new ChessToken(ChessPieceType.BISHOP, c_b, Piece.BLACK), false);
        board[0][6] = new ChessBox(new ChessToken(ChessPieceType.KNIGHT, c_b, Piece.BLACK), false);
        board[0][7] = new ChessBox(new ChessToken(ChessPieceType.ROOK, c_b, Piece.BLACK), false);

        board[1][0] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_b, Piece.BLACK), false);
        board[1][1] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_b, Piece.BLACK), false);
        board[1][2] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_b, Piece.BLACK), false);
        board[1][3] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_b, Piece.BLACK), false);
        board[1][4] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_b, Piece.BLACK), false);
        board[1][5] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_b, Piece.BLACK), false);
        board[1][6] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_b, Piece.BLACK), false);
        board[1][7] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_b, Piece.BLACK), false);

        // all the white pieces
        board[7][0] = new ChessBox(new ChessToken(ChessPieceType.ROOK, c_w, Piece.WHITE), false);
        board[7][1] = new ChessBox(new ChessToken(ChessPieceType.KNIGHT, c_w, Piece.WHITE), false);
        board[7][2] = new ChessBox(new ChessToken(ChessPieceType.BISHOP, c_w, Piece.WHITE), false);
        board[7][3] = new ChessBox(new ChessToken(ChessPieceType.QUEEN, c_w, Piece.WHITE), false);
        board[7][4] = new ChessBox(new ChessToken(ChessPieceType.KING, c_w, Piece.WHITE), false);
        board[7][5] = new ChessBox(new ChessToken(ChessPieceType.BISHOP, c_w, Piece.WHITE), false);
        board[7][6] = new ChessBox(new ChessToken(ChessPieceType.KNIGHT, c_w, Piece.WHITE), false);
        board[7][7] = new ChessBox(new ChessToken(ChessPieceType.ROOK, c_w, Piece.WHITE), false);

        board[6][0] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_w, Piece.WHITE), false);
        board[6][1] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_w, Piece.WHITE), false);
        board[6][2] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_w, Piece.WHITE), false);
        board[6][3] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_w, Piece.WHITE), false);
        board[6][4] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_w, Piece.WHITE), false);
        board[6][5] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_w, Piece.WHITE), false);
        board[6][6] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_w, Piece.WHITE), false);
        board[6][7] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_w, Piece.WHITE), false);
        for (int i = 2; i <= 5; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new ChessBox(null, false);
            }
        }
    }

    public void start() throws InterruptedException {
        KeyBoardInput keyBoardInput = new KeyBoardInput(this);
        clear_display();
        chessBoard.draw();
        while (true) {
            var key = keyBoardInput.getKeyBoardKey();
            switch (key) {
                case UP -> chessBoard.move_cursor_up();
                case DOWN -> chessBoard.move_cursor_down();
                case RIGHT -> chessBoard.move_cursor_right();
                case LEFT -> chessBoard.move_cursor_left();
                case ENTER -> moves();
                case ESC -> System.exit(-1);
            }
            if (!key.equals(Key.NONE)) {
                clear_display();
                chessBoard.draw();
            }
            keyBoardInput.setKeyBoardKey(Key.NONE);
            Thread.sleep(30);
        }
    }

    private void moves() {
        int x = chessBoard.getColumn();
        int y = chessBoard.getRow();
        if (board[y][x]!=null){

        }
        else {

        }
    }

}
