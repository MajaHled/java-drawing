package cz.cuni.mff.java.drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

/**
 * A {@link JPanel} subclass used for displaying and interacting with a drawable image.
 * <p>
 * This panel allows users to draw on an image using a {@link Pen} implementation, with interactions being handled by
 * various mouse events. The panel's behavior is controlled by a {@link DrawPanelSettings} instance,
 * which also specifies which {@code Pen} is to be used.
 * </p>
 * <p>
 * It supports scrolling through a {@link JScrollPane} and ensures that the image
 * is centered within the panel if smaller than the panel's dimensions.
 * </p>
 *
 * @see Pen
 * @see DrawPanelSettings
 */
public class DrawPanel extends JPanel {
    /**
     * The image to be displayed and drawn on.
     */
    private BufferedImage img;
    /**
     * The {@link DrawPanelSettings} to be used.
     */
    private final DrawPanelSettings s;
    /**
     * The scroll pane to which the preferred size of the panel is to be updated.
     */
    private JScrollPane scrollPane;

    /**
     * X coordinate of the top left corner of the image within the panel.
     */
    private int imgStartX = 0;
    /**
     * Y coordinate of the top left corner of the image within the panel.
     */
    private int imgStartY = 0;

    /**
     * Constructs a {@code DrawPanel} with the specified settings and initial image.
     * <p>
     * Links all the appropriate mouse events to the {@code Pen} methods of the
     * {@code currentPen} specified in the {@link DrawPanelSettings}.
     * </p>
     *
     * @param settings the {@link DrawPanelSettings} to configure the panel
     * @param image the initial {@link BufferedImage} to display on the panel
     *
     * @see Pen
     */
    public DrawPanel(DrawPanelSettings settings, BufferedImage image) {
        this.s = settings;
        this.setImage(image);

        // Link all the mouse events to appropriate methods on currentPen from the settings
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (s.currentPen != null)
                    s.currentPen.mouseDragged(transformX(e.getX()), transformY(e.getY()),  img);
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (s.currentPen != null)
                    s.currentPen.mouseMoved(transformX(e.getX()), transformY(e.getY()),  img);
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (s.currentPen != null)
                    s.currentPen.mouseExited(transformX(e.getX()), transformY(e.getY()),  img);
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (s.currentPen != null)
                    s.currentPen.mouseEntered(transformX(e.getX()), transformY(e.getY()),  img);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (s.currentPen != null)
                    s.currentPen.mouseReleased(transformX(e.getX()), transformY(e.getY()),  img);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (s.currentPen != null)
                    s.currentPen.mousePressed(transformX(e.getX()), transformY(e.getY()),  img);
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (s.currentPen != null)
                    s.currentPen.mouseClicked(transformX(e.getX()), transformY(e.getY()),  img);
                repaint();
            }
        });
    }

    /**
     * Transforms the x-coordinate based on {@link #imgStartX}.
     * This is used for properly handling mouse events on the centered image.
     *
     * @param x the original x-coordinate
     * @return the transformed x-coordinate relative to the image
     *
     * @see #transformY(int)
     */
    private int transformX(int x) {
        return x - imgStartX;
    }

    /**
     * Transforms the y-coordinate based on {@link #imgStartY}.
     * This is used for properly handling mouse events on the centered image.
     *
     * @param y the original y-coordinate
     * @return the transformed y-coordinate relative to the image
     *
     * @see #transformX(int)
     */
    private int transformY(int y) {
        return y - imgStartY;
    }

    /**
     * An override of the {@link JComponent#paintComponent(Graphics)} method,
     * which handles the graphical render of the component.
     * <p>
     * Paints the image on the panel, centering it if smaller than the panel dimensions.
     * </p>
     *
     * @param g the {@link Graphics} object used for painting
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // If the image is smaller than the panel area, center it
        imgStartX = Math.max(0, (this.getWidth() - img.getWidth()) / 2);
        imgStartY = Math.max(0, (this.getHeight() - img.getHeight()) / 2);
        g.drawImage(img, imgStartX, imgStartY, null);
    }

    /**
     * Sets the scroll pane that into which this panel will update its preferred size to match the image's dimensions.
     *
     * @param scrollPane the {@link JScrollPane} to set for this panel
     */
    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
        scrollPane.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
    }

    /**
     * Gets the current image displayed on the panel.
     *
     * @return the current {@link BufferedImage} being displayed
     */
    public BufferedImage getImage() {
        return img;
    }

    /**
     * Sets a new image to be displayed on the panel.
     * <p>
     * Also updates the panel's preferred size in the associated {@link JScrollPane},
     * if one has been specified using {@link #setScrollPane(JScrollPane)}.
     * </p>
     *
     * @param image the new {@link BufferedImage} to display
     */
    public void setImage(BufferedImage image) {
        this.img = image;
        this.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        if (scrollPane != null)
            scrollPane.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        repaint();
    }

    /**
     * Gets the settings associated with this drawing panel.
     *
     * @return the {@link DrawPanelSettings} used to configure this panel
     */
    public DrawPanelSettings getSettings() {
        return s;
    }
}
