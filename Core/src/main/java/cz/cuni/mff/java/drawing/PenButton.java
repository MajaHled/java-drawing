package cz.cuni.mff.java.drawing;

import javax.swing.*;
import java.awt.*;

public class PenButton extends JToggleButton {
    public final Pen pen;
    private static final int ICON_SIZE = 40;

    public PenButton(Pen pen) {
        super();
        this.pen = pen;

        Icon icon = pen.getPreferredIcon();
        if (icon != null) {
            this.setIcon(icon);
        } else {
            int widthBackup = pen.settings.strokeWidth;
            Color backgroundBackup = pen.settings.backgroundColor;
            pen.settings.strokeWidth = 3;
            pen.settings.backgroundColor = Color.lightGray;

            var img = PictureLoader.NewImage(ICON_SIZE, ICON_SIZE, Color.WHITE);

            pen.mousePressed(5, 5, img); // TODO put this in docs for sure
            for (int i = 6; i <= ICON_SIZE - 5; i += 2) {
                pen.mouseDragged(i, i, img);
            }

            pen.mouseReleased(ICON_SIZE - 5, ICON_SIZE - 5, img);

            this.setIcon(new ImageIcon(img));

            pen.settings.strokeWidth = widthBackup;
            pen.settings.backgroundColor = backgroundBackup;
        }
    }

    public int getIconSize() {
        return ICON_SIZE;
    }
}
