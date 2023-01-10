package org.ascii.chess.board;

import org.ascii.chess.util.Cursor;
import org.ascii.chess.util.Text;

public class ChessBoard extends Cursor {

    private final int WIDTH = 16;
    private final int HEIGHT = 16;
    private final ChessBox[][] chessBoard = new ChessBox[8][8];

    public ChessBox[][] getChessBoard() {
        return chessBoard;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public ChessBoard(Cursor cursor) {
        super(cursor.getColumn(), cursor.getRow(), cursor.getColors());
    }

    public void draw(String message) {
        String[] a = {"╤", "═══", "╔", "╗"};
        String[] b = {"│", "   ", "║",};
        String[] c = {"┼", "───", "╟", "╢"};
        String[] d = {"╧", "═══", "╚", "╝"};
        StringBuilder chess = new StringBuilder();
        for (int i = 0; i <= 16; i++) {
            for (int j = 0; j <= 16; j++) {
                if (i == 0) {
                    if (j == 0)
                        chess.append(getText(i, j, a[2]));
                    else if (j == 16)
                        chess.append(getText(i, j, a[3]));
                    else chess.append(getText(i, j, a[j % 2]));
                } else if (i == 16) {
                    if (j == 0)
                        chess.append(getText(i, j, d[2]));
                    else if (j == 16)
                        chess.append(getText(i, j, d[3]));
                    else chess.append(getText(i, j, d[j % 2]));
                } else if (i % 2 != 0) {
                    if (j == 0 || j == 16)
                        chess.append(getText(i, j, b[2]));
                    else {
                        if (j % 2 != 0 && chessBoard[i / 2][j / 2] != null &&
                                chessBoard[i / 2][j / 2].getChessToken() != null) {
                            chess.append(getText(i / 2, j / 2, chessBoard[i / 2][j / 2].
                                    getChessToken().getText()));
                        } else {
                            chess.append(getText(i, j, b[j % 2]));
                        }
                        if (j % 2 == 0)
                            b[1] = b[1].equals("   ") ? " ░ " : "   ";
                    }
                } else {
                    if (j == 0)
                        chess.append(getText(i, j, c[2]));
                    else if (j == 16)
                        chess.append(getText(i, j, c[3]));
                    else chess.append(getText(i, j, c[j % 2]));
                }
            }
            chess.append("\n");

        }
        System.out.println(chess.append('\n').append(message));
    }

    private String getText(int i, int j, String text) {
        var z=isSelectedBox(i, j);
        if (isCursorPoint(i, j))
            return Text.getColorText(text, getColors());
        else if (z.condition()) {
            return Text.getColorText(text, chessBoard[z.y][z.x].getColors());
        }
        else return text;
    }

    private Val isSelectedBox(int i, int j) {
        int x = j / 2;
        int y = i / 2;
        x = (x == 8) ? x - 1 : x;
        y = (y == 8) ? y - 1 : y;
        if (chessBoard[y][x].isSelected()) {
            return new Val(true, x, y);
        } else {
            int i1 = (j != 0 && j % 2 == 0) ? j / 2 - 1 : j / 2;
            if (chessBoard[y][i1].isSelected()) {
                return new Val(true, i1, y);
            } else {
                int i2 = (i != 0 && i % 2 == 0) ? i / 2 - 1 : i / 2;
                if (chessBoard[i2][x].isSelected())
                    return new Val(chessBoard[i2][x].isSelected(), x, i2);
                return new Val(chessBoard[i2][i1].isSelected(), i1, i2);
            }
        }

    }

    private record Val(boolean condition, int x, int y) {
    }


    private boolean isCursorPoint(int i, int j) {
        int size = 2;
        if (((j == getColumn() * size || j == getColumn() * size + size) &&
                (i >= getRow() * size && i <= getRow() * size + size)))
            return true;
        else return (i == getRow() * size || i == getRow() * size + size) &&
                (j >= getColumn() * size && j <= getColumn() * size + size);
    }
}
