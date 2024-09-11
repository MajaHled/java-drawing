package cz.cuni.mff.java.hw.drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class PenButton extends JToggleButton {
    public final Pen pen;

    public PenButton(Pen pen) {
        super();
        this.pen = pen;

        Icon icon = pen.getPreferredIcon();
        if (icon != null) {
            this.setIcon(icon);
        } else {
            int widthBackup = pen.settings.strokeWidth;
            pen.settings.strokeWidth = 2;

            var img = PictureLoader.NewImage(30, 30, Color.WHITE);
            pen.mousePressed(new MouseEvent(this, 0, 0, 0, 5, 5, 1, false), img); // TODO put this in docs for sure
            pen.mouseDragged(new MouseEvent(this, 0, 0, 0, 15, 15, 1, false), img);
            pen.mouseDragged(new MouseEvent(this, 0, 0, 0, 25, 25, 1, false), img); // TODO put this in docs for sure
            pen.mouseReleased(new MouseEvent(this, 0, 0, 0, 25, 25, 1, false), img);

            this.setIcon(new ImageIcon(img));

            pen.settings.strokeWidth = widthBackup;
        }
    }
}
