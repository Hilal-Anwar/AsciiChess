package org.ascii.chess.pieces;

import org.ascii.chess.util.Colors;
import org.ascii.chess.util.Players;
import org.ascii.chess.util.Text;

public class ChessToken {
    private final ChessPieceType chessPieceType;
    private Players players;
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


    public Players getPiece() {
        return players;
    }

    public void setPiece(Players players) {
        this.players = players;
    }

    public ChessToken(ChessPieceType chessPieceType, Colors color, Players players) {
        this.chessPieceType = chessPieceType;
        this.color = color;
        this.players = players;
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
