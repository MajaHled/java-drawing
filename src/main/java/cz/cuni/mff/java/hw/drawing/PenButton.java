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
            // TODO resize
            this.setIcon(icon);
        } else {
            int widthBackup = pen.settings.strokeWidth;
            pen.settings.strokeWidth = 3;

            var img = PictureLoader.NewImage(30, 30, Color.WHITE);
            pen.mousePressed(5, 5, img); // TODO put this in docs for sure
            pen.mouseDragged(15, 15, img);
            pen.mouseDragged(25, 25, img);
            pen.mouseReleased(25, 25, img);

            this.setIcon(new ImageIcon(img));

            pen.settings.strokeWidth = widthBackup;
        }
    }
}
