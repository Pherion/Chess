/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.renderers.renderStyles;

import java.awt.Color;


/**
 *
 * @author CarrollFD
 */
public class DefaultSwingRendererStyle implements RendererStyle {

    @Override
    public Color getWhitePieceColor() {
        return Color.BLACK;
    }

    @Override
    public Color getBlackPieceColor() {
        return Color.BLACK;
    }

    @Override
    public Color getWhiteSquareColor() {
        return Color.WHITE;
    }

    @Override
    public Color getBlackSquareColor() {
        return Color.GRAY;
    }

    @Override
    public Color getExteriorBorderColor() {
        return Color.BLACK;
    }

    @Override
    public Color getInteriorBorderColor() {
        return Color.BLACK;
    }

    @Override
    public Color getSelectedSquareColor() {
        return Color.BLUE;
    }

    @Override
    public Color getSelectedBlackPieceColor() {
        return Color.WHITE;
    }

    @Override
    public Color getSelectedWhitePieceColor() {
        return Color.WHITE;
    }
    
}
