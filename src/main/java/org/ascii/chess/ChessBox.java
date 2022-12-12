package org.ascii.chess;

public class ChessBox {
    private ChessToken chessToken;
    private boolean selected;

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

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
