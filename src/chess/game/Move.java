/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.game;

import chess.Pieces.PieceType;
import chess.game.board.Position;

/**
 *
 * @author CarrollFD
 */
public class Move {
    private boolean moveColor;
    
    // describes the type of piece and its start/end positions
    private PieceType type;
    private Position startPosition;
    private Position endPosition;
    
    // second set necessary if a second piece is involved in the move (a
    // castle for instance)
    private PieceType typeTwo;
    private Position startPositionTwo;
    private Position endPositionTwo;

    public boolean isMoveColor() {
        return moveColor;
    }

    public PieceType getType() {
        return type;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public PieceType getTypeTwo() {
        return typeTwo;
    }

    public Position getStartPositionTwo() {
        return startPositionTwo;
    }

    public Position getEndPositionTwo() {
        return endPositionTwo;
    }
}
