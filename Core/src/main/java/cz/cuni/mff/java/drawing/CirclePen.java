package cz.cuni.mff.java.drawing;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Concrete implementation of {@link ShapePen} for drawing circles and ellipses.
 *
 * @see ShapePen
 */
public class CirclePen extends ShapePen {
    @Override
    protected Shape getShape(int x1, int y1, int x2, int y2) {
        return new Ellipse2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
    }

    public CirclePen(PenSettings settings) {
        this.settings = settings;
    }

    public CirclePen() { }
}
