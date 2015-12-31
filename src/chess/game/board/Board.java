package chess.game.board;

import chess.game.Game;
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
    private List<List<Square>> boardGrid = new ArrayList<>();

    // Game info wrapper
    private GameInfoWrapper gameInfo;

    // keeps track of who is in check
    private ColorInCheck colorInCheck = ColorInCheck.none;

    // string indicating current move error
    private String moveError = "";

    private boolean protectedSquaresInitialized = false;

    /**
     * Constructs and initializes the board.
     *
     * @param wrapper GameInfoWrapper with which to initialize the board.
     */
    public Board(GameInfoWrapper wrapper) {
        // set the wrapper
        this.gameInfo = wrapper;

        // populate the board with starting positions
        initializeBoard();

        // we would like to invoke determineProtectedSquares() here, but
        // if this is part of the initialization of the game referenced in
        // the gameInfoWrapper, we will create a loop that can not
        // execute without error.  The flag protectedSquaresInitialized
        // will determine if this initialization needs to occur.
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

        // we would like to invoke determineProtectedSquares() here, but
        // if this is part of the initilization of the game referenced in
        // the gameInfoWrapper, we will create a loop that can not
        // execute without error.  The flag protectdSquaresInitialized
        // will determine if this initialization needs to occur.
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
        // make sure the protected squares have been initialized
        initialize();

        Piece pieceToMove = getPieceAt(piecePosition);
        moveError = "";

        // make sure there is a piece to move
        if(pieceToMove == null) {
            moveError = "No piece at given position.";
            return false;
        }

        // make sure the move is valid
        if(!pieceToMove.validateMove(targetPosition)) {
            moveError = "Invalid move.";
            return false;
        }

        // create a test game to verify that it doesn't cause the moving color
        // to be in check
        Game testGame;
        try {
            testGame = gameInfo.forceMove(piecePosition, targetPosition);
        } catch(IllegalArgumentException | IllegalStateException e) {
            // if an exception is thrown when attempting to force the move
            // we know that an illegal game state has been created.
            moveError = "Move results in invalid board state.";
            return false;
        }

        // make sure the moving piece isn't placing its king in check.
        if(testGame.getBoard().getColorInCheck().toBoolean() != null &&
                pieceToMove.getColor() == testGame.getBoard().getColorInCheck().toBoolean()) {
            moveError = "Move places player in check";
            return false;
        }

        // we can now safely move the piece
        // get the piece to capture, and capture it if there is one
        Piece pieceToCapture = getPieceAt(targetPosition);
        if(pieceToCapture != null) {
            pieceToCapture.capture();
        }

        // remove the piece from it's starting position
        boardGrid.get(piecePosition.getY()).get(piecePosition.getX()).setPieceOnSquare(null);

        // place the piece on it's ending position
        boardGrid.get(targetPosition.getY()).get(targetPosition.getX()).setPieceOnSquare(pieceToMove);

        // update the piece itself
        pieceToMove.move(targetPosition);

        // update the game state
        determineProtectedSquares();
        verifyCheck();

        return true;
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
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Rook(positionToSet, Piece.BLACK, gameInfo));
        positionToSet = new Position(StartingPositions.ROOK_2, StartingPositions.BLACK_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Rook(positionToSet, Piece.BLACK, gameInfo));

        positionToSet = new Position(StartingPositions.BISHOP_1, StartingPositions.BLACK_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Bishop(positionToSet, Piece.BLACK, gameInfo));
        positionToSet = new Position(StartingPositions.BISHOP_2, StartingPositions.BLACK_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Bishop(positionToSet, Piece.BLACK, gameInfo));

        positionToSet = new Position(StartingPositions.KNIGHT_1, StartingPositions.BLACK_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Knight(positionToSet, Piece.BLACK, gameInfo));
        positionToSet = new Position(StartingPositions.KNIGHT_2, StartingPositions.BLACK_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Knight(positionToSet, Piece.BLACK, gameInfo));

        positionToSet = new Position(StartingPositions.BLACK_KING_X, StartingPositions.BLACK_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new King(positionToSet, Piece.BLACK, gameInfo));
        positionToSet = new Position(StartingPositions.BLACK_QUEEN_X, StartingPositions.BLACK_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Queen(positionToSet, Piece.BLACK, gameInfo));

        // initialize black pawns
        positionToSet = new Position(0, StartingPositions.BLACK_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, Piece.BLACK, gameInfo));
        positionToSet = new Position(1, StartingPositions.BLACK_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, Piece.BLACK, gameInfo));
        positionToSet = new Position(2, StartingPositions.BLACK_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, Piece.BLACK, gameInfo));
        positionToSet = new Position(3, StartingPositions.BLACK_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, Piece.BLACK, gameInfo));
        positionToSet = new Position(4, StartingPositions.BLACK_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, Piece.BLACK, gameInfo));
        positionToSet = new Position(5, StartingPositions.BLACK_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, Piece.BLACK, gameInfo));
        positionToSet = new Position(6, StartingPositions.BLACK_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, Piece.BLACK, gameInfo));
        positionToSet = new Position(7, StartingPositions.BLACK_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, Piece.BLACK, gameInfo));

        // Initialize white non-pawn pieces
        positionToSet = new Position(StartingPositions.ROOK_1, StartingPositions.WHITE_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Rook(positionToSet, Piece.WHITE, gameInfo));
        positionToSet = new Position(StartingPositions.ROOK_2, StartingPositions.WHITE_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Rook(positionToSet, Piece.WHITE, gameInfo));

        positionToSet = new Position(StartingPositions.BISHOP_1, StartingPositions.WHITE_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Bishop(positionToSet, Piece.WHITE, gameInfo));
        positionToSet = new Position(StartingPositions.BISHOP_2, StartingPositions.WHITE_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Bishop(positionToSet, Piece.WHITE, gameInfo));

        positionToSet = new Position(StartingPositions.KNIGHT_1, StartingPositions.WHITE_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Knight(positionToSet, Piece.WHITE, gameInfo));
        positionToSet = new Position(StartingPositions.KNIGHT_2, StartingPositions.WHITE_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Knight(positionToSet, Piece.WHITE, gameInfo));

        positionToSet = new Position(StartingPositions.WHITE_KING_X, StartingPositions.WHITE_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new King(positionToSet, Piece.WHITE, gameInfo));
        positionToSet = new Position(StartingPositions.WHITE_QUEEN_X, StartingPositions.WHITE_NON_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Queen(positionToSet, Piece.WHITE, gameInfo));

        // initialize white pawns
        positionToSet = new Position(0, StartingPositions.WHITE_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, Piece.WHITE, gameInfo));
        positionToSet = new Position(1, StartingPositions.WHITE_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, Piece.WHITE, gameInfo));
        positionToSet = new Position(2, StartingPositions.WHITE_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, Piece.WHITE, gameInfo));
        positionToSet = new Position(3, StartingPositions.WHITE_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, Piece.WHITE, gameInfo));
        positionToSet = new Position(4, StartingPositions.WHITE_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, Piece.WHITE, gameInfo));
        positionToSet = new Position(5, StartingPositions.WHITE_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, Piece.WHITE, gameInfo));
        positionToSet = new Position(6, StartingPositions.WHITE_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, Piece.WHITE, gameInfo));
        positionToSet = new Position(7, StartingPositions.WHITE_PAWN_Y);
        boardGrid.get(positionToSet.getY()).get(positionToSet.getX()).setPieceOnSquare(new Pawn(positionToSet, Piece.WHITE, gameInfo));
    }

    /**
     * Populates the board with empty squares
     */
    private void populateSquares() {
        // re-set the board
        boardGrid = new ArrayList<>();

        // loop through rows
        for(int y = 0; y < Board.BOARD_SIZE_Y; y++) {
            // create a new row
            boardGrid.add(new ArrayList<>());

            // loop through columns
            for(int x = 0; x < Board.BOARD_SIZE_X; x++) {
                // create a new square at the current x and y
                boardGrid.get(y).add(new Square());
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
                if(piece == null || piece.isCaptured()) {
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
        // make sure the protected squares have been initialized
        initialize();

        Square square = boardGrid.get(position.getY()).get(position.getX());

        return (square.isProtectedByBlack() && color) || (square.isProtectedByWhite() && color);
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
        // calculate the deltas
        int deltaX = position2.getX() - position1.getX();
        int deltaY = position2.getY() - position1.getY();

        // default our changes in x and y to 0
        int changeInX = 0;
        int changeInY = 0;

        // determine if our changes in x and y are different
        if(Math.abs(deltaX) == Math.abs(deltaY)) {
            // the move is diagonal

            // calculate the change in x
            if(deltaX > 0) {
                changeInX = 1;
            } else if(deltaX < 0) {
                changeInX = -1;
            }

            // calculate the change in Y
            if(deltaY > 0) {
                changeInY = 1;
            } else if(deltaY < 0) {
                changeInY = -1;
            }
        } else if(deltaX == 0 ^ deltaY == 0) {
            // the move is horizontal or vertical

            // change in X
            if(deltaX > 0) {
                changeInX = 1;
            } else if(deltaX < 0) {
                changeInX = -1;
            }

            // change Y
            if(deltaY > 0) {
                changeInY = 1;
            } else if (deltaY < 0) {
                changeInY = -1;
            }
        } else {
            // the move is not vertical or horizontal, it may be a knights move, but either way we don't
            // need to verify intervening moves
            return false;
        }

        int x = position1.getX() + changeInX;
        int y = position1.getY() + changeInY;

        while(!(position2.getX() == x && position2.getY() == y)) {
            if(!gameInfo.isOnBoard(new Position(x, y))) {
                return false;
            }

            // check if there is a piece at the current spot
            if(boardGrid.get(y).get(x).getPieceOnSquare() != null) {
                return true;
            }

            // increment the position
            x += changeInX;
            y += changeInY;
        }

        // no intervening pieces were found
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
        // make sure the protected squares have been initialized
        initialize();

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
        // make sure the protected squares have been initialized
        initialize();

        // reset color in check
        colorInCheck = ColorInCheck.none;

        // extract the kings
        Piece whiteKing = null;
        Piece blackKing = null;

        // loop through the rows
        for(List<Square> list : boardGrid) {
            // loop through the columns
            for(Square square : list) {
                // extract the piece on the square
                Piece piece = square.getPieceOnSquare();

                // verify there is a piece
                if(piece != null) {
                    // check if it's a king
                    if(piece instanceof King) {
                        // check it's color
                        if(piece.getColor()) {
                            whiteKing = piece;
                        } else {
                            blackKing = piece;
                        }
                    }
                }
            }
        }

        // throw an exception if we can't find one of the kings - they have to
        // be there!
        if(whiteKing == null || blackKing == null) {
            throw new IllegalStateException("Unable to locate one of the kings.");
        }

        // get the lists of threatened positions
        List<Position> threatenedByWhite = getThreatenedPositions(Piece.WHITE);
        List<Position> threatenedByBlack = getThreatenedPositions(Piece.BLACK);

        // check if white is in check
        if(threatenedByBlack.contains(whiteKing.getPosition())) {
            colorInCheck = ColorInCheck.white;
        }

        // check if black is in check
        if(threatenedByWhite.contains(blackKing.getPosition())) {
            // if white is also in check we have an invalid state
            if(colorInCheck != ColorInCheck.none) {
                throw new IllegalStateException("Both kings in check");
            }

            colorInCheck = ColorInCheck.black;
        }
    }

    /**
     * Moves a piece without any validation, except verifying it's not
     * moving onto a piece of the same color.
     *
     * @param startPosition The starting position
     * @param endPosition The ending position
     */
    public void forceMove(Position startPosition, Position endPosition)
            throws IllegalArgumentException {
        // get the starting and ending pieces
        Piece startPiece = getPieceAt(startPosition);
        Piece endPiece = getPieceAt(endPosition);

        boolean validMove = true;

        // make sure not attempting to capture same color piece
        if(endPiece != null) {
            if(endPiece.getColor() == startPiece.getColor()) {
                validMove = false;
            }
        }

        // if its OK, move the piece
        if(validMove) {
            // capture the end piece if it's there
            if(endPiece != null) {
                endPiece.capture();
            }

            // update the board grid
            boardGrid.get(startPosition.getY()).get(startPosition.getX()).setPieceOnSquare(null);
            boardGrid.get(endPosition.getY()).get(endPosition.getX()).setPieceOnSquare(startPiece);

            // update the piece
            startPiece.move(endPosition);
        } else {
            throw new IllegalArgumentException("Can not capture a piece "
                    + "of the same color.");
        }

        determineProtectedSquares();

        // this can throw an exception, but we expect that to be checked
        // for by the caller who is forcing the move
        verifyCheck();
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

    /**
     * Initializes the protected squares.  This is necessary because
     * this this task requires the game referenced in the GameInfoWrapper
     * to be fully instantiated.
     */
    private void initialize() {
        if(!protectedSquaresInitialized) {
            determineProtectedSquares();
            protectedSquaresInitialized = true;
        }
    }
}
