package org.ascii.chess;

import org.ascii.chess.util.*;

import java.util.ArrayList;
import java.util.Arrays;


public class Game extends Display implements Movements {
    /**
     * @author Hilal Anwar on 01-12-2022
     * @project AsciiChess
     */
    private final ChessBoard chessBoard = new ChessBoard(new Cursor(4, 6, Colors.MAGENTA));
    private ChessBox[][] board;
    private ArrayList<int[]> possible_position = new ArrayList<>();
    private int[] selected_box;
    private Players turn = Players.WHITE;
    private String message = "";
    private boolean isKingChecked = false;
    private boolean isCastlingValid_Black = true;
    private boolean isCastlingValid_White = true;

    public Game() {
        init();
    }

    private int[] enPassant = {};
    private ArrayList<int[]> castling = new ArrayList<>(2);

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
                    else if (isEnPassant(y, 3, _y, _x, x, y)) {
                        enPassant = new int[]{x, y - 1};
                        board[y][x] = board[_y][_x];
                    } else if (isEnPassant(y, 4, _y, _x, x, y)) {
                        enPassant = new int[]{x, y + 1};
                        board[y][x] = board[_y][_x];
                    } else if (((castling.size() > 0 && castling.get(0)[0] == x && castling.get(0)[1] == y) ||
                            (castling.size() > 1 && castling.get(1)[0] == x && castling.get(1)[1] == y))) {
                        var pi = board[_y][_x].getChessToken().getPiece();
                        if (pi.equals(Players.BLACK) && isCastlingValid_Black) {
                            board[y][x] = board[_y][_x];
                            if (x < _x) {
                                board[y][x + 1] = board[0][0];
                                board[0][0] = new ChessBox(null, false);
                            } else if (x > _x) {
                                board[y][x - 1] = board[0][7];
                                board[0][7] = new ChessBox(null, false);
                            }
                            isCastlingValid_Black = false;
                            castling=new ArrayList<>();
                        } else if (pi.equals(Players.WHITE) && isCastlingValid_White) {
                            board[y][x] = board[_y][_x];
                            if (x < _x) {
                                board[y][x + 1] = board[7][0];
                                board[7][0] = new ChessBox(null, false);
                            } else if (x > _x) {
                                board[y][x - 1] = board[7][7];
                                board[7][7] = new ChessBox(null, false);
                            }
                            isCastlingValid_White = false;
                            castling=new ArrayList<>();
                        }
                    } else if (enPassant.length > 0 && enPassant[0] == x && enPassant[1] == y) {
                        board[enPassant[1]][enPassant[0]] = board[_y][_x];
                        if (board[_y][_x].getChessToken().getPiece().equals(Players.WHITE))
                            board[_y][_x - 1] = new ChessBox(null, false);
                        else if (board[_y][_x].getChessToken().getPiece().equals(Players.BLACK)) {
                            board[_y][_x + 1] = new ChessBox(null, false);
                        }
                        enPassant = new int[]{};

                    } else board[y][x] = board[_y][_x];
                    board[_y][_x] = new ChessBox(null, false);
                    turn = turn.equals(Players.WHITE) ? Players.BLACK : Players.WHITE;
                }
                board[selected_box[1]][selected_box[0]].setSelected(false, Colors.WHITE);
                for (int[] sl : possible_position) {
                    board[sl[1]][sl[0]].setSelected(false, Colors.WHITE);
                }
                if (enPassant.length > 0) {
                    board[enPassant[1]][enPassant[0]].setSelected(false, Colors.WHITE);
                }
                for (int[] ints : castling) {
                    board[ints[1]][ints[0]].setSelected(false, Colors.WHITE);
                }

                selected_box = null;
            }
            message = "";
        } else if (chess_box.getChessToken() != null) {
            message = "Invalid move its," + turn.name() + " turn " +
                    chess_box.getChessToken().getPiece().name() + " cannot be moved";
        }


    }

    private boolean isEnPassant(int m, int n, int _y, int _x, int x, int y) {
        Players co = board[_y][_x].getChessToken().getPiece();
        if (m == n && board[_y][_x].getChessToken().getChessPieceType().equals(ChessPieceType.PAWN) && x != 0 &&
                board[_y][_x].getChessToken().getPiece().equals(Players.WHITE) &&
                board[y][x - 1].getChessToken() != null && !board[y][x - 1].getChessToken().getPiece().equals(co)) {
            return true;

        } else if (m == n && board[_y][_x].getChessToken().getChessPieceType().equals(ChessPieceType.PAWN) && x != 7 &&
                board[_y][_x].getChessToken().getPiece().equals(Players.BLACK) &&
                board[y][x + 1].getChessToken() != null && !board[y][x + 1].getChessToken().getPiece().equals(co))
            return true;
        return false;
    }

    private boolean ifAnyMatching(int x, int y) {
        var z = new int[]{x, y};
        for (int[] t : possible_position) {
            if (Arrays.equals(t, z)) {
                return true;
            }
        }
        for (int[] ints : castling) {
            if (Arrays.equals(z, ints))
                return true;
        }
        return (enPassant.length > 0) && (Arrays.equals(z, enPassant));
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
        return (x >= 0 && x < 8 && y >= 0 && y < 8 && board[y][x].getChessToken() == null);
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

    private boolean isValidCastling(int x, int y, Players players, String direction) {
        if (players.equals(Players.BLACK)) {
            if (direction.equals("left"))
                return board[y][x - 1].getChessToken() == null &&
                        board[y][x - 2].getChessToken() == null &&
                        board[y][x - 3].getChessToken() == null &&
                        board[0][0].getChessToken().getChessPieceType().equals(ChessPieceType.ROOK) &&
                        !isKingChecked && board[0][0].getChessToken().getPiece().equals(Players.BLACK);
            if (direction.equals("right")) {
                return board[y][x + 1].getChessToken() == null &&
                        board[y][x + 2].getChessToken() == null &&
                        board[0][7].getChessToken().getChessPieceType().equals(ChessPieceType.ROOK) &&
                        !isKingChecked && board[0][7].getChessToken().getPiece().equals(Players.BLACK);
            }
        } else if (players.equals(Players.WHITE))
            if (direction.equals("left"))
                return board[y][x - 1].getChessToken() == null &&
                        board[y][x - 2].getChessToken() == null &&
                        board[y][x - 3].getChessToken() == null &&
                        board[7][0].getChessToken().getChessPieceType().equals(ChessPieceType.ROOK) &&
                        !isKingChecked && board[7][0].getChessToken().getPiece().equals(Players.WHITE);
        if (direction.equals("right")) {
            return board[y][x + 1].getChessToken() == null &&
                    board[y][x + 2].getChessToken() == null &&
                    board[7][7].getChessToken().getChessPieceType().equals(ChessPieceType.ROOK) &&
                    !isKingChecked && board[7][7].getChessToken().getPiece().equals(Players.WHITE);
        }
        return false;

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
        if (isCastlingValid_Black || isCastlingValid_White) {
            if (y == 0 && isCastlingValid_Black && color.equals(Players.BLACK) && isValidCastling(x, y, color, "left")) {
                castling.add(new int[]{x - 2, y});
                board[y][x - 2].setSelected(true, Colors.ORANGE);
            }
            if (y == 0 && isCastlingValid_Black && color.equals(Players.BLACK) && isValidCastling(x, y, color, "right")) {
                castling.add(new int[]{x + 2, y});
                board[y][x + 2].setSelected(true, Colors.ORANGE);
            }
            if (y == 7 && isCastlingValid_White && color.equals(Players.WHITE) && isValidCastling(x, y, color, "left")) {
                castling.add(new int[]{x - 2, y});
                board[y][x - 2].setSelected(true, Colors.ORANGE);
            }
            if (y == 7 && isCastlingValid_White && color.equals(Players.WHITE) && isValidCastling(x, y, color, "right")) {
                castling.add(new int[]{x + 2, y});
                board[y][x + 2].setSelected(true, Colors.ORANGE);
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
            if (enPassant.length > 0 && (y == 3 || y == 4)) {
                board[enPassant[1]][enPassant[0]].setSelected(true, Colors.ORANGE);
            }

        }
        return list;
    }
}

