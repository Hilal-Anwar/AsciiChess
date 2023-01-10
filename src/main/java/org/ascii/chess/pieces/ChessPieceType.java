package org.ascii.chess.pieces;

public enum ChessPieceType {
    KING("♔"), ROOK("♖"), BISHOP("♗"), QUEEN("♕"),
    KNIGHT("♘"), PAWN("♙");

    private final String text;

    ChessPieceType(String text) {
        this.text = text;
    }

    public String getText(){
        return text;
    }
}
