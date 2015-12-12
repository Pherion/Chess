package chess.game.board;

import chess.pieces.*;
import chess.game.Game;
import chess.game.GameInfoWrapper;
import chess.game.StartingPositions;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game board.
 *
 * @author CarrollFD
 */
public class Board {
    public static final int BOARD_SIZE_X = 8;
    public static final int BOARD_SIZE_Y = 8;

    // the pieces on the board
    private ArrayList<Piece> whitePieces;
    private ArrayList<Piece> blackPieces;

    // indicates the color in check, set to null if there is no
    private Boolean colorInCheck = null;

    // game info wrapper
    private GameInfoWrapper gameInfo;

    // move error message
    private String moveError;

    /**
     * Constructs and initializes the board.
     *
     * @param wrapper GameInfoWrapper with which to initialize the board.
     */
    public Board(GameInfoWrapper wrapper) {
        gameInfo = wrapper;
        initializeBoard(wrapper);
    }

    /**
     * Deep Copy Constructor
     *
     * @param toCopy Board to copy
     * @param wrapper A new wrapper must be provided as the old one will
     *                still point at the original game parent.
     */
    public Board(Board toCopy, GameInfoWrapper wrapper) {
        // assing wrapper and check flag.
        gameInfo = wrapper;
        colorInCheck = toCopy.colorInCheck;

        // copy white pieces
        whitePieces = new ArrayList<>();
        for(Piece piece : toCopy.whitePieces) {
            switch(piece.getType()) {
                case bishop:
                    whitePieces.add(new Bishop((Bishop)piece, wrapper));
                    break;
                case king:
                    whitePieces.add(new King((King)piece, wrapper));
                    break;
                case knight:
                    whitePieces.add(new Knight((Knight)piece, wrapper));
                    break;
                case pawn:
                    whitePieces.add(new Pawn((Pawn)piece, wrapper));
                    break;
                case queen:
                    whitePieces.add(new Queen((Queen)piece, wrapper));
                    break;
                case rook:
                    whitePieces.add(new Rook((Rook)piece, wrapper));
                    break;
            }
        }

        // copy black pieces
        blackPieces = new ArrayList<>();
        for(Piece piece : toCopy.blackPieces) {
            switch(piece.getType()) {
                case bishop:
                    blackPieces.add(new Bishop((Bishop)piece, wrapper));
                    break;
                case king:
                    blackPieces.add(new King((King)piece, wrapper));
                    break;
                case knight:
                    blackPieces.add(new Knight((Knight)piece, wrapper));
                    break;
                case pawn:
                    blackPieces.add(new Pawn((Pawn)piece, wrapper));
                    break;
                case queen:
                    blackPieces.add(new Queen((Queen)piece, wrapper));
                    break;
                case rook:
                    blackPieces.add(new Rook((Rook)piece, wrapper));
                    break;
            }
        }
    }

    /**
     * Validates a move, and performs it if its OK.
     *
     * @param piecePosition Starting position
     * @param targetPosition Ending position
     *
     * @return TRUE if the move was performed, FALSE if there were any problems
     */
    public boolean requestMove(Position piecePosition, Position targetPosition) {
        Piece pieceToMove = getPieceAt(piecePosition);
        moveError = "";

        // make sure there is a piece at the requested location
        if(pieceToMove == null) {
            moveError = "No piece to move.";
            return false;
        }

        // validate the move
        if(!pieceToMove.validateMove(targetPosition)) {
            moveError = "Invalid move for a " + pieceToMove.getType();
            return false;
        }

        // generate a test game board with the move executed.
        Game testGame = gameInfo.forceMove(piecePosition, targetPosition);

        // verify check states
        try {
            testGame.getBoard().verifyCheck();

            // make sure an existing check is aleviated
            if(colorInCheck != null && colorInCheck.equals(testGame.getBoard().colorInCheck)) {
                moveError = "Move does not aleviate check.";
                return false;
            }

            // if not in check, make sure this move doesnt put the player into
            // check
            if(colorInCheck == null && pieceToMove.getColor() == testGame.getBoard().colorInCheck) {
                moveError = "Move places moving player into check.";
                return false;
            }
        } catch(IllegalStateException e) {
            // this exception is thrown when two kings are in check.  It will
            // prevent a player in check from checking their oponent while not
            // alleviating their own check.
            moveError = "Move generates invalid board state - multiple kings "
                    + "in check.";
            return false;
        }

        // intervening piece does not check the endpoint, since it doesn't have
        // access to the color of the piece being moved to check for a capture
        // so we'll check that here.
        Piece pieceToCapture = getPieceAt(targetPosition);

        if(pieceToCapture != null && pieceToCapture.getColor() != pieceToMove.getColor()) {
            // execute the capture
            pieceToCapture.capture();
        }

        // move the piece
        pieceToMove.move(targetPosition);

        // check for a threatened king
        verifyCheck();

        // let the caller know the move was executed
        return true;
    }

    /**
     * Sets-up the board with starting positions.  Re-sets the board if pieces
     * are already placed.
     *
     * @param wrapper the GameInfoWrapper to initialize the board with
     */
    public final void initializeBoard(GameInfoWrapper wrapper) {
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();

        Position piecePosition;
        // create white rooks
        piecePosition = new Position(StartingPositions.ROOK_1, StartingPositions.WHITE_NON_PAWN_Y);
        whitePieces.add(new Rook(piecePosition, true, wrapper));
        piecePosition = new Position(StartingPositions.ROOK_2, StartingPositions.WHITE_NON_PAWN_Y);
        whitePieces.add(new Rook(piecePosition, true, wrapper));

        // create white bishops
        piecePosition = new Position(StartingPositions.BISHOP_1, StartingPositions.WHITE_NON_PAWN_Y);
        whitePieces.add(new Bishop(piecePosition, true, wrapper));
        piecePosition = new Position(StartingPositions.BISHOP_2, StartingPositions.WHITE_NON_PAWN_Y);
        whitePieces.add(new Bishop(piecePosition, true, wrapper));

        // create white knights
        piecePosition = new Position(StartingPositions.KNIGHT_1, StartingPositions.WHITE_NON_PAWN_Y);
        whitePieces.add(new Knight(piecePosition, true, wrapper));
        piecePosition = new Position(StartingPositions.KNIGHT_2, StartingPositions.WHITE_NON_PAWN_Y);
        whitePieces.add(new Knight(piecePosition, true, wrapper));

        // create white royalty
        piecePosition = new Position(StartingPositions.WHITE_KING_X, StartingPositions.WHITE_NON_PAWN_Y);
        whitePieces.add(new King(piecePosition, true, wrapper));
        piecePosition = new Position(StartingPositions.WHITE_QUEEN_X, StartingPositions.WHITE_NON_PAWN_Y);
        whitePieces.add(new Queen(piecePosition, true, wrapper));

        // create white pawns
        for(int i = 0; i < 8; i++) {
            piecePosition = new Position(i, StartingPositions.WHITE_PAWN_Y);
            whitePieces.add(new Pawn(piecePosition, true, wrapper));
        }

        // create black rooks
        piecePosition = new Position(StartingPositions.ROOK_1, StartingPositions.BLACK_NON_PAWN_Y);
        blackPieces.add(new Rook(piecePosition, true, wrapper));
        piecePosition = new Position(StartingPositions.ROOK_2, StartingPositions.BLACK_NON_PAWN_Y);
        blackPieces.add(new Rook(piecePosition, true, wrapper));

        // create black bishops
        piecePosition = new Position(StartingPositions.BISHOP_1, StartingPositions.BLACK_NON_PAWN_Y);
        blackPieces.add(new Bishop(piecePosition, true, wrapper));
        piecePosition = new Position(StartingPositions.BISHOP_2, StartingPositions.BLACK_NON_PAWN_Y);
        blackPieces.add(new Bishop(piecePosition, true, wrapper));

        // create black knights
        piecePosition = new Position(StartingPositions.KNIGHT_1, StartingPositions.BLACK_NON_PAWN_Y);
        blackPieces.add(new Knight(piecePosition, true, wrapper));
        piecePosition = new Position(StartingPositions.KNIGHT_2, StartingPositions.BLACK_NON_PAWN_Y);
        blackPieces.add(new Knight(piecePosition, true, wrapper));

        // create black royalty
        piecePosition = new Position(StartingPositions.BLACK_KING_X, StartingPositions.BLACK_NON_PAWN_Y);
        blackPieces.add(new King(piecePosition, true, wrapper));
        piecePosition = new Position(StartingPositions.BLACK_QUEEN_X, StartingPositions.BLACK_NON_PAWN_Y);
        blackPieces.add(new Queen(piecePosition, true, wrapper));

        // create black pawns
        for(int i = 0; i < 8; i++) {
            piecePosition = new Position(i, StartingPositions.BLACK_PAWN_Y);
            blackPieces.add(new Pawn(piecePosition, true, wrapper));
        }
    }

    /**
     * Indicates if the provided position is threatened by the given color
     *
     * @param position The position to check
     * @param color the color to check for
     *
     * @return TRUE if the given color threatens the given position
     */
    public boolean threatenedBy(Position position, boolean color) {
        List<Position> threatenedPositions = getThreatenedPositions(color);

        return threatenedPositions.contains(position);
    }

    /**
     * Determines if there is a piece intervening along the move from
     * the first position to the second position - end points are
     * excluded form this check.  Returns false if the move
     * is not diagonal, horizontal, or vertical.  This is because a knights
     * move is invalid for this check, but there are never any intervening
     * pieces for a knight.
     *
     * @param position1
     * @param position2
     * @return
     */
    public boolean isInterveningPiece(Position position1, Position position2) {
        // calculate the distance moved in each direction
        int deltaY = Math.abs(position1.getY() - position2.getY());
        int deltaX = Math.abs(position1.getX() - position2.getX());

        if(deltaY != deltaX && !(deltaY == 0 ^ deltaX == 0)) {
            return false;
        }

        // determine the direction we're moving
        int xMovement;
        int yMovement;

        if(position1.getX() - position2.getX() > 0) {
            xMovement = 1;
        } else {
            xMovement = -1;
        }

        if(position1.getY() - position2.getY() > 0) {
            yMovement = 1;
        } else {
            yMovement = -1;
        }

        // calculate the first position to check
        Position checkPosition = new Position(position1.getX() + xMovement, position1.getY() + yMovement);

        // loop through positions
        do {
            // check if the position is contained in the list of white pieces
            for(Piece piece : whitePieces) {
                if(!piece.isCaptured() && piece.getPosition().equals(checkPosition)) {
                    return true;
                }
            }

            // and for black
            for(Piece piece : blackPieces) {
                if(!piece.isCaptured() && piece.getPosition().equals(checkPosition)) {
                    return true;
                }
            }

            // move to the next check positions
            checkPosition = new Position(checkPosition.getX() + xMovement, checkPosition.getY() + yMovement);
        } while(!checkPosition.equals(position2));

        return false;
    }

    /**
     * Provides the piece at the given position, or null if there is none.
     *
     * @param position The position to check
     *
     * @return The piece at the given position, or null if there is one.
     */
    public Piece getPieceAt(Position position) {
        // check white pieces
        for(Piece piece : whitePieces) {
            if(!piece.isCaptured() && piece.getPosition().equals(position)) {
                return piece;
            }
        }

        // check black pieces
        for(Piece piece : blackPieces) {
            if(!piece.isCaptured() && piece.getPosition().equals(position)) {
                return piece;
            }
        }
        return null;
    }

    /**
     * Determines all threatened positions for the given color.
     *
     * @param color The color to check
     *
     * @return List of positions threatened by the given color.
     */
    public List<Position> getThreatenedPositions(boolean color) {
        List<Piece> piecesToCheck;
        List<Position> threatenedPositions = new ArrayList<>();

        // the the list of peices for the given color
        if(color) {
            piecesToCheck = whitePieces;
        } else {
            piecesToCheck = blackPieces;
        }

        // get the positions that each piece threatens
        for(Piece piece : piecesToCheck) {
            threatenedPositions.addAll(piece.getThreatenedPositions());
        }

        // return the list
        return threatenedPositions;
    }

    /**
     * Updates the colorInCheck flag to indicate if a color is in check.
     *
     * @throws IllegalStateException If two kings are in check.
     */
    private void verifyCheck() throws IllegalStateException {
        Piece whiteKing = null;
        Piece blackKing = null;

        // find the kings
        for(Piece piece : whitePieces) {
            if(piece instanceof King) {
                whiteKing = piece;
                break;
            }
        }

        for(Piece piece : blackPieces) {
            if(piece instanceof King) {
                blackKing = piece;
                break;
            }
        }

        // make sure nothing incredibly weird is going on
        if(blackKing == null || whiteKing == null) {
            throw new IllegalStateException("One of the kings doesn't exist!");
        }

        // get the squares threatened by each group
        List<Position> threatenedByWhite = this.getThreatenedPositions(true);
        List<Position> threatenedByBlack = this.getThreatenedPositions(false);

        // reset check flag
        colorInCheck = null;

        // see if the black king is in check.
        if(threatenedByWhite.contains(blackKing.getPosition())) {
            colorInCheck = false;
        }

        // see if the white king is in check
        if(threatenedByBlack.contains(whiteKing.getPosition())) {
            // the supposedly impossible case where both kings are in check.
            if(colorInCheck != null) {
                throw new IllegalStateException("Both kings in check.");
            }

            colorInCheck = true;
        }
    }

    /**
     * Moves a piece without any validation
     *
     * @param startPosition The starting position
     * @param endPosition The ending position
     */
    public void forceMove(Position startPosition, Position endPosition) {
        // get the pieces at the start ane end positions
        Piece piece = getPieceAt(startPosition);
        Piece capturedPiece = getPieceAt(endPosition);

        // make sure there's a piece to move
        if(piece == null) {
            throw new IllegalArgumentException("Attempting to force move on "
                    + "a position with no piece.");
        }

        // capture the piece at the end position
        if(capturedPiece != null) {
            capturedPiece.capture();
        }

        // move the piece
        piece.move(endPosition);
    }

    /**
     * @return Returns the move error message.
     */
    public String getMoveError() {
        return moveError;
    }

    /**
     * @return Provides the colorInCheck flag.
     */
    public Boolean isColorInCheck() {
        return colorInCheck;
    }
}
