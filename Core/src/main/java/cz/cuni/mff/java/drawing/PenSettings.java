package cz.cuni.mff.java.drawing;

import java.awt.*;

/**
 * A class representing the settings used for configuring the appearance of a {@link Pen} implementation.
 * <p>
 * This class holds the configuration for the pen's color, background color, and stroke width.
 * It provides a method to apply these settings to a {@link Graphics2D} object.
 * </p>
 * <p>
 * Individual pens may choose to use or ignore any of these settings
 * as needed for their specific functioning.
 * </p>
 *
 * @see Pen
 * @see ShapePen
 */
public class PenSettings {
    /**
     * The color to be used for drawing with the pen.
     * <p>
     * Common usage includes he stroke color of pens and outline color of shapes
     * </p>
     */
    public Color mainColor;

    /**
     * The color to be used for the background.
     * <p>
     * Common usage includes the fill color of shapes.
     * </p>
     */
    public Color backgroundColor;

    /**
     * The stroke width to be used for the pen.
     * <p>
     * Common usage also includes the width of shape outlines
     * </p>
     */
    public int strokeWidth;

    /**
     * Creates a {@code PenSettings} object with default values:
     * <ul>
     *   <li>Main color: {@link Color#BLACK}</li>
     *   <li>Background color: {@link Color#WHITE}</li>
     *   <li>Stroke width: 1</li>
     * </ul>
     */
    public PenSettings() {
        mainColor = Color.BLACK;
        backgroundColor = Color.WHITE;
        strokeWidth = 1;
    }

    /**
     * Creates a {@code PenSettings} object with the specified values.
     *
     * @param mainColor he color to be used for drawing with the pen
     * @param backgroundColor he color to be used for the background and fill
     * @param strokeWidth the stroke width to be used for the pen
     */
    public PenSettings(Color mainColor, Color backgroundColor, int strokeWidth) {
        this.mainColor = mainColor;
        this.backgroundColor = backgroundColor;
        this.strokeWidth = strokeWidth;
    }

    /**
     * Configures the specified {@link Graphics2D} object using the settings of this {@code PenSettings}.
     * <p>
     * This method sets the color and stroke of the {@code Graphics2D} object to match the pen's settings.
     * Specifically, it sets the color to {@code mainColor} and the stroke to a {@link BasicStroke} with the
     * specified {@code strokeWidth}, rounded caps, and miter join.
     * </p>
     *
     * @param g the {@link Graphics2D} object to configure
     */
    public void setupGraphics2D(Graphics2D g) {
        g.setColor(mainColor);
        g.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
    }
}