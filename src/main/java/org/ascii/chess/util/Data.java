package org.ascii.chess.util;

import org.ascii.chess.pieces.ChessToken;

/**
 * @author hilal on 22-03-2023
 * @project AsciiChess
 */
public record Data(int initialX,
                   int initialY,
                   int finalX, int finalY,
                   ChessToken previous_token,
                   Players turn)  {
}
