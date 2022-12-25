package org.ascii.chess;

import org.ascii.chess.util.Colors;

public class ChessBox {
    private ChessToken chessToken;
    private boolean selected;
    private Colors colors=Colors.WHITE;
    public ChessBox(ChessToken chessToken, boolean selected) {
        this.chessToken = chessToken;
        this.selected = selected;
    }

    public ChessToken getChessToken() {
        return chessToken;
    }

    public void setChessToken(ChessToken chessToken) {
        this.chessToken = chessToken;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected,Colors colors) {
        this.selected = selected;
        this.colors=colors;
    }

    public Colors getColors() {
        return colors;
    }
}
