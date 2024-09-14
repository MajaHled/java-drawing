package cz.cuni.mff.java.drawing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * Abstract class representing a pen specifically designed for drawing shapes.
 * <p>
 * This class extends {@code Pen} and provides functionality for drawing geometric shapes.
 * The specific shape to be drawn is determined by the implementation
 * of the {@link #getShape(int x1, int y1, int x2, int y2)} method, which must be defined by subclasses.
 * </p>
 * <p>
 * The {@code ShapePen} class manages the start and end points of the shape based on mouse movements,
 * handles the rendering of the shape on a {@link BufferedImage}, and supports optional
 * filling of the shape.
 * </p>
 * <p>
 * The outline of the shape is rendered using the stroke with and main color defined in the {@code settings}
 * field. The fill, if turned on, is done using the {@code backgroundColor}.
 * Filling behavior can be turned on by setting the {@link #fill} field to {@code true} in the constructor.
 * </p>
 * <p>
 * Subclasses should always have a non-parametric constructor in order to be usable by {@link java.util.ServiceLoader}.
 * </p>
 * <p>
 * The {@code settings} must always be set using {@link #setSettings(PenSettings)} before use.
 * </p>
 *
 * @see Pen
 * @see PenSettings
 * @see RectanglePen
 * @see CirclePen
 * @see LinePen
 */
public abstract class ShapePen extends Pen {
    private int startX = 0;
    private int startY = 0;
    private WritableRaster origRaster;

    /**
     * Indicates whether the shape should be filled.
     * <p>
     * In order to turn on the fill behavior, this field should be set to {@code true} in the constructor.
     * </p>
     */
    protected boolean fill = false;

    /**
     * Returns the {@link java.awt.Shape} that will be drawn by this {@code ShapePen}, based on the given coordinates.
     * <p>
     * This method must be implemented by subclasses to define the specific {@link java.awt.Shape}
     * that will be drawn. The shape should be situated between the start point (x1, y1)
     * and the end point (x2, y2). The start point represents the coordinates where
     * the mouse was first pressed to begin drawing, the end point represents the
     * coordinates that the mouse was dragged to.
     * </p>
     *
     * @param x1 the x-coordinate of the start point
     * @param y1 the y-coordinate of the start point
     * @param x2 the x-coordinate of the end point
     * @param y2 the y-coordinate of the end point
     * @return the {@link Shape} to be drawn
     */
    protected abstract Shape getShape(int x1, int y1, int x2, int y2);

    /**
     * Handles the event when the mouse is dragged.
     * <p>
     * This method draws a temporary version of the shape on the provided {@link BufferedImage},
     * spanning from the initial start point to the current mouse position. The state of the
     * image behind the currently drawn shape is preserved and redrawn under it
     * until the mouse is released, which finalizes the shape.
     * </p>
     * <p>If {@code fill} is {@code true},
     * the shape is filled with the background color before the outline is drawn with the
     * main color, based on the {@link #settings}.
     * </p>
     *
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     * @param image the {@link BufferedImage} to be drawn on
     *
     * @throws IllegalStateException when the {@code ShapePen} is used before providing {@code PenSettings}
     *
     * @see PenSettings
     */
    @Override
    public final void mouseDragged(int x, int y, BufferedImage image) {
        if (settings == null)
            throw new IllegalStateException("Settings must be provided before use.");
        if (origRaster != null)
            image.setData(origRaster);

        Graphics2D g = image.createGraphics();
        settings.setupGraphics2D(g);

        if (fill) {
            g.setColor(settings.backgroundColor);
            g.fill(getShape(startX, startY, x, y));
            g.setColor(settings.mainColor);
        }

        g.draw(getShape(startX, startY, x, y));
    }

    /**
     * Seals the {@link Pen#mouseMoved(int, int, BufferedImage)} method as empty,
     * since it is not needed for shape drawing.
     */
    @Override
    public final void mouseMoved(int x, int y, BufferedImage image) {}

    /**
     * Seals the {@link Pen#mouseExited(int, int, BufferedImage)} method as empty,
     * since it is not needed for shape drawing.
     */
    @Override
    public final void mouseExited(int x, int y, BufferedImage image) {}

    /**
     * Seals the {@link Pen#mouseEntered(int, int, BufferedImage)} method as empty,
     * since it is not needed for shape drawing.
     */
    @Override
    public final void mouseEntered(int x, int y, BufferedImage image) {}

    /**
     * Handles the event when the mouse is pressed.
     * <p>
     * This method initializes the start point of the shape and saves the original state
     * of the image to allow for proper rendering during dragging.
     * </p>
     *
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     * @param image the {@link BufferedImage} to be drawn on
     */
    @Override
    public final void mousePressed(int x, int y, BufferedImage image) {
        startX = x;
        startY = y;
        origRaster = image.copyData(null);
    }

    /**
     * Seals the {@link Pen#mouseClicked(int, int, BufferedImage)} method as empty,
     * since it is not needed for shape drawing.
     */
    @Override
    public final void mouseClicked(int x, int y, BufferedImage image) {}

    /**
     * Handles the event when the mouse is released.
     * <p>
     * This method finalizes the drawn shape and
     * forgets the original state of the image, as it is not needed anymore.
     * </p>
     *
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     * @param image the {@link BufferedImage} to be drawn on
     */
    @Override
    public final void mouseReleased(int x, int y, BufferedImage image) {
        startX = 0;
        startY = 0;
        origRaster = null;
    }

    /**
     * Resets the {@code ShapePen} to its initial state.
     */
    @Override
    public final void reset() {
        startX = 0;
        startY = 0;
        origRaster = null;
    }
}
