package chess.pieces;

import chess.game.GameInfoWrapper;
import chess.game.board.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the chess piece: Queen
 * @author CarrollFD
 */
public class Queen extends Piece {

    /**
     * Constructs a new Queen
     *
     * @param position The position of the piece
     * @param color The color of the piece
     * @param gameInfo The game info wrapper
     */
    public Queen (Position position, boolean color, GameInfoWrapper gameInfo) {
        super(position, PieceType.queen, color, gameInfo);
    }

    /**
     * Deep Copy Constructor
     *
     * @param toCopy The Queen to copy
     * @param gameInfo The new game info wrapper
     */
    public Queen(Queen toCopy, GameInfoWrapper gameInfo) {
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
        
        // check if it is a verticle or horizontal move
        // then check for diagonal
        return ((deltaX == 0 && deltaY != 0) || (deltaY == 0 && deltaX != 0) ||
                (deltaX == deltaY)) && super.validateMove(position);
    }

    @Override
    public List<Position> getThreatenedPositions() {
        List<Position> threatenedPositions = new ArrayList<>();
        Position checkPosition;
        boolean validMove;
        
        // loop through the eight directions the queen can move
        for(int direction = 0; direction < 8; direction++) {
            // re-set check position
            checkPosition = new Position(getPosition());

            // reseet valid move
            validMove = true;

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
                    case 3:
                        // east 
                        checkPosition = new Position(checkPosition.getX() + 1, checkPosition.getY());
                        break;
                    case 4:
                        // north west
                        checkPosition = new Position(checkPosition.getX() - 1, checkPosition.getY() - 1);
                        break;
                    case 5:
                        // north east 
                        checkPosition = new Position(checkPosition.getX() + 1, checkPosition.getY() - 1);
                        break;
                    case 6:
                        // south west
                        checkPosition = new Position(checkPosition.getX() - 1, checkPosition.getY() + 1);
                        break;
                    default:
                        // south east
                        checkPosition = new Position(checkPosition.getX() + 1, checkPosition.getY() + 1);
                }
                
                // validate the move
                validMove = this.validateThreatened(checkPosition);
                
                // if it's good, add it to the list
                if(validMove) {
                    threatenedPositions.add(checkPosition);
                }
            }
        }
        
        // return the list of valid moves
        return threatenedPositions;
    }
}
