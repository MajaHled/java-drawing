package cz.cuni.mff.java.drawing;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.google.auto.service.AutoService;

/**
 * An implementation of {@link Pen} for drawing a rainbow-colored stroke.
 * <p>
 * The color of the stroke is controlled by incrementing the hue of the HSB color model
 * as the user drags the mouse.
 * </p>
 * <p>
 * The pen's behavior is controlled by {@link PenSettings}, which determines the stoke width.
 * The color setting are ignored.
 * {@code PenSettings} must be provided using {@link #setSettings(PenSettings)} before use.
 * </p>
 * <p>
 * This class is packaged into a {@link Pen} plugin using the {@link AutoService} annotation.
 * </p>
 *
 * @see Pen
 */
@AutoService(Pen.class)
public class RainbowPen extends Pen {
    private int lastX = 0;
    private int lastY = 0;
    private float hue = 0;

    /**
     * Handles the event when the mouse is pressed.
     * <p>
     * This method starts the drawing by rendering a point
     * at the location where the mouse was pressed. The color is
     * initialized as the HSB hue of 0, i.e. red.
     * </p>
     *
     * @param x the x-coordinate of the mouse press
     * @param y the y-coordinate of the mouse press
     * @param image the {@link BufferedImage} to be drawn on
     *
     * @throws IllegalStateException when the {@code RainbowPen} is used before providing {@link PenSettings}
     */
    @Override
    public void mousePressed(int x, int y, BufferedImage image) {
        if (settings == null)
            throw new IllegalStateException("Settings must be provided before use.");

        Graphics2D g = image.createGraphics();
        settings.setupGraphics2D(g);

        g.setColor(Color.getHSBColor(hue, 1, 1));

        g.drawLine(x, y, x, y);

        lastX = x;
        lastY = y;
    }

    /**
     * Handles the event when the mouse is dragged.
     * <p>
     * This method draws a line segment from the last recorded mouse position to the
     * current mouse position. The color is determined by how far the mouse has moved
     * from its last known position.
     * </p>
     *
     * @param x the x-coordinate of the current mouse position
     * @param y the y-coordinate of the current mouse position
     * @param image the {@link BufferedImage} to be drawn on
     *
     * @throws IllegalStateException when the {@code RainbowPen} is used before providing {@link PenSettings}
     */
    @Override
    public void mouseDragged(int x, int y, BufferedImage image) {
        if (settings == null)
            throw new IllegalStateException("Settings must be provided before use.");

        Graphics2D g = image.createGraphics();
        settings.setupGraphics2D(g);

        float len = (float) Math.sqrt(Math.pow(lastX - x, 2) + Math.pow(lastY - y, 2));
        hue += len / 100;
        g.setColor(Color.getHSBColor(hue, 1, 1));

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
     * @param image the {@link BufferedImage} to be drawn on
     */
    @Override
    public void mouseReleased(int x, int y, BufferedImage image) {
        lastX = 0;
        lastY = 0;
    }

    /**
     * Resets the {@code RainbowPen} to its initial state.
     * <p>
     * This method should be called any time the pen stops being used (i.e. it is deselected),
     * it should not be called between immediately consecutive uses.
     * </p>
     */
    @Override
    public void reset() {
        lastX = 0;
        lastY = 0;
        hue = 0;
    }
}
