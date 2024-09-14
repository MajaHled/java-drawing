package cz.cuni.mff.java.drawing;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Concrete implementation of {@link ShapePen} for drawing lines.
 *
 * @see ShapePen
 */
public class LinePen extends ShapePen {
    @Override
    protected Shape getShape(int x1, int y1, int x2, int y2) {
        return new Line2D.Float(x1, y1, x2, y2);
    }

    public LinePen(PenSettings settings) {
        this.settings = settings;
    }

    public LinePen() { }
}
