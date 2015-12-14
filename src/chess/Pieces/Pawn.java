package chess.pieces;

import chess.game.Move;
import chess.game.board.Position;
import chess.game.GameInfoWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the chess piece: Pawn
 *
 * @author CarrollFD
 */
public class Pawn extends Piece {
    /**
     * Constructs a new Pawn
     *
     * @param position The position of the piece
     * @param color The color of the piece
     * @param gameInfo The game info wrapper
     */
    public Pawn(Position position, boolean color, GameInfoWrapper gameInfo) {
        super(position, PieceType.pawn, color, gameInfo);
    }

    /**
     * Deep Copy Constructor
     *
     * @param toCopy The Pawn to copy
     * @param gameInfo The new game info wrapper
     */
    public Pawn(Pawn toCopy, GameInfoWrapper gameInfo) {
        super(toCopy, gameInfo);
    }

    @Override
    public boolean validateMove(Position position) {
        // change in position
        int deltaX = Math.abs(getPosition().getX() - position.getX());
        int deltaY = Math.abs(getPosition().getY() - position.getY());

        // check that the pawn isn't moving too far vertically
        if((isMoved() && deltaY > 1) || (!isMoved() && deltaY > 2)) {
            return false;
        }

        // make sure the pawn is moving the right direction
        if(getColor()) {
            // white pieces move up the board, down in number
            if(getPosition().getY() < position.getY()) {
                return false;
            }
        } else {
            // black pieces move down the board, up in number
            if(getPosition().getY() > position.getY()) {
                return false;
            }
        }

        // if we're moving to the left or right, attempt to validate an en passant
        // otherwise if we're moving too far to the left or right the move is invalid.
        if(deltaX == 1) {
            if(!validateEnPassant(position)) {
                return false;
            }
        } else if(deltaX > 1) {
            return false;
        }

        // perform global validation
        return super.validateMove(position);
    }

    /**
     * Validates a possible En Passant move by the pawn.
     *
     * @param position the position the pawn is moving to.
     *
     * @return TRUE if the move is a valid En Passant
     */
    private boolean validateEnPassant(Position position) {
        // change in position
        int deltaX = Math.abs(getPosition().getX() - position.getX());
        int deltaY = Math.abs(getPosition().getY() - position.getY());

        // make sure the move is exactly one space diagonally
        if(deltaX != 1 || deltaY != 1) {
            return false;
        }

        // get the previous move
        Move previousMove = gameInfo.getPreviousMove();

        // if its the first move of the game, and en passant is illegal
        if(previousMove == null) {
            return false;
        }

        // ensure that last move was performed by a pawn
        if(previousMove.getType() != PieceType.pawn) {
            return false;
        }

        // calculate how far the previous moves pawn traveled
        int previousYDelta = Math.abs(previousMove.getStartPosition().getY() - previousMove.getEndPosition().getY());

        // make sure the pawn moved 2 spaces
        if(previousYDelta != 2) {
            return false;
        }

        // make sure this pawn is next to the pawn from the previous move
        if(getPosition().getY() != previousMove.getEndPosition().getY() ||
                Math.abs(getPosition().getX() - previousMove.getEndPosition().getX()) != 1) {
            return false;
        }

        // make sure the move is attempting to capture the pawn from the previous move,
        // and not moving the opposite direction
        int directionOfPawn = getPosition().getX() - previousMove.getEndPosition().getX();
        int directionOfMove = getPosition().getX() - position.getX();
        if(directionOfMove != directionOfPawn) {
            return false;
        }

        // must be a valid en passant
        return true;
    }

    @Override
    public List<Position> getValidMoves() {
        List<Position> validMoves = new ArrayList<>();

        // pawns have four possible moves
        Position oneSpaceForward;
        Position twoSpacesForward;
        Position enPassantToTheLeft;
        Position enPassantToTheRight;

        // determine which direction the pawn should be moving
        int moveDirection;
        if(getColor()) {
            moveDirection = -1;
        } else {
            moveDirection = 1;
        }

        oneSpaceForward = new Position(getPosition().getX(), getPosition().getY() + moveDirection);
        twoSpacesForward = new Position(getPosition().getX(), getPosition().getY() + moveDirection * 2);
        enPassantToTheLeft = new Position(getPosition().getX() - 1, getPosition().getY() + moveDirection);
        enPassantToTheRight = new Position(getPosition().getX() + 1, getPosition().getY() + moveDirection);

        // validate each possible move and add it to the list if it is good
        if(validateMove(oneSpaceForward)) {
            validMoves.add(oneSpaceForward);
        }

        if(validateMove(twoSpacesForward)) {
            validMoves.add(twoSpacesForward);
        }

        if(validateMove(enPassantToTheLeft)) {
            validMoves.add(enPassantToTheLeft);
        }

        if(validateMove(enPassantToTheRight)) {
            validMoves.add(enPassantToTheRight);
        }

        // return the valid moves
        return validMoves;
    }
    
}
