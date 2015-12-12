package chess.pieces;

import chess.game.board.Position;
import chess.game.GameInfoWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the chess piece Rook
 *
 * @author CarrollFD
 */
public class Rook extends Piece {
    /**
     * Constructs a new Rook
     *
     * @param position The position of the piece
     * @param color The color of the piece
     * @param gameInfo The game info wrapper
     */
    public Rook(Position position, boolean color, GameInfoWrapper gameInfo) {
        super(position, PieceType.rook, color, gameInfo);
    }

    /**
     * Deep Copy Constructor
     *
     * @param toCopy The Rook to copy
     * @param gameInfo The new game info wrapper
     */
    public Rook(Rook toCopy, GameInfoWrapper gameInfo) {
        super(toCopy, gameInfo);
    }

    @Override
    public boolean validateMove(Position position) {
        // calculate the changes
        int deltaX = Math.abs(getPosition().getX() - position.getX());
        int deltaY = Math.abs(getPosition().getY() - position.getY());
        
        // one of the directions has to have moved 0
        if(!(deltaX == 0 ^ deltaY == 0)) {
            return false;
        }
        
        // perform global validation.
        return super.validateMove(position);
    }
    
    @Override
    public List<Position> getValidMoves() {
        List<Position> validMoves = new ArrayList<>();
        Position checkPosition;
        boolean validMove = true;
        
        // loop through the eight directions the queen can move
        for(int direction = 0; direction < 4; direction++) {
            // re-set check position
            checkPosition = new Position(getPosition());
            
            // keep moving in each direction until an invalid move id
            // detected
            while(validMove) {                
                // move one space in the apropriate direction
                switch(direction) {
                    case 0:
                        // north
                        checkPosition = new Position(checkPosition.getX(), checkPosition.getY() - 1);
                        break;
                    case 1:
                        // south
                        checkPosition = new Position(checkPosition.getX(), checkPosition.getY() + 1);
                        break;
                    case 2:
                        // west 
                        checkPosition = new Position(checkPosition.getX() - 1, checkPosition.getY());
                        break;
                    default:
                        // east 
                        checkPosition = new Position(checkPosition.getX() + 1, checkPosition.getY());
                        break;
                }
                
                // validate the move
                validMove = validateMove(checkPosition);
                
                // if it's good, add it to the list
                if(validMove) {
                    validMoves.add(checkPosition);
                }
            }
        }
        
        // return the list of valid moves
        return validMoves;
    }
}
