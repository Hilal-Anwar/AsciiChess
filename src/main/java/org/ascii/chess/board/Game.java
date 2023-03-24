package org.ascii.chess.board;

import org.ascii.chess.pieces.ChessPieceType;
import org.ascii.chess.pieces.ChessToken;
import org.ascii.chess.util.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;


public class Game extends Display implements Movements {
    /**
     * @author Hilal Anwar on 01-12-2022
     * @project AsciiChess
     */
    private final ChessBoard chessBoard = new ChessBoard(new Cursor(4, 6, Colors.MAGENTA));
    private ChessBox[][] board;
    private ArrayList<Point> possible_position = new ArrayList<>();
    private Point selected_box;
    private Players turn = Players.WHITE;
    private String message = "";
    private boolean isKingChecked = false;
    private Point dangerPoint;
    private ArrayList<Point> path_to_check = new ArrayList<>();
    private final Point black_king = new Point(4, 0);
    private final Point white_king = new Point(4, 7);
    private final HashSet<Point> guard_tokens = new HashSet<>();

    private boolean isCastlingValid_Black = true;
    private boolean isCastlingValid_White = true;


    public Game() {
        init();
    }

    private Point enPassant;
    private final ArrayDeque<MovementRecord> memory = new ArrayDeque<>();
    private final ArrayDeque<Point> enPassant_memory=new ArrayDeque<>();
    private ArrayList<Point> castling = new ArrayList<>(2);

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
        chessBoard.draw(message, terminal.getWidth());
        while (true) {
            var key = keyBoardInput.getKeyBoardKey();
            switch (key) {
                case UP -> chessBoard.move_cursor_up();
                case DOWN -> chessBoard.move_cursor_down();
                case RIGHT -> chessBoard.move_cursor_right();
                case LEFT -> chessBoard.move_cursor_left();
                case ENTER -> moves();
                case BACKSPACE -> undo_moves();
                case ESC -> System.exit(-1);
            }
            if (!key.equals(Key.NONE)) {
                clear_display();
                chessBoard.draw(message, terminal.getWidth());
                //System.out.println(memory);
            }
            keyBoardInput.setKeyBoardKey(Key.NONE);
            Thread.sleep(30);
        }
    }

    private void undo_moves() {
        if (!memory.isEmpty()) {
            var move = memory.pollLast();
            if (move.finalX() == -1) {
                _undo_move(Objects.requireNonNull(memory.pollLast()));
                _undo_move(Objects.requireNonNull(memory.pollLast()));
                if (move.turn().equals(Players.BLACK))
                    isCastlingValid_Black = true;
                else
                    isCastlingValid_White = true;
            } else if (move.finalX() == -2) {
                _undo_move(Objects.requireNonNull(memory.pollLast()));
                var m = Objects.requireNonNull(memory.pollLast());
                board[m.finalY()][m.finalX()] = new
                        ChessBox(m.previous_token(), false);
                enPassant=enPassant_memory.pollLast();
                turn = m.turn();
            } else {
                _undo_move(move);
            }
        }
    }

    private void _undo_move(MovementRecord move) {
        int f_x = move.finalX();
        int f_y = move.finalY();
        int i_x = move.initialX();
        int i_y = move.initialY();
        var piece = board[f_y][f_x];
        board[i_y][i_x] = piece;
        board[f_y][f_x] = new ChessBox(move.previous_token(), false);
        turn = move.turn();
    }

    private void moves() {
        int x = chessBoard.getColumn();
        int y = chessBoard.getRow();
        var chess_box = board[y][x];
        Point pr = new Point(x, y);
        if ((turn.isEqual(chess_box) || selected_box != null) && !guard_tokens.contains(pr)) {
            if (chess_box.getChessToken() != null && selected_box == null) {
                var chess_piece_type = chess_box.getChessToken().getChessPieceType();
                getPossiblePosition(x, y, chess_box, chess_piece_type);
                if (possible_position.size() != 0) {
                    if (isKingChecked) {
                        possible_position = intersection(possible_position, path_to_check);
                    }
                    for (var sl : possible_position) {
                        board[sl.y][sl.x].
                                setSelected(true, board[sl.y][sl.x].getChessToken() != null ?
                                        Colors.RED : Colors.GREEN);
                    }
                    selected_box = new Point(x, y);
                    if (board[y][x].getChessToken() != null) {
                        if (!board[y][x].isSelected())
                            board[y][x].setSelected(true, Colors.BLUE);
                    }
                }
            } else if (selected_box != null) {
                if (ifAnyMatching(x, y)) {
                    int _x = selected_box.x;
                    int _y = selected_box.y;
                    if (y == 0 && board[_y][_x].getChessToken().getPiece().equals(Players.WHITE) &&
                            board[_y][_x].getChessToken().getChessPieceType().equals(ChessPieceType.PAWN))
                        move_the_piece_to(x, y, _x, _y, new ChessBox(new ChessToken(ChessPieceType.QUEEN,
                                Colors.CYAN_BRIGHT, Players.WHITE), false));
                    else if (y == 7 && board[_y][_x].getChessToken().getPiece().equals(Players.BLACK) &&
                            board[_y][_x].getChessToken().getChessPieceType().equals(ChessPieceType.PAWN))
                        move_the_piece_to(x, y, _x, _y, new ChessBox(new ChessToken(ChessPieceType.QUEEN,
                                Colors.MAGENTA, Players.BLACK), false));
                    else if (isEnPassant(y, _y, _x, x, y) && turn.equals(Players.BLACK)) {
                        enPassant = new Point(x, y - 1);
                        move_the_piece_to(x, y, _x, _y, board[_y][_x]);
                    } else if (isEnPassant(y, _y, _x, x, y) && turn.equals(Players.WHITE)) {
                        enPassant = new Point(x, y + 1);
                        move_the_piece_to(x, y, _x, _y, board[_y][_x]);
                    } else if (((castling.size() > 0 && castling.get(0).x == x && castling.get(0).y == y) ||
                            (castling.size() > 1 && castling.get(1).x == x && castling.get(1).y == y))) {
                        var pi = board[_y][_x].getChessToken().getPiece();
                        if (pi.equals(Players.BLACK) && isCastlingValid_Black) {
                            move_the_piece_to(x, y, _x, _y, board[_y][_x]);
                            if (x < _x) {
                                move_the_piece_to(x + 1, y, 0, 0, board[0][0]);
                                //move_the_piece_to(0, 0, _x, _y, new ChessBox(null, false));
                            } else if (x > _x) {
                                move_the_piece_to(x - 1, y, 7, 0, board[0][7]);
                                //move_the_piece_to(7, 0, _x, _y, new ChessBox(null, false));
                            }
                            memory.add(new MovementRecord(-1, -1, -1,
                                    -1, null, Players.BLACK));
                            isCastlingValid_Black = false;
                            castling = new ArrayList<>();
                        } else if (pi.equals(Players.WHITE) && isCastlingValid_White) {
                            move_the_piece_to(x, y, _x, _y, board[_y][_x]);
                            if (x < _x) {
                                move_the_piece_to(x + 1, y, 0, 7, board[7][0]);
                                //move_the_piece_to(0, 7, _x, _y, new ChessBox(null, false));
                            } else if (x > _x) {
                                move_the_piece_to(x - 1, y, 7, 7, board[7][7]);
                                //move_the_piece_to(7, 7, _x, _y, new ChessBox(null, false));
                            }
                            memory.add(new MovementRecord(-1, -1, -1,
                                    -1, null, Players.WHITE));
                            isCastlingValid_White = false;
                            castling = new ArrayList<>();
                        }
                    } else if (enPassant != null && enPassant.x == x && enPassant.y == y && enPassant.x != _x
                            && ((board[_y][_x - 1].getChessToken() != null &&
                            board[_y][_x - 1].getChessToken().getChessPieceType().equals(ChessPieceType.PAWN)) ||
                            (board[_y][_x + 1].getChessToken() != null &&
                                    board[_y][_x + 1].getChessToken().getChessPieceType().equals(ChessPieceType.PAWN)))) {
                        var m = board[_y][_x];
                        //if (board[_y][_x].getChessToken().getPiece().equals(Players.WHITE))
                        if (enPassant.x < _x)
                            move_the_piece_to(_x - 1, _y, _x, _y, new ChessBox(null, false));
                            //else if (board[_y][_x].getChessToken().getPiece().equals(Players.BLACK)) {
                        else if (enPassant.x > _x)
                            move_the_piece_to(_x + 1, _y, _x, _y, new ChessBox(null, false));
                        //}
                        move_the_piece_to(enPassant.x, enPassant.y, _x, _y, m);
                        memory.add(new MovementRecord(-2, -2, -2, -2, null, null));
                        enPassant_memory.add(enPassant);
                        enPassant = null;

                    } else
                        move_the_piece_to(x, y, _x, _y, board[_y][_x]);
                    //move_the_piece_to(_x, _y, new ChessBox(null, false));
                    turn = turn.equals(Players.WHITE) ?
                            Players.BLACK : Players.WHITE;
                }
                board[selected_box.y][selected_box.x].
                        setSelected(false, Colors.WHITE);
                dSelect(possible_position);
                if (enPassant != null) {
                    board[enPassant.y][enPassant.x].
                            setSelected(false, Colors.WHITE);
                }
                dSelect(castling);

                selected_box = null;
            }
            message = "";
        } else if (guard_tokens.contains(pr)) {
            message = "This is guard piece to your king.";
        } else if (chess_box.getChessToken() != null) {
            message = "Invalid move its," + turn.name() + " turn " +
                    chess_box.getChessToken().getPiece().name() + " cannot be moved";
        }


    }

    private void dSelect(ArrayList<Point> possible_position) {
        for (var sl : possible_position) {
            board[sl.y][sl.x].setSelected(false, Colors.WHITE);
        }
    }

    private void getPossiblePosition(int x, int y, ChessBox chess_box, ChessPieceType chess_piece_type) {
        possible_position = switch (chess_piece_type) {
            case KING -> king_movement(x, y, chess_box.getChessToken().
                    getPiece());
            case QUEEN -> queen_movement(x, y, chess_box.getChessToken().
                    getPiece());
            case BISHOP -> bishop_movement(x, y, chess_box.getChessToken().
                    getPiece());
            case ROOK -> rook_movement(x, y, chess_box.getChessToken().
                    getPiece());
            case KNIGHT -> knight_movement(x, y, chess_box.getChessToken().
                    getPiece());
            case PAWN -> pawn_movement(x, y, chess_box.getChessToken(),
                    chess_box.getChessToken().getPiece());
        };
    }

    private boolean isEnPassant(int m, int _y, int _x, int x, int y) {
        Players co = board[_y][_x].getChessToken().getPiece();
        if (m == 3 &&
                board[_y][_x].getChessToken().getChessPieceType().
                        equals(ChessPieceType.PAWN) && x != 0 &&
                board[_y][_x].getChessToken().
                        getPiece().equals(Players.BLACK) &&
                board[y][x - 1].getChessToken() != null &&
                !board[y][x - 1].getChessToken().getPiece().equals(co)) {
            return true;

        } else if (m == 3 && board[_y][_x].getChessToken().getChessPieceType().equals(ChessPieceType.PAWN) && x != 7 &&
                board[_y][_x].getChessToken().getPiece().equals(Players.BLACK) &&
                board[y][x + 1].getChessToken() != null && !board[y][x + 1].getChessToken().getPiece().equals(co)) {
            return true;
        } else if (m == 4 && board[_y][_x].getChessToken().getChessPieceType().equals(ChessPieceType.PAWN) && x != 7 &&
                board[_y][_x].getChessToken().getPiece().equals(Players.WHITE) &&
                board[y][x + 1].getChessToken() != null && !board[y][x + 1].getChessToken().getPiece().equals(co))
            return true;
        else return m == 4 && board[_y][_x].getChessToken().getChessPieceType().equals(ChessPieceType.PAWN) && x != 0 &&
                    board[_y][_x].getChessToken().getPiece().equals(Players.WHITE) &&
                    board[y][x - 1].getChessToken() != null && !board[y][x - 1].getChessToken().getPiece().equals(co);
    }

    private boolean ifAnyMatching(int x, int y) {
        var z = new Point(x, y);
        for (var t : possible_position) {
            if (t.equals(z)) {
                return true;
            }
        }
        for (var ints : castling) {
            if (ints.equals(z))
                return true;
        }
        return (z.equals(enPassant)) && enPassant.x != selected_box.x;
    }

    @Override
    public ArrayList<Point> rook_movement(int x, int y, Players color) {
        int degree_f = 4;
        int[][] freedom = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int[][] movement = {{x, y}, {x, y}, {x, y}, {x, y}};
        var list = new ArrayList<Point>();
        while (degree_f > 0) {
            for (int i = 0; i < 4; i++) {
                if (freedom[i] != null) {
                    int p = freedom[i][0] + movement[i][0];
                    int q = freedom[i][1] + movement[i][1];
                    if (isValidPoint(p, q)) {
                        movement[i][0] = p;
                        movement[i][1] = q;
                        list.add(new Point(p, q));
                    } else if (isValidPointFilledPosition(p, q, color)) {
                        movement[i][0] = p;
                        movement[i][1] = q;
                        list.add(new Point(p, q));
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

    private boolean isValidPoint(int x, int y) {
        return (x >= 0 && x < 8 && y >= 0 && y < 8
                && board[y][x].getChessToken() == null);
    }

    private boolean isValidPointFilledPosition(int x, int y, Players color) {
        return (x >= 0 && x < 8 && y >= 0 && y < 8 &&
                board[y][x].getChessToken() != null &&
                !color.equals(board[y][x].getChessToken().getPiece()));
    }

    @Override
    public ArrayList<Point> bishop_movement(int x, int y, Players color) {
        int degree_f = 4;
        int[][] freedom = {{1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
        int[][] movement = {{x, y}, {x, y}, {x, y}, {x, y}};
        var list = new ArrayList<Point>();
        while (degree_f > 0) {
            for (int i = 0; i < 4; i++) {
                if (freedom[i] != null) {
                    int p = freedom[i][0] + movement[i][0];
                    int q = freedom[i][1] + movement[i][1];
                    if (isValidPoint(p, q)) {
                        movement[i][0] = p;
                        movement[i][1] = q;
                        list.add(new Point(p, q));
                    } else if (isValidPointFilledPosition(p, q, color)) {
                        movement[i][0] = p;
                        movement[i][1] = q;
                        list.add(new Point(p, q));
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
    public ArrayList<Point> knight_movement(int x, int y, Players color) {
        int[][] freedom = {{1, 2}, {-1, 2}, {1, -2},
                {-1, -2}, {2, 1}, {2, -1},
                {-2, 1}, {-2, 1}};
        int[][] movement = {{x, y}, {x, y}, {x, y}, {x, y},
                {x, y}, {x, y}, {x, y}, {x, y}};
        var list = new ArrayList<Point>();
        for (int i = 0; i < 8; i++) {
            int p = freedom[i][0] + movement[i][0];
            int q = freedom[i][1] + movement[i][1];
            if (isValidPoint(p, q) ||
                    isValidPointFilledPosition(p, q, color)) {
                movement[i][0] = p;
                movement[i][1] = q;
                list.add(new Point(p, q));
            }
        }
        return list;
    }

    private boolean isValidCastling(int x, int y,
                                    Players players, String direction) {
        if (players.equals(Players.BLACK)) {
            if (direction.equals("left"))
                return board[y][x - 1].getChessToken() == null &&
                        board[y][x - 2].getChessToken() == null &&
                        board[y][x - 3].getChessToken() == null &&
                        board[0][0].getChessToken().
                                getChessPieceType().
                                equals(ChessPieceType.ROOK) &&
                        !isKingChecked && board[0][0].getChessToken().
                        getPiece().equals(Players.BLACK);
            if (direction.equals("right")) {
                return board[y][x + 1].getChessToken() == null &&
                        board[y][x + 2].getChessToken() == null &&
                        board[0][7].getChessToken().
                                getChessPieceType().
                                equals(ChessPieceType.ROOK) &&
                        !isKingChecked && board[0][7].getChessToken().
                        getPiece().equals(Players.BLACK);
            }
        } else if (players.equals(Players.WHITE))
            if (direction.equals("left"))
                return board[y][x - 1].getChessToken() == null &&
                        board[y][x - 2].getChessToken() == null &&
                        board[y][x - 3].getChessToken() == null &&
                        board[7][0].getChessToken().getChessPieceType().
                                equals(ChessPieceType.ROOK) &&
                        !isKingChecked && board[7][0].getChessToken().
                        getPiece().equals(Players.WHITE);
        if (direction.equals("right")) {
            return board[y][x + 1].getChessToken() == null &&
                    board[y][x + 2].getChessToken() == null &&
                    board[7][7].getChessToken().getChessPieceType().
                            equals(ChessPieceType.ROOK) &&
                    !isKingChecked && board[7][7].getChessToken().
                    getPiece().equals(Players.WHITE);
        }
        return false;

    }

    @Override
    public ArrayList<Point> queen_movement(int x, int y, Players color) {
        int degree_f = 8;
        int[][] freedom = {{0, 1}, {0, -1}, {1, 0},
                {-1, 0}, {1, 1}, {-1, 1},
                {1, -1}, {-1, -1}};
        int[][] movement = {{x, y}, {x, y}, {x, y},
                {x, y}, {x, y}, {x, y},
                {x, y}, {x, y}};
        var list = new ArrayList<Point>();
        while (degree_f > 0) {
            for (int i = 0; i < 8; i++) {
                if (freedom[i] != null) {
                    int p = freedom[i][0] + movement[i][0];
                    int q = freedom[i][1] + movement[i][1];
                    if (isValidPoint(p, q)) {
                        movement[i][0] = p;
                        movement[i][1] = q;
                        list.add(new Point(p, q));
                    } else if (isValidPointFilledPosition(p, q, color)) {
                        movement[i][0] = p;
                        movement[i][1] = q;
                        list.add(new Point(p, q));
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
    public ArrayList<Point> king_movement(int x, int y, Players color) {
        int[][] freedom = {{0, 1}, {0, -1}, {1, 0},
                {-1, 0}, {1, 1}, {-1, 1},
                {1, -1}, {-1, -1}};
        int[][] movement = {{x, y}, {x, y}, {x, y},
                {x, y}, {x, y}, {x, y},
                {x, y}, {x, y}};
        var list = new ArrayList<Point>();
        for (int i = 0; i < 8; i++) {
            int p = freedom[i][0] + movement[i][0];
            int q = freedom[i][1] + movement[i][1];
            if (isValidPoint(p, q) ||
                    isValidPointFilledPosition(p, q, color)) {
                movement[i][0] = p;
                movement[i][1] = q;
                if (isKingSafeIn(new Point(p, q), color))
                    list.add(new Point(p, q));
            }
        }
        if (isCastlingValid_Black || isCastlingValid_White) {
            if (y == 0 && isCastlingValid_Black &&
                    color.equals(Players.BLACK) &&
                    isValidCastling(x, y, color, "left") &&
                    isKingSafeIn(new Point(x - 2, y), color)) {
                castling.add(new Point(x - 2, y));
                board[y][x - 2].setSelected(true, Colors.ORANGE);
            }
            if (y == 0 && isCastlingValid_Black &&
                    color.equals(Players.BLACK) &&
                    isValidCastling(x, y, color, "right") &&
                    isKingSafeIn(new Point(x + 2, y), color)) {
                castling.add(new Point(x + 2, y));
                board[y][x + 2].setSelected(true, Colors.ORANGE);
            }
            if (y == 7 && isCastlingValid_White
                    && color.equals(Players.WHITE) &&
                    isValidCastling(x, y, color, "left") &&
                    isKingSafeIn(new Point(x - 2, y), color)) {
                castling.add(new Point(x - 2, y));
                board[y][x - 2].setSelected(true, Colors.ORANGE);
            }
            if (y == 7 && isCastlingValid_White &&
                    color.equals(Players.WHITE) &&
                    isValidCastling(x, y, color, "right") &&
                    isKingSafeIn(new Point(x + 2, y), color)) {
                castling.add(new Point(x + 2, y));
                board[y][x + 2].setSelected(true, Colors.ORANGE);
            }
        }
        return list;
    }

    @Override
    public ArrayList<Point> pawn_movement(int x, int y,
                                          ChessToken chessToken,
                                          Players color) {
        int[][] freedom;
        var list = new ArrayList<Point>();
        int[][] w = {{0, -1}, {1, -1}, {-1, -1}};
        int[][] b = {{0, 1}, {1, 1}, {-1, 1}};
        freedom = color.equals(Players.WHITE) ? w : b;
        int[][] movement = {{x, y}, {x, y}, {x, y}};
        for (int i = 0; i < 3; i++) {
            int p = freedom[i][0] + movement[i][0];
            int q = freedom[i][1] + movement[i][1];
            if (isValidPoint(p, q) ||
                    isValidPointFilledPosition(p, q, color)) {
                movement[i][0] = p;
                movement[i][1] = q;
                switch (i) {
                    case 0 -> {
                        if (isValidPoint(p, q)) {
                            list.add(new Point(p, q));
                            if (y == 1 || y == 6) {
                                p = freedom[i][0] + movement[i][0];
                                q = freedom[i][1] + movement[i][1];
                                if (isValidPoint(p, q))
                                    list.add(new Point(p, q));
                            }
                        }
                    }
                    case 1, 2 -> {
                        if (isValidPointFilledPosition(p, q, color))
                            list.add(new Point(p, q));
                    }
                }
            }
            if (enPassant != null && y == 3 && turn.equals(Players.WHITE)
                    && x != 7 && x != 0 && board[y][x].getChessToken() != null
                    && board[y][x].getChessToken().
                    getChessPieceType().equals(ChessPieceType.PAWN)) {
                board[enPassant.y][enPassant.x].
                        setSelected(true, Colors.ORANGE);
            }
            if (enPassant != null && y == 4 && turn.equals(Players.BLACK)
                    && x != 7 && x != 0 &&
                    board[y][x].getChessToken() != null && board[y][x].
                    getChessToken().
                    getChessPieceType().equals(ChessPieceType.PAWN)) {
                board[enPassant.y][enPassant.x].
                        setSelected(true, Colors.ORANGE);
            }

        }
        return list;
    }

    private void move_the_piece_to(int x, int y, int _x, int _y,
                                   ChessBox chessBox) {
        chessBox.setSelected(false, Colors.WHITE);
        if (chessBox.getChessToken() != null && chessBox.getChessToken().
                getChessPieceType().equals(ChessPieceType.KING)) {
            if (chessBox.getChessToken().getPiece().equals(Players.BLACK)) {
                black_king.x = x;
                black_king.y = y;
            } else {
                white_king.x = x;
                white_king.y = y;
            }
        }
        memory.add(new MovementRecord(_x, _y, x, y,
                board[y][x].getChessToken(), turn));
        board[y][x] = chessBox;
        board[_y][_x] = new ChessBox(null, false);
        dSelect(possible_position);
        if (chessBox.getChessToken() != null) {
            guard_tokens.clear();
            //System.out.println("Guard token is made clear");
            board[black_king.y][black_king.x].
                    setSelected(false, Colors.WHITE);
            board[white_king.y][white_king.x].
                    setSelected(true, Colors.WHITE);
            path_to_check.clear();
            isKingChecked = false;
            dangerPoint = null;
            boolean condition = turn.equals(Players.BLACK) ?
                    isKingSafeIn(white_king, Players.WHITE) :
                    isKingSafeIn(black_king, Players.BLACK);
            if (turn.equals(Players.WHITE) && !condition) {
                board[black_king.y][black_king.x].
                        setSelected(true, Colors.RED);
            } else if (turn.equals(Players.BLACK) && !condition) {
                board[white_king.y][white_king.x].
                        setSelected(true, Colors.RED);
            }
            //System.out.println("Guard token is = " + guard_tokens);
        }
    }

    private boolean isKingSafeIn(Point point, Players players) {
        return isKingSafeFromAllDirection(point, players) &&
                isKingSafeFromKnight(point, players);

    }

    private boolean isKingSafeFromKnight(Point point, Players players) {
        var path = knight_movement(point.x, point.y, players);
        for (var p : path) {
            if (board[p.y][p.x].getChessToken() != null &&
                    board[p.y][p.x].getChessToken().
                            getChessPieceType().
                            equals(ChessPieceType.KNIGHT)) {
                dangerPoint = new Point(p.x, p.y);
                return false;
            }
        }
        return true;
    }

    private boolean isKingSafeFromAllDirection(Point testPoint, Players players) {
        var king = players.equals(Players.BLACK) ? black_king : white_king;
        var directions = Directions.values();
        for (var direction : directions) {
            Path path = switch (direction) {
                case UP -> check_move_up(testPoint, players);
                case DOWN -> check_move_down(testPoint, players);
                case LEFT -> check_move_left(testPoint, players);
                case RIGHT -> check_move_right(testPoint, players);
                case UP_LEFT -> check_move_up_left(testPoint, players);
                case UP_RIGHT -> check_move_up_right(testPoint, players);
                case DOWN_LEFT -> check_move_down_left(testPoint, players);
                case DOWN_RIGHT -> check_move_down_right(testPoint, players);
            };
            //System.out.println(direction + "  " + path);
            if (path.isDanger() && !king.equals(testPoint)) {
                return false;
            } else if (path.isDanger() && path.guardPoint() == null) {
                path_to_check = path.points();
                isKingChecked = true;
                return false;
            } else if (path.isDanger()) {
                guard_tokens.add(path.guardPoint());
            }
        }
        return true;
    }

    private boolean isPawnOrBishopOrQueen(int ix, int iy, int x, int y, Players piece) {
        if (!board[y][x].getChessToken().getPiece().equals(piece)) {
            if (board[y][x].getChessToken().getChessPieceType().
                    equals(ChessPieceType.PAWN) && Math.abs(ix - x) == 1 &&
                    Math.abs(iy - y) == 1)
                return true;
            else if (board[y][x].getChessToken().getChessPieceType().
                    equals(ChessPieceType.QUEEN))
                return true;
            else return board[y][x].getChessToken().getChessPieceType().
                        equals(ChessPieceType.BISHOP);
        }
        return false;
    }

    private boolean isQueenOrRook(int x, int y, Players piece) {
        if (!board[y][x].getChessToken().getPiece().equals(piece)) {
            if (board[y][x].getChessToken().getChessPieceType().
                    equals(ChessPieceType.QUEEN))
                return true;
            else return board[y][x].getChessToken().getChessPieceType().
                    equals(ChessPieceType.ROOK);
        }
        return false;
    }

    private boolean isInSideBoard(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    private Path check_move_up(Point point, Players players) {
        var points = new ArrayList<Point>();
        Point guardPoint = null;
        int x = point.x;
        int y = point.y;
        while (isInSideBoard(x, y)) {
            y = y - 1;
            if (isValidPointFilledPosition(x, y, players)) {
                if (isQueenOrRook(x, y, players)) {
                    dangerPoint = new Point(x, y);
                    return new Path(true, guardPoint, points);
                } else {
                    return new Path(false, guardPoint, points);
                }
            } else if (isValidPoint(x, y)) {
                points.add(new Point(x, y));
            } else if (isInSideBoard(x, y)) {
                if (guardPoint == null)
                    guardPoint = new Point(x, y);
                else {
                    return new Path(false, guardPoint, points);
                }
            }
        }
        return new Path(false, guardPoint, points);
    }

    private Path check_move_down(Point point, Players players) {
        var points = new ArrayList<Point>();
        Point guardPoint = null;
        int x = point.x;
        int y = point.y;
        while (isInSideBoard(x, y)) {
            y = y + 1;
            if (isValidPointFilledPosition(x, y, players)) {
                if (isQueenOrRook(x, y, players)) {
                    dangerPoint = new Point(x, y);
                    return new Path(true, guardPoint, points);
                } else {
                    return new Path(false, guardPoint, points);
                }
            } else if (isValidPoint(x, y)) {
                points.add(new Point(x, y));
            } else if (isInSideBoard(x, y)) {
                if (guardPoint == null)
                    guardPoint = new Point(x, y);
                else {
                    return new Path(false, guardPoint, points);
                }
            }
        }
        return new Path(false, guardPoint, points);
    }

    private Path check_move_right(Point point, Players players) {
        var points = new ArrayList<Point>();
        Point guardPoint = null;
        int x = point.x;
        int y = point.y;
        while (isInSideBoard(x, y)) {
            x = x + 1;
            if (isValidPointFilledPosition(x, y, players)) {
                if (isQueenOrRook(x, y, players)) {
                    dangerPoint = new Point(x, y);
                    return new Path(true, guardPoint, points);
                } else {
                    return new Path(false, guardPoint, points);
                }
            } else if (isValidPoint(x, y)) {
                points.add(new Point(x, y));
            } else if (isInSideBoard(x, y)) {
                if (guardPoint == null)
                    guardPoint = new Point(x, y);
                else {
                    return new Path(false, guardPoint, points);
                }
            }
        }
        return new Path(false, guardPoint, points);
    }

    private Path check_move_left(Point point, Players players) {
        var points = new ArrayList<Point>();
        Point guardPoint = null;
        int x = point.x;
        int y = point.y;
        while (isInSideBoard(x, y)) {
            x = x - 1;
            if (isValidPointFilledPosition(x, y, players)) {
                if (isQueenOrRook(x, y, players)) {
                    dangerPoint = new Point(x, y);
                    return new Path(true, guardPoint, points);
                } else {
                    return new Path(false, guardPoint, points);
                }
            } else if (isValidPoint(x, y)) {
                points.add(new Point(x, y));
            } else if (isInSideBoard(x, y)) {
                if (guardPoint == null)
                    guardPoint = new Point(x, y);
                else {
                    return new Path(false, guardPoint, points);
                }
            }
        }
        return new Path(false, guardPoint, points);
    }

    private Path check_move_up_right(Point point, Players players) {
        var points = new ArrayList<Point>();
        Point guardPoint = null;
        int x = point.x;
        int y = point.y;
        while (isInSideBoard(x, y)) {
            x = x + 1;
            y = y - 1;
            if (isValidPointFilledPosition(x, y, players)) {
                if (isPawnOrBishopOrQueen(point.x, point.y, x, y, players)) {
                    dangerPoint = new Point(x, y);
                    return new Path(true, guardPoint, points);
                } else {
                    return new Path(false, guardPoint, points);
                }
            } else if (isValidPoint(x, y)) {
                points.add(new Point(x, y));
            } else if (isInSideBoard(x, y)) {
                if (guardPoint == null)
                    guardPoint = new Point(x, y);
                else {
                    return new Path(false, guardPoint, points);
                }
            }
        }
        return new Path(false, guardPoint, points);
    }

    private Path check_move_up_left(Point point, Players players) {
        var points = new ArrayList<Point>();
        Point guardPoint = null;
        int x = point.x;
        int y = point.y;
        while (isInSideBoard(x, y)) {
            x = x - 1;
            y = y - 1;
            if (isValidPointFilledPosition(x, y, players)) {
                if (isPawnOrBishopOrQueen(point.x, point.y, x, y, players)) {
                    dangerPoint = new Point(x, y);
                    return new Path(true, guardPoint, points);
                } else {
                    return new Path(false, guardPoint, points);
                }
            } else if (isValidPoint(x, y)) {
                points.add(new Point(x, y));
            } else if (isInSideBoard(x, y)) {
                if (guardPoint == null)
                    guardPoint = new Point(x, y);
                else {
                    return new Path(false, guardPoint, points);
                }
            }
        }
        return new Path(false, guardPoint, points);
    }

    private Path check_move_down_right(Point point, Players players) {
        var points = new ArrayList<Point>();
        Point guardPoint = null;
        int x = point.x;
        int y = point.y;
        while (isInSideBoard(x, y)) {
            x = x + 1;
            y = y + 1;
            if (isValidPointFilledPosition(x, y, players)) {
                if (isPawnOrBishopOrQueen(point.x, point.y, x, y, players)) {
                    dangerPoint = new Point(x, y);
                    return new Path(true, guardPoint, points);
                } else {
                    return new Path(false, guardPoint, points);
                }
            } else if (isValidPoint(x, y)) {
                points.add(new Point(x, y));
            } else if (isInSideBoard(x, y)) {
                if (guardPoint == null)
                    guardPoint = new Point(x, y);
                else {
                    return new Path(false, guardPoint, points);
                }
            }
        }
        return new Path(false, guardPoint, points);
    }

    private Path check_move_down_left(Point point, Players players) {
        var points = new ArrayList<Point>();
        Point guardPoint = null;
        int x = point.x;
        int y = point.y;
        while (isInSideBoard(x, y)) {
            x = x - 1;
            y = y + 1;
            if (isValidPointFilledPosition(x, y, players)) {
                if (isPawnOrBishopOrQueen(point.x, point.y, x, y, players)) {
                    dangerPoint = new Point(x, y);
                    return new Path(true, guardPoint, points);
                } else {
                    return new Path(false, guardPoint, points);
                }
            } else if (isValidPoint(x, y)) {
                points.add(new Point(x, y));
            } else if (isInSideBoard(x, y)) {
                if (guardPoint == null)
                    guardPoint = new Point(x, y);
                else {
                    return new Path(false, guardPoint, points);
                }
            }
        }
        return new Path(false, guardPoint, points);
    }

    private ArrayList<Point> intersection(ArrayList<Point> list1,
                                          ArrayList<Point> list2) {
        if (!list2.isEmpty()) {
            int size = Math.min(list1.size(), list2.size());
            var list = new ArrayList<Point>();
            if (isKingChecked) {
                for (int i = 0; i < size; i++) {
                    if (list2.contains(list1.get(i)))
                        list.add(list1.get(i));
                    else if (list1.contains(dangerPoint))
                        list.add(dangerPoint);
                }
                return list;
            }
            return list;
        } else {
            if (list1.contains(dangerPoint)) {
                list1.removeIf(t -> !t.equals(dangerPoint));
            }
        }
        return list1;
    }

}