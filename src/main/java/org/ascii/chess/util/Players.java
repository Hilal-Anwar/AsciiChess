package org.ascii.chess.util;

import org.ascii.chess.ChessBox;

public enum Players {
    BLACK,WHITE;
    public boolean isEqual(ChessBox token){
      if (token.getChessToken()==null)
          return false;
      else return token.getChessToken().getPiece().equals(this);
    }
}
