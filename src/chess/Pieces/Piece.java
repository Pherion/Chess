/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.Pieces;

import chess.game.GameInfoWrapper;
import chess.game.board.Position;
import java.util.List;

/**
 * Represents a generic chess piece.
 * @author CarrollFD
 */
public abstract class Piece {
    // members accessable by child classes
    protected GameInfoWrapper gameInfo;
    
    // members imutable by child classes
    private boolean color;
    private Position position;
    private boolean captured = false;
    private PieceType type;
    private boolean moved = false;
    
    protected Piece(Position position, PieceType type, boolean color, GameInfoWrapper gameInfo) {
        this.color = color;
        this.position = new Position(position);
        this.type = type;
        this.gameInfo = gameInfo;
    }

    public Piece(Piece toCopy, GameInfoWrapper gameInfo) {
        this.gameInfo = gameInfo;
        color = toCopy.color;
        position = new Position(toCopy.position);
        captured = toCopy.captured;
        type = toCopy.type;
        moved = toCopy.moved;
    }

    /**
     * Verifies if the provided position is a valid move for the given piece.
     * The assumption is made that the provided position is a position within
     * the confines of the board.
     * 
     * @param position Position to check
     * @return TRUE if the position is a valid move
     */
    public boolean validateMove(Position position) {
        // calculate the changes
        int deltaX = Math.abs(getPosition().getX() - position.getX());
        int deltaY = Math.abs(getPosition().getY() - position.getY());
        
        // ensure the position is actually different
        // check there are no intervening peices
        // check that any peices on the target position are capturable
        // check that the move is on the board
        return (deltaX != 0 || deltaY != 0) &&
               GameInfoWrapper.isOnBoard(position) &&
               !gameInfo.isInterveningPeice(this.position, position) && 
               gameInfo.isCapturable(color, position);
    }
    
    /**
     * Provides a list of valid moves for the given piece.
     * @return
     */
    public abstract List<Position> getValidMoves();
    
    /**
     * Provides a list of positions threatened by this piece.
     * At default this just returns the list of valid moves, but the Pawn
     * will need to override it to compensate for it's capturing moves.
     * 
     * @return List of positions threatened by this piece.
     */
    public List<Position> getThreatenedPositions() {
        return getValidMoves();
    }
    
    /**
     * Moves the piece to the given position.  No check is made to verify if
     * the position is valid.  Use validateMove(...) to verify validity before
     * invoking this method.
     * 
     * @param position The position to which the piece is moved.
     */
    public void move(Position position) {
        this.position = new Position(position);
        moved = true;
    }
    
    /**
     * Captures the piece
     */
    public void capture() {
        captured = true;
    }
    
    /**
     * @return the pieces current position
     */
    public Position getPosition() {
        return position;
    }
    
    /**
     * @return TRUE if the piece is captured.
     */
    public boolean isCaptured() {
        return captured;
    }

    /**
     * @return the type of the piece
     */
    public PieceType getType() {
        return type;
    }

    /**
     * @return the color of the piece
     */
    public boolean getColor() {
        return color;
    }

    /**
     * @return flag indicating if the piece has moved in the current game
     */
    public boolean isMoved() {
        return moved;
    }
}
