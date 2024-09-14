package cz.cuni.mff.java.drawing;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Abstract class which defines the basic API for drawing tools.
 * <p>
 * This class provides the basic functioning of a pen.
 * It is designed to be extended with the actual implementation of the drawing logic for various kinds of pen.
 * It handles setting the {@link PenSettings} for the pen, which can be used as parameters for the drawing behavior.
 * </p>
 * <p>
 * Subclasses should override any of the {@code mouse*} methods necessary for the functioning of the pen;
 * the others may be left default (empty). Implementation may or may not use all the provided parameters in the
 * {@code PenSettings} instance, but should use them wherever it makes logical sense in order for the pen
 * to behave intuitively,
 * (e.g. a pen which draws in rainbow colors can safely ignore the {@code backgroundColor} and
 * {@code mainColor} parameters, but should reasonably keep to the {@code strokeWidth} parameter.)
 * </p>
 * <p>
 * Each subclass should have a non-parametric constructor in order to be usable by {@link java.util.ServiceLoader}.
 * Any internal state and held resources should be released in the {@link #reset()} method. The pen may optionally
 * provide a preferred icon to be used as a graphical representation of the pen in the {@link  #getPreferredIcon()} method.
 * </p>
 * <p>
 * The {@code PenSettings} should be provided using {@link #setSettings(PenSettings)} before use.
 * </p>
 *
 * @see PenSettings
 * @see BasicPen
 * @see DoublePen
 * @see #mouseDragged(int, int, BufferedImage)
 * @see #mouseMoved(int, int, BufferedImage)
 * @see #mouseExited(int, int, BufferedImage)
 * @see #mouseEntered(int, int, BufferedImage)
 * @see #mousePressed(int, int, BufferedImage)
 * @see #mouseClicked(int, int, BufferedImage)
 * @see #mouseReleased(int, int, BufferedImage)
 */
public abstract class Pen {
    /**
     * The settings for this pen, which may affect its appearance.
     *
     * @see PenSettings
     */
    protected PenSettings settings;

    /**
     * Constructs a new {@code Pen} with no initial settings.
     * <p>
     * (A {@link PenSettings} instance should be provided via the {@code setSettings} method before use.)
     * </p>
     */
    public Pen() {}

    /**
     * Sets the settings for this pen.
     *
     * @param settings the {@link PenSettings} to set
     */
    public final void setSettings(PenSettings settings) {
        this.settings = settings;
    }

    /**
     * Handles the event when the mouse is dragged.
     * <p>
     * This method does nothing by default. Subclasses may override this method to provide specific drawing logic.
     * </p>
     *
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     * @param image the {@link BufferedImage} to be drawn on
     */
    public void mouseDragged(int x, int y, BufferedImage image) {}

    /**
     * Handles the event when the mouse is moved.
     * <p>
     * This method does nothing by default. Subclasses may override this method to provide specific drawing logic.
     * </p>
     *
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     * @param image the {@link BufferedImage} to be drawn on
     */
    public void mouseMoved(int x, int y, BufferedImage image) {}

    /**
     * Handles the event when the mouse exits the drawing area.
     * <p>
     * This method does nothing by default. Subclasses may override this method to provide specific drawing logic.
     * </p>
     *
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     * @param image the {@link BufferedImage} to be drawn on
     */
    public void mouseExited(int x, int y, BufferedImage image) {}

    /**
     * Handles the event when the mouse enters the drawing area.
     * <p>
     * This method does nothing by default. Subclasses may override this method to provide specific drawing logic.
     * </p>
     *
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     * @param image the {@link BufferedImage} to be drawn on
     */
    public void mouseEntered(int x, int y, BufferedImage image) {}

    /**
     * Handles the event when the mouse is pressed.
     * <p>
     * This method does nothing by default. Subclasses may override this method to provide specific drawing logic.
     * </p>
     *
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     * @param image the {@link BufferedImage} to be drawn on
     */
    public void mousePressed(int x, int y, BufferedImage image) {}

    /**
     * Handles the event when the mouse is clicked.
     * <p>
     * This method does nothing by default. Subclasses may override this method to provide specific drawing logic.
     * </p>
     *
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     * @param image the {@link BufferedImage} to be drawn on
     */
    public void mouseClicked(int x, int y, BufferedImage image) {}

    /**
     * Handles the event when the mouse is released.
     * <p>
     * This method does nothing by default. Subclasses may override this method to provide specific drawing logic.
     * </p>
     *
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     * @param image the {@link BufferedImage} to be drawn on
     */
    public void mouseReleased(int x, int y, BufferedImage image) {}

    /**
     * Resets the state of the pen.
     * <p>
     * This method does nothing by default. Subclasses should override this method to provide specific logic for
     * resetting the pen to its initial state. Any internal state and held resources should be released.
     * </p>
     * <p>
     * This method should be called any time the pen stops being used (i.e. it is deselected),
     * it should not be called between immediately consecutive uses.
     * </p>
     */
    public void reset() {}

    /**
     * Returns the preferred icon for this pen, to be used as a graphical representation of the pen.
     * <p>
     * This method returns {@code null} by default. Subclasses may optionally override this method to provide an {@link Icon}
     * representing the pen.
     * </p>
     *
     * @return the preferred {@link Icon} for this pen, or {@code null} if none is preferred
     */
    public Icon getPreferredIcon() {
        return null;
    }
}
