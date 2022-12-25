package org.ascii.chess;

import org.ascii.chess.util.Colors;
import org.ascii.chess.util.Text;

public class ChessToken {
    private final ChessPieceType chessPieceType;
    private PieceColor pieceColor;
    private Colors color;
    private  int noOfMoves;

    public int getNoOfMoves() {
        return noOfMoves;
    }

    public void setNoOfMoves(int noOfMoves) {
        this.noOfMoves = noOfMoves;
    }

    public ChessPieceType getChessPieceType() {
        return chessPieceType;
    }


    public PieceColor getPiece() {
        return pieceColor;
    }

    public void setPiece(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    public ChessToken(ChessPieceType chessPieceType, Colors color, PieceColor pieceColor) {
        this.chessPieceType = chessPieceType;
        this.color = color;
        this.pieceColor = pieceColor;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public String getText() {
        return " " + Text.getColorText(this.chessPieceType.getText(), color) + " ";
    }
}
