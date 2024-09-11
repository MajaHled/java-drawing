package cz.cuni.mff.java.hw.drawing;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RectanglePen extends ShapePen {
    @Override
    protected Shape getShape(int x1, int y1, int x2, int y2) {
        return new Rectangle(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
    }

    public RectanglePen(PenSettings settings) {
        super(settings);
    }
}
