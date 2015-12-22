/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.renderers;

import chess.renderers.renderStyles.RendererStyle;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 *
 * @author CarrollFD
 */
public class GameBoardPanel extends JPanel implements BoardRenderer {
    
    public void render() {
        GridLayout layout = new GridLayout(8, 8);
        setLayout(layout);
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        
        addPiece(TextChessPieces.BLACK_ROOK);
        addPiece(TextChessPieces.BLACK_KNIGHT);
        addPiece(TextChessPieces.BLACK_BISHOP);
        addPiece(TextChessPieces.BLACK_QUEEN);
        addPiece(TextChessPieces.BLACK_KING);
        addPiece(TextChessPieces.BLACK_BISHOP);
        addPiece(TextChessPieces.BLACK_KNIGHT);
        addPiece(TextChessPieces.BLACK_ROOK);
        
        for(int i = 0; i < 8; i++) {
            addPiece(TextChessPieces.BLACK_PAWN);
        }
        
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 8; j++) {
                addPiece(TextChessPieces.BLANK);
            }
        }
        
        addPiece(TextChessPieces.WHITE_ROOK);
        addPiece(TextChessPieces.WHITE_KNIGHT);
        addPiece(TextChessPieces.WHITE_BISHOP);
        addPiece(TextChessPieces.WHITE_QUEEN);
        addPiece(TextChessPieces.WHITE_KING);
        addPiece(TextChessPieces.WHITE_BISHOP);
        addPiece(TextChessPieces.WHITE_KNIGHT);
        addPiece(TextChessPieces.WHITE_ROOK);
        
        for(int i = 0; i < 8; i++) {
            addPiece(TextChessPieces.WHITE_PAWN);
        }
    }

    @Override
    public void setRendererStyle(RendererStyle style) {

    }

    private void addPiece(String piece) {
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 75);
        JLabel label = new JLabel(piece, SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setFont(font);
        add(label);
    }
    
    public JPanel getPanel() throws IllegalStateException {
        if(panel == null) {
            throw new IllegalStateException("You must invoke "
                    + "GameBoardPanel.render() before attempting to access "
                    + "GameBoardPanel.getPanel()");
        }
        
        return panel;
    }
}
