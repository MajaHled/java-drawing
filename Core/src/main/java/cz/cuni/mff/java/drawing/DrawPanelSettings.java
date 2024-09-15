package cz.cuni.mff.java.drawing;

import java.awt.*;

/**
 * A class that encapsulates the settings for configuring a {@link DrawPanel}.
 * <p>
 * This class holds the background color and the current pen tool used for drawing on the panel. It allows
 * customization of the panel's appearance and drawing behavior through its settings.
 * </p>
 */
public class DrawPanelSettings {
    public Color backgroundColor;
    public Pen currentPen;

    /**
     * Constructs a {@code DrawPanelSettings} object with default values:
     * <ul>
     *   <li>Background color: {@link Color#WHITE}</li>
     *   <li>Current pen: {@code null}</li>
     * </ul>
     *
     * @see DrawPanel
     */
    public DrawPanelSettings() {
        backgroundColor = Color.WHITE;
    }

    /**
     * Constructs a {@code DrawPanelSettings} object with the specified background color and pen tool.
     *
     * @param backgroundColor the background color of the draw panel
     * @param pen the {@link Pen} tool to use for drawing on the panel
     *
     * @see DrawPanel
     */
    public DrawPanelSettings(Color backgroundColor, Pen pen) {
        this.backgroundColor = backgroundColor;
        this.currentPen = pen;
    }
}
