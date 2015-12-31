package chess.game;

import chess.pieces.PieceType;
import chess.game.board.Position;

/**
 * Represents a single move in the chess game.
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

    public Move(PieceType type, Position startPosition, Position endPosition) {
        this.type = type;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public Move(PieceType type, Position startPosition, Position endPosition, PieceType typeTwo, Position startPositionTwo, Position endPositionTwo) {
        this.type = type;
        this.startPosition = startPosition;
        this.endPosition = endPosition;

        this.typeTwo = typeTwo;
        this.startPositionTwo = startPositionTwo;
        this.endPositionTwo = endPositionTwo;
    }

    /**
     * Copy Constructor
     *
     * @param toCopy Move to copy
     */
    public Move(Move toCopy) {
        moveColor = toCopy.moveColor;
        type = toCopy.type;
        startPosition = new Position(toCopy.startPosition);
        endPosition = new Position(toCopy.endPosition);
        typeTwo = toCopy.typeTwo;
        startPositionTwo = new Position(toCopy.startPositionTwo);
        endPositionTwo = new Position(toCopy.endPositionTwo);
    }

    /**
     * @return The color of the piece that moved
     */
    public boolean isMoveColor() {
        return moveColor;
    }

    /**
     * @return The type of the piece that moved
     */
    public PieceType getType() {
        return type;
    }

    /**
     * @return The starting position of the move
     */
    public Position getStartPosition() {
        return startPosition;
    }

    /**
     * @return The ending position of the move
     */
    public Position getEndPosition() {
        return endPosition;
    }

    /**
     * @return The type of any second piece involved in the move
     */
    public PieceType getTypeTwo() {
        return typeTwo;
    }

    /**
     * @return The starting position of a second piece
     */
    public Position getStartPositionTwo() {
        return startPositionTwo;
    }

    /**
     * @return The ending position of a second piece
     */
    public Position getEndPositionTwo() {
        return endPositionTwo;
    }
}
