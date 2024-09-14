package cz.cuni.mff.java.drawing;

import java.awt.*;

/**
 * Concrete implementation of {@link ShapePen} for drawing rectangles.
 *
 * @see ShapePen
 */
public class RectanglePen extends ShapePen {
    @Override
    protected Shape getShape(int x1, int y1, int x2, int y2) {
        return new Rectangle(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
    }

    public RectanglePen(PenSettings settings) {
        this.settings = settings;
    }
    public RectanglePen() { }
}
