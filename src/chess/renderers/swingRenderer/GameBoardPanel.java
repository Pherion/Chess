package chess.renderers.swingRenderer;

import chess.game.GameInfoWrapper;
import chess.game.board.Board;
import chess.game.board.Position;
import chess.pieces.ImmutablePiece;
import chess.renderers.BoardRenderer;
import chess.renderers.MoveRequestAction;
import chess.renderers.TextChessPieces;
import chess.renderers.renderStyles.DefaultSwingRendererStyle;
import chess.renderers.renderStyles.RendererStyle;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * Renders the game board on a JPanel
 * 
 * @author CarrollFD
 */
public class GameBoardPanel extends JPanel implements BoardRenderer {
   // the style object
    private RendererStyle style = new DefaultSwingRendererStyle();

    // the game info wrapper
    private final GameInfoWrapper gameInfo;

    // the move request action
    private MoveRequestAction moveRequestAction;
    
    // Position object to store the button clicks and to be passed into
    // the MoveRequestAction
    private Position startPosition;
    
    /**
     * Constructs a new game panel
     * 
     * @param gameInfo the game info wrapper
     */
    public GameBoardPanel(GameInfoWrapper gameInfo) {
        this.gameInfo = gameInfo;
        
        // set-up layout and borders
        setUpPanel();
    }
    
    @Override
    public void render() {
        // clear the board
        this.removeAll();
        
        // iniitalize background color flag
        // and color objects
        boolean bgColorFlag = true;
        Color bgColor;
        Color fgColor;
        
        for(int y = 0; y < Board.BOARD_SIZE_Y; y++) {
            for(int x = 0; x < Board.BOARD_SIZE_X; x++) {
                Position position = new Position(x, y);
                ImmutablePiece piece = gameInfo.getPieceAt(position);
                
                if(piece != null && piece.getColor()) {
                    fgColor = style.getWhitePieceColor();
                } else {
                    fgColor = style.getBlackPieceColor();
                }
                
                // set the background color for this cell
                if(startPosition != null && startPosition.equals(position)) {
                    // if its the selected cell
                    bgColor = style.getSelectedSquareColor();
                    
                    if(piece != null && piece.getColor()) {
                        fgColor = style.getSelectedWhitePieceColor();
                    } else {
                        fgColor = style.getSelectedBlackPieceColor();
                    }
                }else if(bgColorFlag) {
                    // if its a white cell
                    bgColor = style.getWhiteSquareColor();
                } else {
                    // if its a black cell
                    bgColor = style.getBlackSquareColor();
                }
                
                // check if the piece is null, if so make a blank cell
                // otherwise check what kind of piece it is, then its color
                if(piece == null) {
                    addPiece(TextChessPieces.BLANK, bgColor, fgColor, new Position(x, y));
                } else {
                    switch(piece.getPieceType()) {
                        case king:
                            if(piece.getColor()) {
                                addPiece(TextChessPieces.WHITE_KING, bgColor, fgColor, position);
                            } else {
                                addPiece(TextChessPieces.BLACK_KING, bgColor, fgColor, position);
                            }
                            break;
                        case queen:
                            if(piece.getColor()) {
                                addPiece(TextChessPieces.WHITE_QUEEN, bgColor, fgColor, position);
                            } else {
                                addPiece(TextChessPieces.BLACK_QUEEN, bgColor, fgColor, position);
                            }
                            break;
                        case rook:
                            if(piece.getColor()) {
                                addPiece(TextChessPieces.WHITE_ROOK, bgColor, fgColor, position);
                            } else {
                                addPiece(TextChessPieces.BLACK_ROOK, bgColor, fgColor, position);
                            }
                            break;
                        case bishop:
                            if(piece.getColor()) {
                                addPiece(TextChessPieces.WHITE_BISHOP, bgColor, fgColor, position);
                            } else {
                                addPiece(TextChessPieces.BLACK_BISHOP, bgColor, fgColor, position);
                            }
                            break;
                        case knight:
                            if(piece.getColor()) {
                                addPiece(TextChessPieces.WHITE_KNIGHT, bgColor, fgColor, position);
                            } else {
                                addPiece(TextChessPieces.BLACK_KNIGHT, bgColor, fgColor, position);
                            }
                            break;
                        case pawn:
                            if(piece.getColor()) {
                                addPiece(TextChessPieces.WHITE_PAWN, bgColor, fgColor, position);
                            } else {
                                addPiece(TextChessPieces.BLACK_PAWN, bgColor, fgColor, position);
                            }
                            break;
                    }
                }
                
                // alternate the color unless its the end of a row.
                if(x != 7) {
                    bgColorFlag = !bgColorFlag;
                }
            }
        }
        
        updateDisplay();
    }

    /**
     * Creates the button that will represent the square on the board
     * and adds it to the panel.
     * 
     * @param piece The string representing the piece
     * @param bgColor The background color of the cell
     * @param position The position object
     */
    private void addPiece(String piece, Color bgColor, Color fgColor, Position position) {
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 75);
        PieceButton button = new PieceButton(piece, bgColor, position);
        button.setFont(font);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setForeground(fgColor);
        button.addActionListener(new ButtonPressListener());
        add(button);
    }

    /**
     * Sets-up the layout and border of the panel
     */
    private void setUpPanel() {
        GridLayout layout = new GridLayout(8, 8);
        setLayout(layout);
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    }

    private void updateDisplay() {
        revalidate();
    }
    
    @Override
    public void setRendererStyle(RendererStyle style) {
        this.style = style;
    }

    @Override
    public void setMoveRequestAction(MoveRequestAction action) {
        this.moveRequestAction = action;
    }
    
    private class ButtonPressListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // get the source
            Object source = e.getSource();
            
            // if the source isn't a PieceButton, then we don't need to do anything
            if(!(source instanceof PieceButton)) {
                return;
            }
            
            // convert the source to a PieceButton
            PieceButton buttonSource = (PieceButton)source;
            
            // check if we're clicking the start or end position
            if(startPosition != null && startPosition.equals(buttonSource.getPosition())) {
                // if the start position was clicked a second time, clear it
                startPosition = null;
            }else if(startPosition == null) {
                // if the start position was just now clicked, set it
                startPosition = buttonSource.getPosition();
            } else {
                // otherwise must be the end position clicked, make sure
                // we have a moveRequestAction object avaliable
                if(moveRequestAction == null) {
                    throw new IllegalStateException("MoveRequestAction not "
                            + "set.");
                }
                
                // spin off the move request in a new thread
//                new Thread() {
//                    @Override
//                    public void run() {
                        moveRequestAction.moveRequest(startPosition, buttonSource.getPosition());
//                    }
//                }.start();
                
                // reset the start position
                startPosition = null;
            }
            
            render();
        }        
    }
}
