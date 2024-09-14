package cz.cuni.mff.java.drawing;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A simple implementation of {@link Pen} for drawing basic strokes.
 * <p>
 * This class represents a basic drawing tool that creates a continuous
 * single-color stroke as the user drags the mouse across the drawing area.
 * </p>
 * <p>
 * The pen's behavior is controlled by {@link PenSettings}, which determines the stoke width and color.
 * {@code PenSettings} must be provided either in the constructor or using {@link #setSettings(PenSettings)}
 * before use.
 * </p>
 *
 * @see Pen
 */
public class BasicPen extends Pen {
    private int lastX = 0;
    private int lastY = 0;

    public BasicPen(PenSettings settings) {
        this.settings = settings;
    }

    public BasicPen() { }

    /**
     * Handles the event when the mouse is pressed.
     * <p>
     * This method starts the drawing by rendering a point
     * at the location where the mouse was pressed.
     * </p>
     *
     * @param x the x-coordinate of the mouse press
     * @param y the y-coordinate of the mouse press
     * @param image the {@link BufferedImage} where drawing occurs
     *
     * @throws IllegalStateException when the {@code ShapePen} is used before providing {@code PenSettings}
     */
    @Override
    public void mousePressed(int x, int y, BufferedImage image) {
        if (settings == null)
            throw new IllegalStateException("Settings must be provided before use.");

        Graphics2D g = image.createGraphics();
        settings.setupGraphics2D(g);

        g.drawLine(x, y, x, y);

        lastX = x;
        lastY = y;
    }

    /**
     * Handles the event when the mouse is dragged.
     * <p>
     * This method draws a line segment from the last recorded mouse position to the
     * current mouse position.
     * </p>
     *
     * @param x the x-coordinate of the current mouse position
     * @param y the y-coordinate of the current mouse position
     * @param image the {@link BufferedImage} where drawing occurs
     *
     * @throws IllegalStateException when the {@code ShapePen} is used before providing {@code PenSettings}
     */
    @Override
    public void mouseDragged(int x, int y, BufferedImage image) {
        if (settings == null)
            throw new IllegalStateException("Settings must be provided before use.");

        Graphics2D g = image.createGraphics();
        settings.setupGraphics2D(g);

        g.drawLine(lastX, lastY, x, y);

        lastX = x;
        lastY = y;
    }

    /**
     * Handles the event when the mouse is released.
     * <p>
     * This method finishes the current drawing segment.
     * </p>
     *
     * @param x the x-coordinate of the mouse release
     * @param y the y-coordinate of the mouse release
     * @param image the {@link BufferedImage} where drawing occurs
     */
    @Override
    public void mouseReleased(int x, int y, BufferedImage image) {
        lastX = 0;
        lastY = 0;
    }

    /**
     * Resets the {@code BasicPen} to its initial state.
     */
    @Override
    public void reset() {
        lastX = 0;
        lastY = 0;
    }
}
