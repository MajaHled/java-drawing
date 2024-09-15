package cz.cuni.mff.java.drawing;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * An implementation of {@link Pen} for drawing double line strokes.
 * <p>
 * This class represents a drawing tool that creates a two continuous
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
public class DoublePen extends Pen {
    private int lastX = 0;
    private int lastY = 0;

    public DoublePen(PenSettings settings) {
        this.settings = settings;
    }

    public DoublePen() { }

    /**
     * Handles the event when the mouse is pressed.
     * <p>
     * This method starts the drawing by rendering two points
     * at the starting positions around where the mouse was pressed.
     * </p>
     *
     * @param x the x-coordinate of the mouse press
     * @param y the y-coordinate of the mouse press
     * @param image the {@link BufferedImage} to be drawn on
     *
     * @throws IllegalStateException when the {@code DoublePen} is used before providing {@link PenSettings}
     */
    @Override
    public void mousePressed(int x, int y, BufferedImage image) {
        Graphics2D g = image.createGraphics();
        settings.setupGraphics2D(g);

        g.drawLine(x, y - settings.strokeWidth, x, y - settings.strokeWidth);
        g.drawLine(x, y + settings.strokeWidth, x, y + settings.strokeWidth);

        lastX = x;
        lastY = y;
    }

    /**
     * Handles the event when the mouse is dragged.
     * <p>
     * This method draws two line segments from the positions around the
     * last recorded mouse position to around the current mouse position.
     * </p>
     *
     * @param x the x-coordinate of the current mouse position
     * @param y the y-coordinate of the current mouse position
     * @param image the {@link BufferedImage} to be drawn on
     *
     * @throws IllegalStateException when the {@code Double} is used before providing {@link PenSettings}
     */
    @Override
    public void mouseDragged(int x, int y, BufferedImage image) {
        Graphics2D g = image.createGraphics();
        settings.setupGraphics2D(g);

        g.drawLine(lastX, lastY - settings.strokeWidth, x, y - settings.strokeWidth);
        g.drawLine(lastX, lastY + settings.strokeWidth, x, y + settings.strokeWidth);

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
     * @param image the {@link BufferedImage} to be drawn on
     */
    @Override
    public void mouseReleased(int x, int y, BufferedImage image) {
        lastX = 0;
        lastY = 0;
    }

    /**
     * Resets the {@code DoublePen} to its initial state.
     */
    @Override
    public void reset() {
        lastX = 0;
        lastY = 0;
    }
}
