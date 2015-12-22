package chess.pieces;

import chess.game.board.Position;

/**
 * A wrapper for Piece that does not present any mutable methods.
 * 
 * @author CarrollFD
 */
public class ImmutablePiece {
    Piece piece;
   
    /**
     * Construct the immutable wrapper
     * 
     * @param piece 
     */
    public ImmutablePiece(Piece piece) {
        this.piece = piece;
    }
    
    /**
     * @return a copy of the pieces position object
     */
    public Position getPosition() {
        return new Position(piece.getPosition());
    }
    
    /**
     * @return the pieces color
     */
    public boolean getColor() {
        return piece.getColor();
    }
    
    /**
     * @return the piece type
     */
    public PieceType getPieceType() {
        return piece.getType();
    }
}

