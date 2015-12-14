package chess.game.board;

import chess.game.GameInfoWrapper;
import chess.game.StartingPositions;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game board.
 *
 * @author CarrollFD
 */
public class Board {
    // The size of a standard chess board
    public static final int BOARD_SIZE_X = 8;
    public static final int BOARD_SIZE_Y = 8;

    // The 2D array representing the board's squares
    List<List<Square>> boardGrid = new ArrayList<>();

    // Game info wrapper
    GameInfoWrapper wrapper;

    // keeps track of who is in check
    ColorInCheck colorInCheck = ColorInCheck.none;

    // string indicating current move error
    String moveError = "";

    /**
     * Constructs and initializes the board.
     *
     * @param wrapper GameInfoWrapper with which to initialize the board.
     */
    public Board(GameInfoWrapper wrapper) {
        // set the wrapper
        this.wrapper = wrapper;

        // populate the board with starting positions
        initializeBoard();

        // figure out which squares are protected
        determineProtectedSquares();
    }

    /**
     * Deep Copy Constructor
     *
     * @param toCopy Board to copy
     * @param wrapper A new wrapper must be provided as the old one will
     *                still point at the original game parent.
     */
    public Board(Board toCopy, GameInfoWrapper wrapper) {
        // copy primitive members
        this.colorInCheck = toCopy.colorInCheck;
        this.moveError = toCopy.moveError;

        // populate an empty board
        populateSquares();

        // extract board grid to copy
        List<List<Square>> gridToCopy = toCopy.boardGrid;

        // loop through rows
        for(int y = 0; y < Board.BOARD_SIZE_Y; y++) {
            //loop through columns
            for(int x = 0; x < Board.BOARD_SIZE_X; x++) {
                // create a deep copy of the square
                Square newSquare = new Square(gridToCopy.get(y).get(x), wrapper);

                // assign the new square to the appropriate x, y
                boardGrid.get(y).set(x, newSquare);
            }
        }

        // figure out which squares are protected
        determineProtectedSquares();
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
    }

    /**
     * Sets-up the board with starting positions.  Re-sets the board if pieces
     * are already placed.
     */
    public final void initializeBoard() {
        // clear the board
        populateSquares();

        Position positionToSet;

        // Initialize black non-pawn pieces
        positionToSet = new Position(StartingPositions.ROOK_1, StartingPositions.BLACK_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Rook(positionToSet, false, wrapper));
        positionToSet = new Position(StartingPositions.ROOK_2, StartingPositions.BLACK_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Rook(positionToSet, false, wrapper));

        positionToSet = new Position(StartingPositions.BISHOP_1, StartingPositions.BLACK_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Bishop(positionToSet, false, wrapper));
        positionToSet = new Position(StartingPositions.BISHOP_2, StartingPositions.BLACK_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Bishop(positionToSet, false, wrapper));

        positionToSet = new Position(StartingPositions.KNIGHT_1, StartingPositions.BLACK_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Knight(positionToSet, false, wrapper));
        positionToSet = new Position(StartingPositions.KNIGHT_2, StartingPositions.BLACK_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Knight(positionToSet, false, wrapper));

        positionToSet = new Position(StartingPositions.BLACK_KING_X, StartingPositions.BLACK_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new King(positionToSet, false, wrapper));
        positionToSet = new Position(StartingPositions.BLACK_QUEEN_X, StartingPositions.BLACK_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Queen(positionToSet, false, wrapper));

        // initialize black pawns
        positionToSet = new Position(0, StartingPositions.BLACK_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, false, wrapper));
        positionToSet = new Position(1, StartingPositions.BLACK_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, false, wrapper));
        positionToSet = new Position(2, StartingPositions.BLACK_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, false, wrapper));
        positionToSet = new Position(3, StartingPositions.BLACK_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, false, wrapper));
        positionToSet = new Position(4, StartingPositions.BLACK_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, false, wrapper));
        positionToSet = new Position(5, StartingPositions.BLACK_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, false, wrapper));
        positionToSet = new Position(6, StartingPositions.BLACK_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, false, wrapper));
        positionToSet = new Position(7, StartingPositions.BLACK_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, false, wrapper));

        // Initialize white non-pawn pieces
        positionToSet = new Position(StartingPositions.ROOK_1, StartingPositions.WHITE_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Rook(positionToSet, true, wrapper));
        positionToSet = new Position(StartingPositions.ROOK_2, StartingPositions.WHITE_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Rook(positionToSet, true, wrapper));

        positionToSet = new Position(StartingPositions.BISHOP_1, StartingPositions.WHITE_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Bishop(positionToSet, true, wrapper));
        positionToSet = new Position(StartingPositions.BISHOP_2, StartingPositions.WHITE_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Bishop(positionToSet, true, wrapper));

        positionToSet = new Position(StartingPositions.KNIGHT_1, StartingPositions.WHITE_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Knight(positionToSet, true, wrapper));
        positionToSet = new Position(StartingPositions.KNIGHT_2, StartingPositions.WHITE_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Knight(positionToSet, true, wrapper));

        positionToSet = new Position(StartingPositions.WHITE_KING_X, StartingPositions.WHITE_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new King(positionToSet, true, wrapper));
        positionToSet = new Position(StartingPositions.WHITE_QUEEN_X, StartingPositions.WHITE_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Queen(positionToSet, true, wrapper));

        // initialize white pawns
        positionToSet = new Position(0, StartingPositions.WHITE_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, true, wrapper));
        positionToSet = new Position(1, StartingPositions.WHITE_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, true, wrapper));
        positionToSet = new Position(2, StartingPositions.WHITE_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, true, wrapper));
        positionToSet = new Position(3, StartingPositions.WHITE_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, true, wrapper));
        positionToSet = new Position(4, StartingPositions.WHITE_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, true, wrapper));
        positionToSet = new Position(5, StartingPositions.WHITE_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, true, wrapper));
        positionToSet = new Position(6, StartingPositions.WHITE_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, true, wrapper));
        positionToSet = new Position(7, StartingPositions.WHITE_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, true, wrapper));
    }

    /**
     * Populates the board with empty squares
     */
    private void populateSquares() {
        // loop through rows
        for(int y = 0; y < Board.BOARD_SIZE_Y; y++) {
            // create a new row
            boardGrid.set(y, new ArrayList<>());

            // loop through columns
            for(int x = 0; x < Board.BOARD_SIZE_X; x++) {
                // create a new square at the current x and y
                boardGrid.get(y).set(x, new Square());
            }
        }
    }

    /**
     * Populates the threatened squares lists.
     */
    private void determineProtectedSquares() {
        // clear the protected lists
        List<Position> protectedByBlack = new ArrayList<>();
        List<Position> protectedByWhite = new ArrayList<>();

        // loop through the board's rows and columns
        for(int y = 0; y < Board.BOARD_SIZE_Y; y++) {
            for(int x = 0; x < Board.BOARD_SIZE_X; x++) {
                // extract the piece on this x, y
                Piece piece = boardGrid.get(y).get(x).getPieceOnSquare();

                // make sure there is a piece here, if not continue the loop
                if(piece == null) {
                    continue;
                }

                // extract the positions protected by this piece
                List<Position> protectedPositions = piece.getThreatenedPositions();

                if(piece.getColor()) {
                    // if its white, update the white protected list
                    for(Position position : protectedPositions) {
                        if(!protectedByWhite.contains(position)) {
                            protectedByWhite.add(position);
                        }
                    }
                } else {
                    // if its black, update the black protected list
                    for(Position position : protectedPositions) {
                        if(!protectedByBlack.contains(position)) {
                            protectedByBlack.add(position);
                        }
                    }
                }
            }
        }

        // loop through the rows and columns of the board and update each as necessary
        for(int y = 0; y < Board.BOARD_SIZE_Y; y++) {
            for(int x = 0; x < Board.BOARD_SIZE_X; x++) {
                Position position = new Position(x, y);

                // check if either white or black protects the square
                if(protectedByBlack.contains(position) || protectedByWhite.contains(position)) {
                    // if black protects the square, update its flag
                    if(protectedByBlack.contains(position)) {
                        boardGrid.get(y).get(x).setProtectedByBlack(true);
                    }

                    // if white protects the square, update its flag
                    if(protectedByWhite.contains(position)) {
                        boardGrid.get(y).get(x).setProtectedByWhite(true);
                    }
                } else {
                    // if neither protect the square, update it correctly
                    boardGrid.get(y).get(x).setProtectedByBlack(false);
                    boardGrid.get(y).get(x).setProtectedByWhite(false);
                }

            }
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
        Square square = boardGrid.get(position.getY()).get(position.getX());

        return (square.isProtectedByBlack() && color) || (square.isProtectedByWhite() && color)
    }

    /**
     * Determines if there is a piece intervening along the move from
     * the first position to the second position - end points are
     * excluded form this check.  Returns false if the move
     * is not diagonal, horizontal, or vertical.  This is because a knights
     * move is invalid for this check, but there are never any intervening
     * pieces for a knight.
     *
     * @param position1 The starting position.
     * @param position2 The ending Position
     *
     * @return true if there is an intervening piece
     */
    public boolean isInterveningPiece(Position position1, Position position2) {
    }

    /**
     * Provides the piece at the given position, or null if there is none.
     *
     * @param position The position to check
     *
     * @return The piece at the given position, or null if there is one.
     */
    public Piece getPieceAt(Position position) {
        return boardGrid.get(position.getY()).get(position.getX()).getPieceOnSquare();
    }

    /**
     * Determines all threatened positions for the given color.
     *
     * @param color The color to check
     *
     * @return List of positions threatened by the given color.
     */
    public List<Position> getThreatenedPositions(boolean color) {
        List<Position> threatened = new ArrayList<>();

        // loop through the board squares
        for(int y = 0; y < Board.BOARD_SIZE_Y; y++) {
            for(int x  = 0; x < Board.BOARD_SIZE_X; x++) {
                if(color) {
                    // check if white threatens the square
                    if(boardGrid.get(y).get(x).isProtectedByWhite()) {
                        threatened.add(new Position(x, y));
                    }
                } else {
                    // check if black threatens the square
                    if(boardGrid.get(y).get(x).isProtectedByBlack()) {
                        threatened.add(new Position(x, y));
                    }
                }
            }
        }

        // return the list
        return threatened;
    }

    /**
     * Updates the colorInCheck flag to indicate if a color is in check.
     *
     * @throws IllegalStateException If two kings are in check.
     */
    private void verifyCheck() throws IllegalStateException {
    }

    /**
     * Moves a piece without any validation
     *
     * @param startPosition The starting position
     * @param endPosition The ending position
     */
    public void forceMove(Position startPosition, Position endPosition) {
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
    public ColorInCheck getColorInCheck() {
        return colorInCheck;
    }
}
