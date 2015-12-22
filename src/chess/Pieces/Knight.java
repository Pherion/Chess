package chess.pieces;

import chess.game.GameInfoWrapper;
import chess.game.board.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the chess piece: Knight
 * @author CarrollFD
 */
public class  Knight extends Piece {
    /**
     * Constructs a new Knight
     *
     * @param position The position of the piece
     * @param color The color of the piece
     * @param gameInfo The game info wrapper
     */
    public Knight(Position position, boolean color, GameInfoWrapper gameInfo) {
        super(position, PieceType.knight, color, gameInfo);
    }

    /**
     * Deep Copy Constructor
     *
     * @param toCopy The Knight to copy
     * @param gameInfo The new game info wrapper
     */
    public Knight(Knight toCopy, GameInfoWrapper gameInfo) {
        super(toCopy, gameInfo);
    }
    
    @Override
    public boolean validateMove(Position position) {
        // make sure the move is on the board
        if(!GameInfoWrapper.isOnBoard(position)) {
            return false;
        }
        
        // calculate the changes
        int deltaX = Math.abs(getPosition().getX() - position.getX());
        int deltaY = Math.abs(getPosition().getY() - position.getY());
        
        // make sure one delta is 1, and the other is 2
        if((deltaX == 1 ^ deltaY == 1) && (deltaX == 2 ^ deltaY == 2)) {
            return false;
        }
        
        // perform global validation
        return super.validateMove(position);
    }

    @Override
    public List<Position> getThreatenedPositions() {
        List<Position> threatenedPositions = new ArrayList<>();
        List<Position> possibleMoves = new ArrayList<>();
        
        // populate the possible moves with all knight moves
        possibleMoves.add(new Position(getPosition().getX() + 1, getPosition().getY() + 2));
        possibleMoves.add(new Position(getPosition().getX() + 1, getPosition().getY() - 2));
        possibleMoves.add(new Position(getPosition().getX() - 1, getPosition().getY() + 2));
        possibleMoves.add(new Position(getPosition().getX() - 1, getPosition().getY() - 2));
        possibleMoves.add(new Position(getPosition().getX() + 2, getPosition().getY() + 1));
        possibleMoves.add(new Position(getPosition().getX() - 2, getPosition().getY() + 1));
        possibleMoves.add(new Position(getPosition().getX() + 2, getPosition().getY() - 1));
        possibleMoves.add(new Position(getPosition().getX() - 2, getPosition().getY() - 1));
        
        // check each move
        for(Position position : possibleMoves) {
            if(validateThreatened(position)) {
                threatenedPositions.add(position);
            }
        }
        
        // return the valid moves
        return threatenedPositions;
    }
    
}
