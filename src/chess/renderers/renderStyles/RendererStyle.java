package chess.renderers.renderStyles;

import java.awt.Color;

/**
 * Created by fraca_000 on 12/12/2015.
 */
public interface RendererStyle {
    public Color getWhitePieceColor();
    public Color getBlackPieceColor();
    public Color getWhiteSquareColor();
    public Color getBlackSquareColor();
    public Color getExteriorBorderColor();
    public Color getInteriorBorderColor();
    public Color getSelectedSquareColor();
    public Color getSelectedBlackPieceColor();
    public Color getSelectedWhitePieceColor();
}
