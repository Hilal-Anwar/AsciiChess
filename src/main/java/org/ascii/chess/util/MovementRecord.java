package org.ascii.chess.util;

import org.ascii.chess.pieces.ChessToken;

/**
 * @author hilal on 09-01-2023
 * @project AsciiChess
 */
public record MovementRecord(int initialX,
                             int initialY,
                             int finalX, int finalY,
                             ChessToken previous_token,
                             ChessToken given_token) {
}
