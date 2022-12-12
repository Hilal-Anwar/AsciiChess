package org.ascii.chess;

import org.ascii.chess.util.Colors;
import org.ascii.chess.util.Text;

public class ChessToken {
    private String name;
    private ChessPieceType chessPieceType;
    private Piece piece;
    private Colors color;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ChessPieceType getChessPieceType() {
        return chessPieceType;
    }

    public void setChessPieceType(ChessPieceType chessPieceType) {
        this.chessPieceType = chessPieceType;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public ChessToken(ChessPieceType chessPieceType,
                      Colors color,  Piece piece) {
        this.chessPieceType = chessPieceType;
        this.color = color;
        this.piece = piece;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public String getText() {
        return " "+Text.getColorText(this.chessPieceType.getText(),color)+" ";
    }
}
