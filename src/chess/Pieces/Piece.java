package chess.pieces;

import chess.game.GameInfoWrapper;
import chess.game.board.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a generic chess piece.
 *
 * @author CarrollFD
 */
public abstract class Piece {
    public static final boolean WHITE = true;
    public static final boolean BLACK = false;
    
    // members accessible by child classes
    protected GameInfoWrapper gameInfo;
    
    // members immutable by child classes
    private boolean color;
    private Position position;
    private boolean captured = false;
    private PieceType type;
    private boolean moved = false;

    /**
     * Constructs a new chess piece.
     *
     * @param position The starting position of the piece
     * @param type The type of the piece.
     * @param color The color of the piece.
     * @param gameInfo The game info wrapper
     */
    protected Piece(Position position, PieceType type, boolean color, GameInfoWrapper gameInfo) {
        this.color = color;
        this.position = new Position(position);
        this.type = type;
        this.gameInfo = gameInfo;
    }

    /**
     * Deep copy of the piece
     *
     * @param toCopy The piece to copy
     * @param gameInfo The new game info wrapper to use
     */
    public Piece(Piece toCopy, GameInfoWrapper gameInfo) {
        this.gameInfo = gameInfo;
        color = toCopy.color;
        position = new Position(toCopy.position);
        captured = toCopy.captured;
        type = toCopy.type;
        moved = toCopy.moved;
    }

    /**
     * Verifies if the provided position is a legal move for the given piece.
     * 
     * @param position Position to check
     * @return TRUE if the position is a valid move
     */
    public boolean validateMove(Position position) {
        // ensure the position is actually different
        // check there are no intervening peices
        // check that any pieces on the target position are capturable
        // check that the move is on the board
        return validateThreatened(position) && 
                gameInfo.isCapturable(color, position);
    }
    
    /**
     * Verifies if the provided position is threatened by the piece.
     * @param position
     * @return 
     */
    public boolean validateThreatened(Position position) {
        // calculate the changes
        int deltaX = Math.abs(getPosition().getX() - position.getX());
        int deltaY = Math.abs(getPosition().getY() - position.getY());
        
        // ensure the position is actually different
        // check there are no intervening peices
        // check that the move is on the board
        return (deltaX != 0 || deltaY != 0) &&
               GameInfoWrapper.isOnBoard(position) &&
               !gameInfo.isInterveningPeice(this.position, position);
    }
    
    /**
     * Provides a list of valid moves for the given piece.
     *
     * @return The list of valid moves for the piece
     */
    public List<Position> getValidMoves() {
        // obtain the list of positions threatened by this piece
        List<Position> threatenedPositions = getThreatenedPositions();
        List<Position> validMoves = new ArrayList<>();
        
        // loop through the threatened positions, and verify they are valid
        // moves by invoking isCapturable().  This method returns true if there
        // is a piece of the oposing color, or no piece on the given
        // position.
        for(Position positionToCheck : threatenedPositions) {
            boolean capturable = gameInfo.isCapturable(getColor(), positionToCheck);
            
            // if it's a valid move, add it to the list
            if(capturable) {
                validMoves.add(positionToCheck);
            }
        }
        
        // return list of valid moves
        return validMoves;
    }
    
    /**
     * Provides a list of positions that are threatened by the piece.  These positions
     * may not be valid moves.
     * 
     * @return List of positions threatened by this piece.
     */
    public abstract List<Position> getThreatenedPositions();
    
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
