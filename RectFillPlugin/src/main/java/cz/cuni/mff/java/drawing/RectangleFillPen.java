package cz.cuni.mff.java.drawing;

import java.awt.*;
import com.google.auto.service.AutoService;

/**
 * Concrete implementation of {@link ShapePen} for drawing filled rectangles.
 * <p>
 * This class is packaged into a {@link Pen} plugin using the {@link AutoService} annotation.
 * </p>
 * @see ShapePen
 */

@AutoService(ShapePen.class)
public class RectangleFillPen extends ShapePen {
    @Override
    protected Shape getShape(int x1, int y1, int x2, int y2) {
        return new Rectangle(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
    }

    public RectangleFillPen() {
        fill = true;
    }
}
