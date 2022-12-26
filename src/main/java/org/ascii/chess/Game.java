package org.ascii.chess;

import org.ascii.chess.util.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Game extends Display implements Movements {
    private final ChessBoard chessBoard = new ChessBoard(new Cursor(4, 6, Colors.MAGENTA));
    private ChessBox[][] board;
    private ArrayList<int[]> possible_position = new ArrayList<>();
    private int[] selected_box;
    private Players turn = Players.WHITE;
    private String message = "";

    public Game() {
        init();
    }
    private boolean enPassant=false;
    private boolean castling=false;
    private void init() {
        Colors c_w = Colors.CYAN_BRIGHT;
        Colors c_b = Colors.YELLOW;
        board = chessBoard.getChessBoard();
        //all the black pieces
        board[0][0] = new ChessBox(new ChessToken(ChessPieceType.ROOK, c_b, Players.BLACK), false);
        board[0][1] = new ChessBox(new ChessToken(ChessPieceType.KNIGHT, c_b, Players.BLACK), false);
        board[0][2] = new ChessBox(new ChessToken(ChessPieceType.BISHOP, c_b, Players.BLACK), false);
        board[0][3] = new ChessBox(new ChessToken(ChessPieceType.QUEEN, c_b, Players.BLACK), false);
        board[0][4] = new ChessBox(new ChessToken(ChessPieceType.KING, c_b, Players.BLACK), false);
        board[0][5] = new ChessBox(new ChessToken(ChessPieceType.BISHOP, c_b, Players.BLACK), false);
        board[0][6] = new ChessBox(new ChessToken(ChessPieceType.KNIGHT, c_b, Players.BLACK), false);
        board[0][7] = new ChessBox(new ChessToken(ChessPieceType.ROOK, c_b, Players.BLACK), false);

        board[1][0] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_b, Players.BLACK), false);
        board[1][1] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_b, Players.BLACK), false);
        board[1][2] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_b, Players.BLACK), false);
        board[1][3] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_b, Players.BLACK), false);
        board[1][4] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_b, Players.BLACK), false);
        board[1][5] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_b, Players.BLACK), false);
        board[1][6] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_b, Players.BLACK), false);
        board[1][7] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_b, Players.BLACK), false);

        // all the white pieces
        board[7][0] = new ChessBox(new ChessToken(ChessPieceType.ROOK, c_w, Players.WHITE), false);
        board[7][1] = new ChessBox(new ChessToken(ChessPieceType.KNIGHT, c_w, Players.WHITE), false);
        board[7][2] = new ChessBox(new ChessToken(ChessPieceType.BISHOP, c_w, Players.WHITE), false);
        board[7][3] = new ChessBox(new ChessToken(ChessPieceType.QUEEN, c_w, Players.WHITE), false);
        board[7][4] = new ChessBox(new ChessToken(ChessPieceType.KING, c_w, Players.WHITE), false);
        board[7][5] = new ChessBox(new ChessToken(ChessPieceType.BISHOP, c_w, Players.WHITE), false);
        board[7][6] = new ChessBox(new ChessToken(ChessPieceType.KNIGHT, c_w, Players.WHITE), false);
        board[7][7] = new ChessBox(new ChessToken(ChessPieceType.ROOK, c_w, Players.WHITE), false);

        board[6][0] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_w, Players.WHITE), false);
        board[6][1] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_w, Players.WHITE), false);
        board[6][2] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_w, Players.WHITE), false);
        board[6][3] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_w, Players.WHITE), false);
        board[6][4] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_w, Players.WHITE), false);
        board[6][5] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_w, Players.WHITE), false);
        board[6][6] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_w, Players.WHITE), false);
        board[6][7] = new ChessBox(new ChessToken(ChessPieceType.PAWN, c_w, Players.WHITE), false);
        for (int i = 2; i <= 5; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new ChessBox(null, false);
            }
        }
    }

    public void start() throws InterruptedException {
        KeyBoardInput keyBoardInput = new KeyBoardInput(this);
        clear_display();
        chessBoard.draw(message);
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
                chessBoard.draw(message);
            }
            keyBoardInput.setKeyBoardKey(Key.NONE);
            Thread.sleep(30);
        }
    }

    private void moves() {
        int x = chessBoard.getColumn();
        int y = chessBoard.getRow();
        var chess_box = board[y][x];
        if (turn.isEqual(chess_box) || selected_box != null) {
            if (chess_box.getChessToken() != null && selected_box == null) {
                var chess_piece_type = chess_box.getChessToken().getChessPieceType();
                possible_position = switch (chess_piece_type) {
                    case KING -> king_movement(x, y, chess_box.getChessToken().getPiece());
                    case QUEEN -> queen_movement(x, y, chess_box.getChessToken().getPiece());
                    case BISHOP -> bishop_movement(x, y, chess_box.getChessToken().getPiece());
                    case ROOK -> rook_movement(x, y, chess_box.getChessToken().getPiece());
                    case KNIGHT -> knight_movement(x, y, chess_box.getChessToken().getPiece());
                    case PAWN -> pawn_movement(x, y, chess_box.getChessToken(),
                            chess_box.getChessToken().getPiece());
                };
                if (possible_position.size() != 0) {
                    for (int[] sl : possible_position) {
                        board[sl[1]][sl[0]].
                                setSelected(true, board[sl[1]][sl[0]].getChessToken() != null ?
                                        Colors.RED : Colors.GREEN);
                    }
                    selected_box = new int[]{x, y};
                    if (board[y][x].getChessToken() != null) {
                        if (!board[y][x].isSelected())
                            board[y][x].setSelected(true, Colors.BLUE);
                    }
                }
            } else if (selected_box != null) {
                if (ifAnyMatching(x, y)) {
                    int _x = selected_box[0];
                    int _y = selected_box[1];
                    if (y == 0 && board[_y][_x].getChessToken().getPiece().equals(Players.WHITE) &&
                            board[_y][_x].getChessToken().getChessPieceType().equals(ChessPieceType.PAWN))
                        board[y][x] = new ChessBox(new ChessToken(ChessPieceType.QUEEN,
                                Colors.CYAN_BRIGHT, Players.WHITE), false);
                    else if (y == 7 && board[_y][_x].getChessToken().getPiece().equals(Players.BLACK) &&
                            board[_y][_x].getChessToken().getChessPieceType().equals(ChessPieceType.PAWN))
                        board[y][x] = new ChessBox(new ChessToken(ChessPieceType.QUEEN,
                                Colors.MAGENTA, Players.BLACK), false);
                    else board[y][x] = board[_y][_x];
                    board[_y][_x] = new ChessBox(null, false);
                    turn = turn.equals(Players.WHITE) ? Players.BLACK : Players.WHITE;
                }
                board[selected_box[1]][selected_box[0]].setSelected(false, Colors.WHITE);
                for (int[] sl : possible_position) {
                    board[sl[1]][sl[0]].setSelected(false, Colors.WHITE);

                }
                selected_box = null;
            }
            message = "";
        } else if (chess_box.getChessToken() != null) {
            message = "Invalid move its," + turn.name() + " turn " +
                    chess_box.getChessToken().getPiece().name() + " cannot be moved";
        }


    }

    private boolean ifAnyMatching(int x, int y) {
        var z = new int[]{x, y};
        return possible_position.stream().anyMatch(t -> Arrays.equals(t, z));
    }

    @Override
    public ArrayList<int[]> rook_movement(int x, int y, Players color) {
        int degree_f = 4;
        int[][] freedom = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int[][] movement = {{x, y}, {x, y}, {x, y}, {x, y}};
        var list = new ArrayList<int[]>();
        while (degree_f > 0) {
            for (int i = 0; i < 4; i++) {
                if (freedom[i] != null) {
                    int p = freedom[i][0] + movement[i][0];
                    int q = freedom[i][1] + movement[i][1];
                    if (isValidPoint(p, q, color)) {
                        movement[i][0] = p;
                        movement[i][1] = q;
                        list.add(new int[]{p, q});
                    } else if (isValidPointFilledPosition(p, q, color)) {
                        movement[i][0] = p;
                        movement[i][1] = q;
                        list.add(new int[]{p, q});
                        degree_f--;
                        freedom[i] = null;
                    } else {
                        degree_f--;
                        freedom[i] = null;
                        //movement[i] = null;
                    }
                }
            }
        }
        return list;
    }

    private boolean isValidPoint(int x, int y, Players color) {
        return (x >= 0 && x < 8 && y >= 0 && y < 8 && board[y][x].getChessToken() == null) /*||
                (x >= 0 && x < 8 && y >= 0 && y < 8 && board[y][x].getChessToken() != null &&
                        !color.equals(board[y][x].getChessToken().getPiece()))*/;
    }

    private boolean isValidPointFilledPosition(int x, int y, Players color) {
        return (x >= 0 && x < 8 && y >= 0 && y < 8 && board[y][x].getChessToken() != null &&
                !color.equals(board[y][x].getChessToken().getPiece()));
    }

    @Override
    public ArrayList<int[]> bishop_movement(int x, int y, Players color) {
        int degree_f = 4;
        int[][] freedom = {{1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
        int[][] movement = {{x, y}, {x, y}, {x, y}, {x, y}};
        var list = new ArrayList<int[]>();
        while (degree_f > 0) {
            for (int i = 0; i < 4; i++) {
                if (freedom[i] != null) {
                    int p = freedom[i][0] + movement[i][0];
                    int q = freedom[i][1] + movement[i][1];
                    if (isValidPoint(p, q, color)) {
                        movement[i][0] = p;
                        movement[i][1] = q;
                        list.add(new int[]{p, q});
                    } else if (isValidPointFilledPosition(p, q, color)) {
                        movement[i][0] = p;
                        movement[i][1] = q;
                        list.add(new int[]{p, q});
                        degree_f--;
                        freedom[i] = null;
                    } else {
                        degree_f--;
                        freedom[i] = null;
                        //movement[i] = null;
                    }
                }
            }
        }
        return list;
    }

    @Override
    public ArrayList<int[]> knight_movement(int x, int y, Players color) {
        int[][] freedom = {{1, 2}, {-1, 2}, {1, -2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, 1}};
        int[][] movement = {{x, y}, {x, y}, {x, y}, {x, y}, {x, y}, {x, y}, {x, y}, {x, y}};
        var list = new ArrayList<int[]>();
        for (int i = 0; i < 8; i++) {
            int p = freedom[i][0] + movement[i][0];
            int q = freedom[i][1] + movement[i][1];
            if (isValidPoint(p, q, color) || isValidPointFilledPosition(p, q, color)) {
                movement[i][0] = p;
                movement[i][1] = q;
                list.add(new int[]{p, q});
            }
        }
        return list;
    }

    @Override
    public ArrayList<int[]> queen_movement(int x, int y, Players color) {
        int degree_f = 8;
        int[][] freedom = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
        int[][] movement = {{x, y}, {x, y}, {x, y}, {x, y}, {x, y}, {x, y}, {x, y}, {x, y}};
        var list = new ArrayList<int[]>();
        while (degree_f > 0) {
            for (int i = 0; i < 8; i++) {
                if (freedom[i] != null) {
                    int p = freedom[i][0] + movement[i][0];
                    int q = freedom[i][1] + movement[i][1];
                    if (isValidPoint(p, q, color)) {
                        movement[i][0] = p;
                        movement[i][1] = q;
                        list.add(new int[]{p, q});
                    } else if (isValidPointFilledPosition(p, q, color)) {
                        movement[i][0] = p;
                        movement[i][1] = q;
                        list.add(new int[]{p, q});
                        degree_f--;
                        freedom[i] = null;
                    } else {
                        degree_f--;
                        freedom[i] = null;
                        //movement[i] = null;
                    }
                }
            }
        }
        return list;
    }

    @Override
    public ArrayList<int[]> king_movement(int x, int y, Players color) {
        int[][] freedom = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
        int[][] movement = {{x, y}, {x, y}, {x, y}, {x, y}, {x, y}, {x, y}, {x, y}, {x, y}};
        var list = new ArrayList<int[]>();
        for (int i = 0; i < 8; i++) {
            int p = freedom[i][0] + movement[i][0];
            int q = freedom[i][1] + movement[i][1];
            if (isValidPoint(p, q, color) || isValidPointFilledPosition(p, q, color)) {
                movement[i][0] = p;
                movement[i][1] = q;
                list.add(movement[i]);
            }
        }
        return list;
    }

    @Override
    public ArrayList<int[]> pawn_movement(int x, int y, ChessToken chessToken, Players color) {
        int[][] freedom;
        var list = new ArrayList<int[]>();
        int[][] w = {{0, -1}, {1, -1}, {-1, -1}};
        int[][] b = {{0, 1}, {1, 1}, {-1, 1}};
        freedom = color.equals(Players.WHITE) ? w : b;
        int[][] movement = {{x, y}, {x, y}, {x, y}};
        for (int i = 0; i < 3; i++) {
            int p = freedom[i][0] + movement[i][0];
            int q = freedom[i][1] + movement[i][1];
            if (isValidPoint(p, q, color) || isValidPointFilledPosition(p, q, color)) {
                movement[i][0] = p;
                movement[i][1] = q;
                switch (i) {
                    case 0 -> {
                        if (isValidPoint(p, q, color)) {
                            list.add(movement[i]);
                            if (y == 1 || y == 6) {
                                p = freedom[i][0] + movement[i][0];
                                q = freedom[i][1] + movement[i][1];
                                if (isValidPoint(p, q, color))
                                    list.add(new int[]{p, q});
                            }
                        }
                    }
                    case 1, 2 -> {
                        if (isValidPointFilledPosition(p, q, color))
                            list.add(new int[]{p, q});
                    }
                }
            }
        }
        if (y == 3 ) {

        }
        else if ( y == 4){

        }
        return list;
    }
}

