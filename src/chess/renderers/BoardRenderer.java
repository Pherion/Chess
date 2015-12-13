package chess.renderers;

import chess.renderers.renderStyles.RendererStyle;

/**
 * Created by fraca_000 on 12/12/2015.
 */
public interface BoardRenderer {
    /**
     * Renders the board
     */
    void render();

    /**
     * @param style The style to use when rendering
     */
    void setRendererStyle(RendererStyle style);
}
